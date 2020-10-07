package com.weesnerDevelopment.openGlGameEngine

import com.weesnerDevelopment.gameEngine.math.*
import com.weesnerDevelopment.gameEngine.util.Size
import javax.microedition.khronos.opengles.GL10.GL_MODELVIEW
import javax.microedition.khronos.opengles.GL10.GL_PROJECTION

class Camera2D(
    private val glGraphics: GlGraphics,
    private val frustumSize: Size
) {
    var position = Vector(frustumSize.width / 2f, frustumSize.height / 2f)
    var zoom: Number = 1f

    fun setViewportAndMatrices() {
        glGraphics.gl.apply {
            glViewport(0, 0, glGraphics.size.width.toInt(), glGraphics.size.height.toInt())
            glMatrixMode(GL_PROJECTION)
            glLoadIdentity()
            glOrthof(
                (position.x - frustumSize.width * zoom / 2f).toFloat(),
                (position.x + frustumSize.width * zoom / 2f).toFloat(),
                (position.y - frustumSize.height * zoom / 2f).toFloat(),
                (position.y + frustumSize.height * zoom / 2f).toFloat(),
                1f,
                -1f
            )
            glMatrixMode(GL_MODELVIEW)
            glLoadIdentity()
        }
    }

    fun touchToWorld(touch: Vector) {
        touch.x = (touch.x / glGraphics.size.width) * frustumSize.width * zoom
        touch.y = (1 - touch.y / glGraphics.size.height) * frustumSize.height * zoom

        touch + position - Vector(frustumSize.width * zoom / 2f, frustumSize.height * zoom / 2f)
    }
}
