package com.weesnerdevelopment.playingwithgames.buildingAndroidGames.game

import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.audio.AndroidAudio
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.audio.Audio
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.file.AndroidFileIO
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.file.FileIO
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.graphics.AndroidGraphics
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.graphics.AndroidRenderView
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.graphics.Graphics
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.input.AndroidInput
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.input.Input
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.util.Size
import com.weesnerdevelopment.playingwithgames.math.div
import com.weesnerdevelopment.playingwithgames.objects.Vector
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
