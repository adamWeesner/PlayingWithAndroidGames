package com.weesnerDevelopment.openGlGameEngine.samples

import com.weesnerDevelopment.gameEngine.game.Screen
import com.weesnerDevelopment.gameEngine.math.Vector
import com.weesnerDevelopment.gameEngine.math.compareTo
import com.weesnerDevelopment.gameEngine.math.plus
import com.weesnerDevelopment.gameEngine.objects.GameObject
import com.weesnerDevelopment.gameEngine.util.Size
import com.weesnerDevelopment.openGlGameEngine.*
import javax.microedition.khronos.opengles.GL10
import kotlin.random.Random

private val world = Size(4.48, 9.6)

class CavemanSample : GlGame() {
    override var startScreen: Screen = CavemanScreen(this)
}

private class CavemanScreen(
    private val game: GlGame
) : Screen(game) {
    val numCavemen = 10
    val frustum = Size(4.48, 9.6)
    val cavemen = arrayListOf<Caveman>()

    lateinit var camera: Camera2D
    lateinit var texture: Texture
    lateinit var batcher: SpriteBatcher
    lateinit var walkAnim: Animation

    override fun resume() {
        cavemen.clear()
        for (i in 0 until numCavemen)
            cavemen.add(Caveman(size = Size(1)))

        camera = Camera2D(game.glGraphics, frustum)
        batcher = SpriteBatcher(game.glGraphics, numCavemen)

        texture = Texture(game, "walkanim.png")
        walkAnim = Animation(
            .2,
            TextureRegion(texture, Vector(0, 0), Size(64)),
            TextureRegion(texture, Vector(64, 0), Size(64)),
            TextureRegion(texture, Vector(128, 0), Size(64)),
            TextureRegion(texture, Vector(192, 0), Size(64))
        )
    }

    override fun update(deltaTime: Float) {
        game.input.getTouchEvents()
        game.input.getKeyEvents()

        for (caveman in cavemen) caveman.update(deltaTime)
    }

    override fun present(deltaTime: Float) {
        game.glGraphics.gl.apply {
            glClear(GL10.GL_COLOR_BUFFER_BIT)
            camera.setViewportAndMatrices()

            glEnable(GL10.GL_BLEND)
            glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA)
            glEnable(GL10.GL_TEXTURE_2D)
        }

        batcher.batch(texture) {
            for (caveman in cavemen) {
                val keyFrame = walkAnim.getKeyFrame(caveman.walkingTime, Animation.State.Looping)
                draw(caveman.position, Size(if (caveman.velocity.x < 0) 1 else -1, 1), keyFrame)
            }
        }
    }
}

private data class Caveman(
    override var position: Vector = Vector(
        Random.nextFloat() * world.width.toFloat(),
        Random.nextFloat() * world.height.toFloat(),
    ),
    override val size: Size
) : GameObject() {
    var walkingTime: Number = Random.nextFloat() * 10
    val velocity = Vector(if (Random.nextFloat() > .5) -.5 else .5, 0)

    fun update(deltaTime: Number) {
        position + Vector.times(velocity, deltaTime)
        if (position.x < 0) position.x = world.width
        if (position.x > world.width) position.x = 0
        walkingTime += deltaTime
    }
}