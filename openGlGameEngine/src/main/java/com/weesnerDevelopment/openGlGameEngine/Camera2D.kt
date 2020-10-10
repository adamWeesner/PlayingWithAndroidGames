package com.weesnerDevelopment.openGlGameEngine

import com.weesnerDevelopment.gameEngine.math.*
import com.weesnerDevelopment.gameEngine.math.Size
import javax.microedition.khronos.opengles.GL10.GL_MODELVIEW
import javax.microedition.khronos.opengles.GL10.GL_PROJECTION

class Camera2D(
    private val glGraphics: GlGraphics,
    private val frustumSize: Size
) {
    var position = frustumSize.toVector2D() / 2
    var zoom: Number = 1

    fun setViewportAndMatrices() {
        glGraphics.gl.apply {
            glViewport(0, 0, glGraphics.size.width.toInt(), glGraphics.size.height.toInt())
            glMatrixMode(GL_PROJECTION)
            glLoadIdentity()
            glOrthof(
                (position.x - frustumSize.width * zoom / 2).toFloat(),
                (position.x + frustumSize.width * zoom / 2).toFloat(),
                (position.y - frustumSize.height * zoom / 2).toFloat(),
                (position.y + frustumSize.height * zoom / 2).toFloat(),
                1f,
                -1f
            )
            glMatrixMode(GL_MODELVIEW)
            glLoadIdentity()
        }
    }

    fun touchToWorld(touch: Vector2D) {
        touch.x = (touch.x / glGraphics.size.width) * frustumSize.width * zoom
        touch.y = (1 - touch.y / glGraphics.size.height) * frustumSize.height * zoom

        val updatedPos = position - frustumSize.toVector2D() * zoom / 2
        touch.x += updatedPos.x
        touch.y += updatedPos.y
    }
}
