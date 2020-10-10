package com.weesnerDevelopent.superJumper

import com.weesnerDevelopent.superJumper.objects.BobState
import com.weesnerDevelopent.superJumper.objects.PlatformState
import com.weesnerDevelopment.gameEngine.math.Size
import com.weesnerDevelopment.gameEngine.math.compareTo
import com.weesnerDevelopment.openGlGameEngine.Animation
import com.weesnerDevelopment.openGlGameEngine.Camera2D
import com.weesnerDevelopment.openGlGameEngine.GlGraphics
import com.weesnerDevelopment.openGlGameEngine.SpriteBatcher
import javax.microedition.khronos.opengles.GL10.*


class WorldRenderer(
    val graphics: GlGraphics,
    val batcher: SpriteBatcher,
    val world: World
) {
    val frustum = Size(10, 15)
    val camera = Camera2D(graphics, frustum)

    fun render() {
        if (world.bob.position.y > camera.position.y)
            camera.position.y = world.bob.position.y

        camera.setViewportAndMatrices()
        renderBackground()
        renderObjects()
    }

    private fun renderBackground() {
        batcher.batch(Assets.background) {
            draw(camera.position, frustum, Assets.backgroundRegion)
        }
    }

    private fun renderObjects() {
        graphics.gl.apply {
            glEnable(GL_BLEND)
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)

            batcher.batch(Assets.items) {
                renderBob()
                renderPlatforms()
                renderItems()
                renderSquirrels()
                renderCastle()
            }
            glDisable(GL_BLEND)
        }
    }

    private fun renderBob() {
        val keyFrame = when (world.bob.state) {
            BobState.Jump -> Assets.bobJump.getKeyFrame(
                world.bob.startTime,
                Animation.State.Looping
            )
            BobState.Fall -> Assets.bobFall.getKeyFrame(
                world.bob.startTime,
                Animation.State.Looping
            )
            BobState.Hit -> Assets.bobHit
        }
        val side = if (world.bob.velocity.x < 0) -1 else 1
        batcher.draw(world.bob.position, Size(side, 1), keyFrame)
    }

    private fun renderPlatforms() {
        for (platform in world.platforms) {
            var keyFrame = Assets.platform
            if (platform.state == PlatformState.Pulverizing)
                keyFrame = Assets.breakingPlatform.getKeyFrame(platform.startTime, Animation.State.NonLooping)

            batcher.draw(platform.position, platform.size, keyFrame)
        }
    }

    private fun renderItems() {
        for (spring in world.springs)
            batcher.draw(spring.position, Size(1, 1), Assets.spring)

        for (coin in world.coins) {
            val keyFrame = Assets.coinAnim.getKeyFrame(coin.startTime, Animation.State.Looping)
            batcher.draw(coin.position, Size(1, 1), keyFrame)
        }
    }

    private fun renderSquirrels() {
        for (squirrel in world.squirrels) {
            val keyFrame =
                Assets.squirrelFly.getKeyFrame(squirrel.startTime, Animation.State.Looping)
            val side = if (squirrel.velocity.x < 0) -1 else 1
            batcher.draw(squirrel.position, Size(side, 1), keyFrame)
        }
    }

    private fun renderCastle() {
        batcher.draw(world.castle.position, Size(2, 2), Assets.castle)
    }
}