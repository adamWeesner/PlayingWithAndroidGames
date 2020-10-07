package com.weesnerDevelopment.openGlGameEngine.samples

import com.weesnerDevelopment.gameEngine.game.Screen
import com.weesnerDevelopment.gameEngine.math.Vector
import com.weesnerDevelopment.gameEngine.math.div
import com.weesnerDevelopment.gameEngine.math.minus
import com.weesnerDevelopment.gameEngine.math.times
import com.weesnerDevelopment.gameEngine.util.Size
import com.weesnerDevelopment.openGlGameEngine.GlGame
import com.weesnerDevelopment.openGlGameEngine.Vertices
import javax.microedition.khronos.opengles.GL10.*

class CannonSample : GlGame() {
    override var startScreen: Screen = CannonScreen(this)
}

private class CannonScreen(
    private val game: GlGame
) : Screen(
    game
) {
    val frustum = Size(4.48, 9.6)
    lateinit var vertices: Vertices
    val cannon = Cannon()
    var touchPosition = Vector.zero

    override fun resume() {
        game.glGraphics.apply {
            gl.glViewport(0, 0, size.width.toInt(), size.height.toInt())
            gl.glMatrixMode(GL_PROJECTION)
            gl.glLoadIdentity()

            gl.glOrthof(0f, frustum.width.toFloat(), 0f, frustum.height.toFloat(), 1f, -1f)
        }
        vertices = Vertices(
            glGraphics = game.glGraphics,
            maxVertices = 3,
            maxIndices = 0,
            hasColor = false,
            hasTexCoords = false
        ).apply {
            setVertices(
                floatArrayOf(
                    -0.5f, -0.5f,
                    0.5f, 0.0f,
                    -0.5f, 0.5f
                )
            )
        }
    }

    override fun update(deltaTime: Float) {
        val touchEvents = game.input.getTouchEvents()
        game.input.getKeyEvents()

        for (i in touchEvents.indices) {
            if (touchEvents.isEmpty()) return

            val touch = touchEvents[i].position

            touchPosition.x = (touch.x / game.glGraphics.size.width.toFloat()) * frustum.width
            touchPosition.y = ((1 as Number) - touch.y / game.glGraphics.size.height.toFloat()) * frustum.height

            cannon.adjustAngle(touchPosition)
        }
    }

    override fun present(deltaTime: Float) {
        game.glGraphics.apply {
            gl.glClear(GL_COLOR_BUFFER_BIT)

            gl.glMatrixMode(GL_MODELVIEW)
            gl.glLoadIdentity()

            gl.glTranslatef(cannon.position.x.toFloat(), cannon.position.y.toFloat(), 0f)
            gl.glRotatef(cannon.angle.toFloat(), 0f, 0f, 1f)
            vertices.bind()
            vertices.draw(GL_TRIANGLES, 0, 3)
            vertices.unbind()
        }
    }
}

private data class Cannon(
    val position: Vector = Vector(2.4, .5)
) {
    var angle: Number = 0

    fun adjustAngle(touch: Vector) {
        angle = (Vector.minus(touch, position)).angle()
    }
}