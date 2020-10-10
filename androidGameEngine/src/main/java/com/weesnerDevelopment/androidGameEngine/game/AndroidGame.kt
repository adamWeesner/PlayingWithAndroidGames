package com.weesnerDevelopment.androidGameEngine.game

import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.weesnerDevelopment.androidGameEngine.audio.AndroidAudio
import com.weesnerDevelopment.androidGameEngine.file.AndroidFileIO
import com.weesnerDevelopment.androidGameEngine.graphics.AndroidGraphics
import com.weesnerDevelopment.androidGameEngine.graphics.AndroidRenderView
import com.weesnerDevelopment.androidGameEngine.input.AndroidInput
import com.weesnerDevelopment.gameEngine.audio.Audio
import com.weesnerDevelopment.gameEngine.file.FileIO
import com.weesnerDevelopment.gameEngine.game.Game
import com.weesnerDevelopment.gameEngine.game.Screen
import com.weesnerDevelopment.gameEngine.graphics.Graphics
import com.weesnerDevelopment.gameEngine.input.Input
import com.weesnerDevelopment.gameEngine.math.Vector2D
import com.weesnerDevelopment.gameEngine.math.Size
import java.util.*

abstract class AndroidGame : Fragment(), Game {
    private lateinit var renderView: AndroidRenderView

    private val defaultSize = Vector2D(320, 480)
    private val gameSizePortrait = Size(defaultSize.x, defaultSize.y)
    private val gameSizeLandscape = Size(defaultSize.y, defaultSize.x)
    private lateinit var frameBuffer: Bitmap

    override val backStack: Stack<Screen> = Stack()
    override lateinit var graphics: Graphics
    override lateinit var audio: Audio
    override lateinit var input: Input
    override lateinit var fileIO: FileIO
    override var screen: Screen = startScreen
        set(value) {
            screen?.apply {
                backStack.push(this)
                pause()
                dispose()
            }

            value.resume()
            value.update(0f)

            field = value
        }

    override fun back() {
        val previous = backStack.pop()
        screen = previous
        backStack.pop()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        val frameBufferSize = if (isLandscape) gameSizeLandscape else gameSizePortrait

        frameBuffer = Bitmap.createBitmap(
            frameBufferSize.width.toInt(),
            frameBufferSize.height.toInt(),
            Bitmap.Config.RGB_565
        )

        graphics = AndroidGraphics(requireActivity().assets, frameBuffer)
        fileIO = AndroidFileIO(requireContext())
        audio = AndroidAudio(requireActivity())
        renderView = AndroidRenderView(this, frameBuffer, true)
        return renderView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val parentView = view.parent as View
        val scale = Size(
            frameBuffer.width / parentView.width.toFloat(),
            frameBuffer.height / parentView.height.toFloat(),
        )

        input = AndroidInput(requireContext(), renderView, scale)
        screen = startScreen
    }

    override fun onResume() {
        super.onResume()
        screen.resume()
        renderView.resume()
    }

    override fun onPause() {
        super.onPause()
        renderView.pause()
        screen.pause()
        if (isRemoving) screen.dispose()
    }
}
