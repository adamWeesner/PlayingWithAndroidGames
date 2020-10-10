package com.weesnerDevelopent.superJumper.screens

import com.weesnerDevelopent.superJumper.Assets
import com.weesnerDevelopent.superJumper.Assets.baseSize
import com.weesnerDevelopent.superJumper.Assets.windowSize
import com.weesnerDevelopent.superJumper.Settings
import com.weesnerDevelopment.gameEngine.math.*
import com.weesnerDevelopment.gameEngine.objects.Rectangle
import com.weesnerDevelopment.gameEngine.objects.Transform.Companion.inRectangle
import com.weesnerDevelopment.openGlGameEngine.GlGame
import javax.microedition.khronos.opengles.GL10
import javax.microedition.khronos.opengles.GL10.*

class ScreenHighScores(
    game: GlGame
) : BaseScreen(game) {
    var backBounds = Rectangle(Vector2D(0, 0), baseSize * 2)
    var xOffset: Number = 0
    val highScores = List(Settings.highscores.size) {
        val scoreString = "${it + 1}. ${Settings.highscores[it]}"
        xOffset = max(scoreString.length * Assets.font.glyphSize.width, xOffset)
        scoreString
    }.reversed().also {
        xOffset = (windowSize.width / 2) - xOffset / 2
    }

    override fun onTouchUp(deltaTime: Float, position: Vector2D) {
        if (position.inRectangle(backBounds)) game.screen = ScreenMainMenu(game)
    }

    override fun GL10.draw() {
        batcher.batch(Assets.background) {
            draw(windowSize.toVector2D() / 2, windowSize, Assets.backgroundRegion)
        }

        glEnable(GL_BLEND)
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)

        batcher.batch(Assets.items) {
            draw(Vector2D(windowSize.width / 2, 360), Size(300, 33), Assets.highScoresRegion)

            var middleHeight = windowSize.height / 2
            for(score in highScores){
                Assets.font.draw(batcher, score, Vector2D(xOffset, middleHeight))
                middleHeight += Assets.font.glyphSize.height
            }

            batcher.draw(Vector2D(32, 32), baseSize * 2, Assets.arrow)
        }
        glDisable(GL_BLEND)
    }
}