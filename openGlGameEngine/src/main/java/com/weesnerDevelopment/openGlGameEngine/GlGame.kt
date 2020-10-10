package com.weesnerDevelopment.openGlGameEngine

import android.opengl.GLSurfaceView
import android.opengl.GLSurfaceView.Renderer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.weesnerDevelopment.androidGameEngine.audio.AndroidAudio
import com.weesnerDevelopment.androidGameEngine.file.AndroidFileIO
import com.weesnerDevelopment.androidGameEngine.input.AndroidInput
import com.weesnerDevelopment.gameEngine.audio.Audio
import com.weesnerDevelopment.gameEngine.file.FileIO
import com.weesnerDevelopment.gameEngine.game.Game
import com.weesnerDevelopment.gameEngine.game.Screen
import com.weesnerDevelopment.gameEngine.graphics.Graphics
import com.weesnerDevelopment.gameEngine.input.Input
import com.weesnerDevelopment.gameEngine.math.Size
import java.util.*
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

enum class GlGameState {
    Initialized,
    Running,
    Paused,
    Finished,
    Idle
}

open class GlGame : Fragment(), Game, Renderer {
    lateinit var glView: GLSurfaceView
    lateinit var glGraphics: GlGraphics
    override lateinit var graphics: Graphics
    override lateinit var audio: Audio
    override lateinit var input: Input
    override lateinit var fileIO: FileIO
    override lateinit var startScreen: Screen
    override val backStack: Stack<Screen> = Stack()
    override var screen: Screen = startScreen
        set(value) {
            screen?.pause()
            screen?.dispose()

            value.resume()
            value.update(0f)
            field = value
        }

    var state: GlGameState = GlGameState.Initialized
    var stateChanged = Object()
    var startTime = System.nanoTime()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        glView = GLSurfaceView(requireContext()).apply {
            setRenderer(this@GlGame)
        }
        fileIO = AndroidFileIO(requireContext())
        audio = AndroidAudio(requireActivity())

        return glView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        glGraphics = GlGraphics(glView)
        input = AndroidInput(requireContext(), glView, Size(1, 1))
    }

    override fun onResume() {
        super.onResume()
        glView.onResume()
    }

    override fun onPause() {
        synchronized(stateChanged) {
            state = if (isRemoving) GlGameState.Finished else GlGameState.Paused
            while (true) {
                try {
                    stateChanged.wait()
                    break
                } catch (e: InterruptedException) {
                }
            }
        }
        glView.onPause()
        super.onPause()
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        gl?.let { glGraphics.gl = it }
        synchronized(stateChanged) {
            if (state == GlGameState.Initialized) screen = startScreen
            state = GlGameState.Running
            screen.resume()
            startTime = System.nanoTime()
        }
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {}

    override fun onDrawFrame(gl: GL10?) {
        var state: GlGameState?
        synchronized(stateChanged) { state = this.state }
        if (state == GlGameState.Running) {
            val deltaTime = (System.nanoTime() - startTime) / 1_000_000_000.0f
            startTime = System.nanoTime()
            screen.update(deltaTime)
            screen.present(deltaTime)
        }
        if (state == GlGameState.Paused) {
            screen.pause()
            synchronized(stateChanged) {
                this.state = GlGameState.Idle
                stateChanged.notifyAll()
            }
        }
        if (state == GlGameState.Finished) {
            screen.pause()
            screen.dispose()
            synchronized(stateChanged) {
                this.state = GlGameState.Idle
                stateChanged.notifyAll()
            }
        }
    }

    override fun back() {
        val previous = backStack.pop()
        screen = previous
        backStack.pop()
    }
}