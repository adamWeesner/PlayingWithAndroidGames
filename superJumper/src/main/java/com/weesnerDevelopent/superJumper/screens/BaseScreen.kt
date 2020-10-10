package com.weesnerDevelopent.superJumper.screens

import com.weesnerDevelopent.superJumper.Assets
import com.weesnerDevelopment.gameEngine.input.Input
import com.weesnerDevelopment.gameEngine.math.Vector2D
import com.weesnerDevelopment.openGlGameEngine.Camera2D
import com.weesnerDevelopment.openGlGameEngine.GlGame
import com.weesnerDevelopment.openGlGameEngine.GlScreen
import com.weesnerDevelopment.openGlGameEngine.SpriteBatcher
import javax.microedition.khronos.opengles.GL10

open class BaseScreen(
    protected open val game: GlGame
) : GlScreen(game) {
    lateinit var guiCamera: Camera2D
    lateinit var batcher: SpriteBatcher

    override fun resume() {
        game.glGraphics.apply {
            guiCamera = Camera2D(this, Assets.windowSize)
            batcher = SpriteBatcher(this, maxSpritesToBatch)
        }
    }

    override fun update(deltaTime: Float) {
        val touchEvents = game.input.getTouchEvents()
        game.input.getKeyEvents()

        for (i in touchEvents.indices) {
            if (touchEvents.isEmpty()) return

            val event = touchEvents[i]
            val touch = event.position.copy()

            guiCamera.touchToWorld(touch)

            onTouch(deltaTime, touch)

            if (event.type == Input.TouchEventType.Up) onTouchUp(deltaTime, touch)
        }
    }

    override fun present(deltaTime: Float) {
        game.glGraphics.gl.apply {
            glClear(GL10.GL_COLOR_BUFFER_BIT)
            guiCamera.setViewportAndMatrices()

            glEnable(GL10.GL_TEXTURE_2D)

            draw()
        }
    }

    protected open fun GL10.draw() {}
    protected open val maxSpritesToBatch: Int = 100

    protected open fun onTouch(deltaTime: Float, position: Vector2D) {}
    protected open fun onTouchUp(deltaTime: Float, position: Vector2D) {}
}