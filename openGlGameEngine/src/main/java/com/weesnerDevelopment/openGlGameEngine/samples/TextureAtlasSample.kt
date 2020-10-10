package com.weesnerDevelopment.openGlGameEngine.samples

import com.weesnerDevelopment.gameEngine.game.Screen
import com.weesnerDevelopment.gameEngine.input.Input
import com.weesnerDevelopment.gameEngine.math.*
import com.weesnerDevelopment.gameEngine.objects.DynamicGameObject
import com.weesnerDevelopment.gameEngine.objects.GameObject
import com.weesnerDevelopment.gameEngine.objects.SpatialHashGrid
import com.weesnerDevelopment.gameEngine.objects.StaticGameObject
import com.weesnerDevelopment.gameEngine.util.Size
import com.weesnerDevelopment.openGlGameEngine.*
import javax.microedition.khronos.opengles.GL10.*
import kotlin.random.Random


class TextureAtlasSample : GlGame() {
    override var startScreen: Screen = TextureAtlasScreen(this)
}

private class TextureAtlasScreen(
    private val game: GlGame
) : Screen(game) {
    val gravity = Vector(0, -10)
    var touchPosition = Vector.zero
    val frustum = Size(4.48, 9.6)

    val targets = arrayListOf<StaticGameObject>()
    val cannon = GravityCannon4(Vector(2.2, .3), Size(1, .5))
    val ball = DynamicGameObject(Vector.zero, Size(.2))

    lateinit var grid: SpatialHashGrid
    lateinit var camera: Camera2D
    lateinit var texture: Texture

    lateinit var cannonVertices: Vertices
    lateinit var ballVertices: Vertices
    lateinit var targetVertices: Vertices

    override fun resume() {
        texture = Texture(game, "atlas.png")
        camera = Camera2D(game.glGraphics, frustum)

        grid = SpatialHashGrid(frustum, 2.5)

        targets.clear()
        for (i in 0 until 20)
            targets.add(
                StaticGameObject(
                    Vector(
                        Random.nextFloat() * frustum.width,
                        Random.nextFloat() * frustum.height
                    ),
                    Size(.5)
                ).also {
                    grid.insertStaticObject(it)
                }
            )

        cannonVertices = Vertices(
            glGraphics = game.glGraphics,
            maxVertices = 4,
            maxIndices = 6,
            hasColor = false,
            hasTexCoords = true
        ).apply {
            setVertices(
                floatArrayOf(
                    -0.5f, -0.25f, 0.0f, 0.5f,
                    0.5f, -0.25f, 1.0f, 0.5f,
                    0.5f, 0.25f, 1.0f, 0.0f,
                    -0.5f, 0.25f, 0.0f, 0.0f
                )
            )
            setIndices(shortArrayOf(0, 1, 2, 2, 3, 0))
        }

        ballVertices = Vertices(
            glGraphics = game.glGraphics,
            maxVertices = 4,
            maxIndices = 6,
            hasColor = false,
            hasTexCoords = true
        ).apply {
            setVertices(
                floatArrayOf(
                    -0.1f, -0.1f, 0.0f, 0.75f,
                    0.1f, -0.1f, 0.25f, 0.75f,
                    0.1f, 0.1f, 0.25f, 0.5f,
                    -0.1f, 0.1f, 0.0f, 0.5f
                )
            )
            setIndices(shortArrayOf(0, 1, 2, 2, 3, 0))
        }

        targetVertices = Vertices(
            glGraphics = game.glGraphics,
            maxVertices = 4,
            maxIndices = 6,
            hasColor = false,
            hasTexCoords = true
        ).apply {
            setVertices(
                floatArrayOf(
                    -0.25f, -0.25f, 0.5f, 1.0f,
                    0.25f, -0.25f, 1.0f, 1.0f,
                    0.25f, 0.25f, 1.0f, 0.5f,
                    -0.25f, 0.25f, 0.5f, 0.5f
                )
            )
            setIndices(shortArrayOf(0, 1, 2, 2, 3, 0))
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
                    bounds.lowerLeft = Vector(ball.position.x - .1, ball.position.y - .1)
                }
            }
        }

        ball.apply {
            velocity + Vector.times(gravity, deltaTime)
            position + Vector.times(velocity, deltaTime)
            bounds.lowerLeft + Vector.times(velocity, deltaTime)
        }

        val colliders = grid.getPotentialColliders(ball)
        for (collider in colliders) {
            if (ball.bounds.isOverlapping(collider.bounds)) {
                grid.removeObject(collider)
                targets.remove(collider)
            }
        }

        if (ball.position.y > 0) {
            camera.position = Vector(ball.position.x, ball.position.y)
            camera.zoom = 1 + camera.position.y / frustum.height
        } else {
            camera.position = Vector(frustum.width / 2, frustum.height / 2)
            camera.zoom = 1
        }
    }

    override fun present(deltaTime: Float) {
        game.glGraphics.apply {
            gl.glClear(GL_COLOR_BUFFER_BIT)
            camera.setViewportAndMatrices()

            // draw targets
            gl.glEnable(GL_BLEND)
            gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
            gl.glEnable(GL_TEXTURE_2D)
            texture.bind()

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
            ballVertices.bind()
            ballVertices.draw(GL_TRIANGLES, 0, 6)
            ballVertices.unbind()

            // draw cannon
            gl.glLoadIdentity()
            gl.glTranslatef(cannon.position.x.toFloat(), cannon.position.y.toFloat(), 0f)
            gl.glRotatef(cannon.angle.toFloat(), 0f, 0f, 1f)
            cannonVertices.bind()
            cannonVertices.draw(GL_TRIANGLES, 0, 6)
            cannonVertices.unbind()
        }
    }
}

private data class GravityCannon4(
    override val position: Vector,
    override val size: Size
) : GameObject() {
    var angle: Number = 0

    fun adjustAngle(touch: Vector) {
        angle = (Vector.minus(touch, position)).angle()
    }
}