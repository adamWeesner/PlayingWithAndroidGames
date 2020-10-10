package com.weesnerDevelopment.openGlGameEngine.samples

import com.weesnerDevelopment.gameEngine.game.Screen
import com.weesnerDevelopment.gameEngine.input.Input
import com.weesnerDevelopment.gameEngine.math.*
import com.weesnerDevelopment.openGlGameEngine.GlGame
import com.weesnerDevelopment.openGlGameEngine.Vertices
import javax.microedition.khronos.opengles.GL10.*

class CannonGravitySample : GlGame() {
    override var startScreen: Screen = CannonGravityScreen(this)
}

private class CannonGravityScreen(
    private val game: GlGame
) : Screen(
    game
) {
    val frustum = Size(4.48, 9.6)

    lateinit var cannonVertices: Vertices
    lateinit var ballVertices: Vertices

    val cannon = GravityCannon()
    val ball = Ball()

    val gravity = Vector2D(0, -10)
    val touchPosition = Vector2D(0, 0)

    override fun resume() {
        game.glGraphics.apply {
            gl.glViewport(0, 0, size.width.toInt(), size.height.toInt())
            gl.glMatrixMode(GL_PROJECTION)
            gl.glLoadIdentity()

            gl.glOrthof(0f, frustum.width.toFloat(), 0f, frustum.height.toFloat(), 1f, -1f)
        }
        cannonVertices = Vertices(
            glGraphics = game.glGraphics,
            maxVertices = 3,
            maxIndices = 0,
            hasColor = false,
            hasTexCoords = false
        ).apply {
            setVertices(
                floatArrayOf(
                    -0.25f, -0.25f,
                    0.25f, 0.0f,
                    -0.25f, 0.25f
                )
            )
        }

        ballVertices = Vertices(
            glGraphics = game.glGraphics,
            maxVertices = 4,
            maxIndices = 6,
            hasColor = false,
            hasTexCoords = false
        ).apply {
            setVertices(
                floatArrayOf(
                    -0.1f, -0.1f,
                    0.1f, -0.1f,
                    0.1f, 0.1f,
                    -0.1f, 0.1f
                )
            )
            setIndices(
                shortArrayOf(
                    0, 1, 2, 2, 3, 0
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
            touchPosition.y =
                ((2.4 as Number) - touch.y / game.glGraphics.size.height.toFloat()) * frustum.height

            cannon.adjustAngle(touchPosition)

            if (touchEvents[i].type == Input.TouchEventType.Up) {
                val radians = cannon.angle.toRadians
                val ballSpeed = touchPosition.length()

                ball.apply {
                    position = cannon.position.copy()
                    velocity.x = cos(radians) * ballSpeed
                    velocity.y = sin(radians) * ballSpeed
                }
            }
        }

        ball.apply {
            velocity += gravity * deltaTime
            position += velocity * deltaTime
        }
    }

    override fun present(deltaTime: Float) {
        game.glGraphics.apply {
            gl.glClear(GL_COLOR_BUFFER_BIT)
            gl.glMatrixMode(GL_MODELVIEW)

            // draw ball
            gl.glLoadIdentity()
            gl.glTranslatef(ball.position.x.toFloat(), ball.position.y.toFloat(), 0f)
            gl.glColor4f(1f, 0f, 0f, 1f)
            ballVertices.bind()
            ballVertices.draw(GL_TRIANGLES, 0, 6)
            ballVertices.unbind()

            // draw cannon
            gl.glLoadIdentity()
            gl.glTranslatef(cannon.position.x.toFloat(), cannon.position.y.toFloat(), 0f)
            gl.glRotatef(cannon.angle.toFloat(), 0f, 0f, 1f)
            gl.glColor4f(1f, 1f, 1f, 1f)
            cannonVertices.bind()
            cannonVertices.draw(GL_TRIANGLES, 0, 3)
            cannonVertices.unbind()
        }
    }
}

private data class Ball(
    var position: Vector2D = Vector2D(0, 0)
) {
    var velocity = Vector2D(0, 0)
}

private data class GravityCannon(
    val position: Vector2D = Vector2D(.5, .5)
) {
    var angle: Number = 0

    fun adjustAngle(touch: Vector2D) {
        angle = (touch - position).angle()
    }
}