package com.weesnerDevelopent.superJumper.screens

import com.weesnerDevelopent.superJumper.Assets
import com.weesnerDevelopent.superJumper.Assets.baseSize
import com.weesnerDevelopent.superJumper.Assets.windowSize
import com.weesnerDevelopment.gameEngine.math.Size
import com.weesnerDevelopment.gameEngine.math.Vector2D
import com.weesnerDevelopment.gameEngine.math.minus
import com.weesnerDevelopment.gameEngine.objects.Rectangle
import com.weesnerDevelopment.gameEngine.objects.Transform.Companion.inRectangle
import com.weesnerDevelopment.openGlGameEngine.GlGame
import com.weesnerDevelopment.openGlGameEngine.Texture
import com.weesnerDevelopment.openGlGameEngine.TextureRegion
import javax.microedition.khronos.opengles.GL10
import javax.microedition.khronos.opengles.GL10.*

class ScreenHelp(
    game: GlGame
) : ScreenHelpBase(game, 1) {
    override fun nextScreen() {
        game.screen = ScreenHelp2(game)
    }
}

class ScreenHelp2(
    game: GlGame
) : ScreenHelpBase(game, 2) {
    override fun nextScreen() {
        game.screen = ScreenHelp3(game)
    }
}

class ScreenHelp3(
    game: GlGame
) : ScreenHelpBase(game, 3) {
    override fun nextScreen() {
        game.screen = ScreenHelp4(game)
    }
}

class ScreenHelp4(
    game: GlGame
) : ScreenHelpBase(game, 4) {
    override fun nextScreen() {
        game.screen = ScreenHelp5(game)
    }
}

class ScreenHelp5(
    game: GlGame
) : ScreenHelpBase(game, 5) {
    override fun nextScreen() {
        game.screen = ScreenMainMenu(game)
    }
}

sealed class ScreenHelpBase(
    override val game: GlGame,
    private val number: Int,
) : BaseScreen(game) {
    lateinit var helpImage: Texture
    lateinit var helpRegion: TextureRegion

    override val maxSpritesToBatch: Int = 1

    val nextBounds = Rectangle(Vector2D(windowSize.width - 64, 0), baseSize * 2)

    override fun resume() {
        super.resume()
        game.apply {
            helpImage = Texture(this, "help$number-sj.png")
            helpRegion = TextureRegion(helpImage, Vector2D(0, 0), windowSize)
        }
    }

    override fun onTouchUp(deltaTime: Float, position: Vector2D) {
        if (position.inRectangle(nextBounds)) {
            Assets.playSound(Assets.clickSound)
            nextScreen()
        }
    }

    override fun GL10.draw() {
        batcher.batch(helpImage) {
            draw(Vector2D(160, 240), windowSize, helpRegion)
        }

        glEnable(GL_BLEND)
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)

        batcher.batch(Assets.items) {
            draw(Vector2D(windowSize.width - 32, 32), Size(-64, 64), Assets.arrow)
        }

        glDisable(GL_BLEND)
    }

    override fun pause() {
        helpImage.dispose()
    }

    open fun nextScreen() {}
}