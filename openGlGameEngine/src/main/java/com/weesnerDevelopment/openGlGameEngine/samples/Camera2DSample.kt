package com.weesnerDevelopment.openGlGameEngine.samples

import com.weesnerDevelopment.gameEngine.game.Screen
import com.weesnerDevelopment.gameEngine.input.Input
import com.weesnerDevelopment.gameEngine.math.*
import com.weesnerDevelopment.gameEngine.objects.DynamicGameObject
import com.weesnerDevelopment.gameEngine.objects.GameObject
import com.weesnerDevelopment.gameEngine.objects.SpatialHashGrid
import com.weesnerDevelopment.gameEngine.objects.StaticGameObject
import com.weesnerDevelopment.openGlGameEngine.Camera2D
import com.weesnerDevelopment.openGlGameEngine.GlGame
import com.weesnerDevelopment.openGlGameEngine.Vertices
import javax.microedition.khronos.opengles.GL10.GL_COLOR_BUFFER_BIT
import javax.microedition.khronos.opengles.GL10.GL_TRIANGLES
import kotlin.random.Random

class Camera2DSample : GlGame() {
    override var startScreen: Screen = Camera2DScreen(this)
}

private class Camera2DScreen(
    private val game: GlGame
) : Screen(
    game
) {
    val frustum = Size(4.48, 9.6)

    lateinit var cannonVertices: Vertices
    lateinit var ballVertices: Vertices
    lateinit var targetVertices: Vertices
    lateinit var grid: SpatialHashGrid
    lateinit var camera: Camera2D

    val targets = arrayListOf<StaticGameObject>()
    val cannon = GravityCannon3(Vector2D(0, 0), Size(1, 1))
    val ball = DynamicGameObject(Vector2D(0, 0), Size(.2, .2))

    val gravity = Vector2D(0, -10)
    var touchPosition = Vector2D(0, 0)

    override fun resume() {
        camera = Camera2D(game.glGraphics, frustum)
        targets.clear()
        grid = SpatialHashGrid(frustum, 2.5)
        for (i in 0 until 10)
            targets.add(
                StaticGameObject(
                    Vector2D(
                        Random.nextInt(frustum.width.toInt()),
                        Random.nextInt(frustum.height.toInt())
                    ),
                    Size(.5, .5)
                ).also {
                    grid.insertStaticObject(it)
                }
            )

        cannonVertices = Vertices(
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

        targetVertices = Vertices(
            glGraphics = game.glGraphics,
            maxVertices = 4,
            maxIndices = 6,
            hasColor = false,
            hasTexCoords = false
        ).apply {
            setVertices(
                floatArrayOf(
                    -0.25f, -0.25f,
                    0.25f, -0.25f,
                    0.25f, 0.25f,
                    -0.25f, 0.25f
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

            touchPosition = touch.copy()

            camera.touchToWorld(touchPosition)
            cannon.adjustAngle(touchPosition)

            if (touchEvents[i].type == Input.TouchEventType.Up) {
                val radians = cannon.angle.toRadians
                val ballSpeed = touchPosition.length() * 2

                ball.apply {
                    position = cannon.position.copy()
                    velocity.x = cos(radians) * ballSpeed
                    velocity.y = sin(radians) * ballSpeed
                    bounds.lowerLeft = Vector2D(ball.position.x - .1, ball.position.y - .1)
                }
            }
        }

        ball.apply {
            velocity += gravity * deltaTime
            position += velocity * deltaTime
            bounds.lowerLeft += velocity * deltaTime
        }

        val colliders = grid.getPotentialColliders(ball)
        for (collider in colliders) {
            if (ball.bounds.isOverlapping(collider.bounds)) {
                grid.removeObject(collider)
                targets.remove(collider)
            }
        }

        if (ball.position.y > 0) {
            camera.position = Vector2D(ball.position.x, ball.position.y)
            camera.zoom = 1 + camera.position.y / frustum.height
        } else {
            camera.position = Vector2D(frustum.width / 2, frustum.height / 2)
            camera.zoom = 1
        }
    }

    override fun present(deltaTime: Float) {
        game.glGraphics.apply {
            gl.glClear(GL_COLOR_BUFFER_BIT)
            camera.setViewportAndMatrices()

            // draw targets
            gl.glColor4f(0f, 1f, 0f, 1f)
            targetVertices.bind()
            for (target in targets) {
                gl.glLoadIdentity()
                gl.glTranslatef(target.position.x.toFloat(), target.position.y.toFloat(), 0f)
                targetVertices.draw(GL_TRIANGLES, 0, 6)
            }
            targetVertices.unbind()

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

private data class GravityCannon3(
    override val position: Vector2D,
    override val size: Size
) : GameObject() {
    var angle: Number = 0

    fun adjustAngle(touch: Vector2D) {
        angle = (touch - position).angle()
    }
}