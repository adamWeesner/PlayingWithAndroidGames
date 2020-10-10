package com.weesnerDevelopment.openGlGameEngine.samples

import com.weesnerDevelopment.gameEngine.game.Screen
import com.weesnerDevelopment.gameEngine.input.Input
import com.weesnerDevelopment.gameEngine.math.*
import com.weesnerDevelopment.gameEngine.objects.DynamicGameObject
import com.weesnerDevelopment.gameEngine.objects.GameObject
import com.weesnerDevelopment.gameEngine.objects.SpatialHashGrid
import com.weesnerDevelopment.gameEngine.objects.StaticGameObject
import com.weesnerDevelopment.openGlGameEngine.*
import javax.microedition.khronos.opengles.GL10.*
import kotlin.random.Random


class TextureAtlasBatchSample : GlGame() {
    override var startScreen: Screen = TextureAtlasBatchScreen(this)
}

private class TextureAtlasBatchScreen(
    private val game: GlGame
) : Screen(game) {
    val gravity = Vector2D(0, -10)
    var touchPosition = Vector2D(0, 0)
    val frustum = Size(4.48, 9.6)

    val targets = arrayListOf<StaticGameObject>()
    val cannon = GravityCannon5(Vector2D(2.2, .3), Size(1, .5))
    val ball = DynamicGameObject(Vector2D(0, 0), Size(.2, .2))

    lateinit var grid: SpatialHashGrid
    lateinit var camera: Camera2D
    lateinit var texture: Texture
    lateinit var batcher: SpriteBatcher

    val cannonRegion: TextureRegion by lazy { TextureRegion(texture, Vector2D(0, 0), Size(64, 32)) }
    val ballRegion: TextureRegion by lazy { TextureRegion(texture, Vector2D(0, 32), Size(16, 16)) }
    val bobRegion: TextureRegion by lazy { TextureRegion(texture, Vector2D(32, 32), Size(32, 32)) }

    override fun resume() {
        grid = SpatialHashGrid(frustum, 2.5)

        targets.clear()
        for (i in 0 until 20)
            targets.add(
                StaticGameObject(
                    Vector2D(
                        Random.nextFloat() * frustum.width,
                        Random.nextFloat() * frustum.height
                    ),
                    Size(.5, .5)
                ).also {
                    grid.insertStaticObject(it)
                }
            )

        camera = Camera2D(game.glGraphics, frustum)
        batcher = SpriteBatcher(game.glGraphics, 100)

        texture = Texture(game, "atlas.png")
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
        game.glGraphics.gl.apply {
            glClear(GL_COLOR_BUFFER_BIT)
            camera.setViewportAndMatrices()

            glEnable(GL_BLEND)
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
            glEnable(GL_TEXTURE_2D)
        }

        batcher.batch(texture) {
            for (target in targets)
                draw(target.position, Size(.5, .5), bobRegion)

            draw(ball.position, Size(.2, .2), ballRegion)
            draw(cannon.position, Size(1, .5), cannon.angle, cannonRegion)
        }
    }
}

private data class GravityCannon5(
    override val position: Vector2D,
    override val size: Size
) : GameObject() {
    var angle: Number = 0

    fun adjustAngle(touch: Vector2D) {
        angle = (touch - position).angle()
    }
}