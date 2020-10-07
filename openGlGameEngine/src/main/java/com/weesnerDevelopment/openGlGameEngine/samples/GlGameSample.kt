package com.weesnerDevelopment.openGlGameEngine.samples

import com.weesnerDevelopment.gameEngine.game.Screen
import com.weesnerDevelopment.openGlGameEngine.GlGame
import javax.microedition.khronos.opengles.GL10.GL_COLOR_BUFFER_BIT
import kotlin.random.Random

class GlGameSample : GlGame() {
    override var startScreen: Screen = TestScreen(this)
}

private class TestScreen(
    private val game: GlGame
) : Screen(game) {
    private val random: Random = Random

    override fun present(deltaTime: Float) {
        game.glGraphics.gl.apply {
            glClearColor(random.nextFloat(), random.nextFloat(), random.nextFloat(), 1f)
            glClear(GL_COLOR_BUFFER_BIT)
        }
    }

    override fun update(deltaTime: Float) {
        game.input.getTouchEvents()
        game.input.getKeyEvents()
    }
}