package com.weesnerDevelopent.superJumper.screens

import com.weesnerDevelopent.superJumper.Assets
import com.weesnerDevelopent.superJumper.Assets.windowSize
import com.weesnerDevelopent.superJumper.Settings
import com.weesnerDevelopment.gameEngine.math.Size
import com.weesnerDevelopment.gameEngine.math.Vector2D
import com.weesnerDevelopment.gameEngine.math.div
import com.weesnerDevelopment.gameEngine.math.minus
import com.weesnerDevelopment.gameEngine.objects.Rectangle
import com.weesnerDevelopment.gameEngine.objects.Transform.Companion.inRectangle
import com.weesnerDevelopment.openGlGameEngine.GlGame
import javax.microedition.khronos.opengles.GL10

class ScreenMainMenu(
    override val game: GlGame
) : BaseScreen(game) {
    val soundBounds = Rectangle(Vector2D(0, 0), Assets.baseSize * 2)
    val playBounds = Rectangle(Vector2D(windowSize.width / 2 - 150, 200 + 18), Size(300, 36))
    val highscoresBounds = Rectangle(Vector2D(windowSize.width / 2 - 150, 200 - 18), Size(300, 36))
    val helpBounds = Rectangle(Vector2D(windowSize.width / 2 - 150, 200 - 18 - 36), Size(300, 36))

    override fun onTouchUp(deltaTime: Float, position: Vector2D) {
        when {
            position.inRectangle(playBounds) -> {
                Assets.playSound(Assets.clickSound)
                game.screen = ScreenGame(game)
            }
            position.inRectangle(highscoresBounds) -> {
                Assets.playSound(Assets.clickSound)
                game.screen = ScreenHighScores(game)
            }
            position.inRectangle(helpBounds) -> {
                Assets.playSound(Assets.clickSound)
                game.screen = ScreenHelp(game)
            }
            position.inRectangle(soundBounds) -> {
                Assets.playSound(Assets.clickSound)
                Settings.soundEnabled = !Settings.soundEnabled

                if (Settings.soundEnabled) Assets.music.play()
                else Assets.music.pause()
            }
        }
    }

    override fun GL10.draw() {
        batcher.batch(Assets.background) {
            draw(Vector2D(160, 240), Assets.windowSize, Assets.backgroundRegion)
        }

        glEnable(GL10.GL_BLEND)
        glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA)

        batcher.batch(Assets.items) {
            draw(Vector2D(160, Assets.windowSize.height - 10 - 71), Size(274, 142), Assets.logo)
            draw(Vector2D(160, 200), Size(300, 110), Assets.mainMenu)
            draw(
                Vector2D(32, 32),
                Assets.baseSize * 2,
                if (Settings.soundEnabled) Assets.soundOn else Assets.soundOff
            )
        }
        glDisable(GL10.GL_BLEND)
    }

    override fun pause() {
        Settings.save(game.fileIO)
    }
}