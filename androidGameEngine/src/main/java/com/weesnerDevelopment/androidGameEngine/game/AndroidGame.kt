package com.weesnerDevelopment.androidGameEngine.game

import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.weesnerDevelopment.androidGameEngine.audio.AndroidAudio
import com.weesnerDevelopment.gameEngine.audio.Audio
import com.weesnerDevelopment.androidGameEngine.graphics.AndroidGraphics
import com.weesnerDevelopment.androidGameEngine.graphics.AndroidRenderView
import com.weesnerDevelopment.gameEngine.graphics.Graphics
import com.weesnerDevelopment.androidGameEngine.input.AndroidInput
import com.weesnerDevelopment.gameEngine.input.Input
import com.weesnerDevelopment.gameEngine.util.Size
import com.weesnerDevelopment.androidGameEngine.file.AndroidFileIO
import com.weesnerDevelopment.gameEngine.file.FileIO
import com.weesnerDevelopment.gameEngine.game.Game
import com.weesnerDevelopment.gameEngine.game.Screen
import com.weesnerDevelopment.gameEngine.math.Vector
import com.weesnerDevelopment.gameEngine.math.div
import java.util.*

abstract class AndroidGame : AppCompatActivity(), Game {
    private lateinit var renderView: AndroidRenderView

    private val defaultSize = Vector(320, 480)
    private val gameSizePortrait = Size(defaultSize.x, defaultSize.y)
    private val gameSizeLandscape = Size(defaultSize.y, defaultSize.x)

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

        val frameBufferSize = if (isLandscape) gameSizeLandscape else gameSizePortrait

        val frameBuffer = Bitmap.createBitmap(
            frameBufferSize.width.toInt(),
            frameBufferSize.height.toInt(),
            Bitmap.Config.RGB_565
        )

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val scale = Size(
            frameBufferSize.width / displayMetrics.widthPixels.toFloat(),
            frameBufferSize.height / displayMetrics.heightPixels.toFloat(),
        )

        renderView = AndroidRenderView(this, frameBuffer, true)
        graphics = AndroidGraphics(assets, frameBuffer)
        fileIO = AndroidFileIO(this)
        audio = AndroidAudio(this)
        input = AndroidInput(this, renderView, scale)
        screen = startScreen
        setContentView(renderView)
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
        if (isFinishing) screen.dispose()
    }
}
