package com.weesnerDevelopent.superJumper.screens

import com.weesnerDevelopent.superJumper.*
import com.weesnerDevelopent.superJumper.Assets.baseSize
import com.weesnerDevelopent.superJumper.Assets.windowSize
import com.weesnerDevelopment.gameEngine.math.*
import com.weesnerDevelopment.gameEngine.objects.Rectangle
import com.weesnerDevelopment.gameEngine.objects.Transform.Companion.inRectangle
import com.weesnerDevelopment.openGlGameEngine.GlGame
import javax.microedition.khronos.opengles.GL10

enum class GameState {
    Ready,
    Running,
    Paused,
    LevelEnd,
    GameOver
}

class ScreenGame(
    game: GlGame
) : BaseScreen(game) {
    lateinit var renderer: WorldRenderer

    override val maxSpritesToBatch = 1000

    var lastScore: Number = 0
    var scoreString = "score: $lastScore"
    var state = GameState.Ready

    val worldListener = object : WorldListener {
        override fun jump() = Assets.playSound(Assets.jumpSound)
        override fun highJump() = Assets.playSound(Assets.highJumpSound)
        override fun hit() = Assets.playSound(Assets.hitSound)
        override fun coin() = Assets.playSound(Assets.coinSound)
    }
    var world = World(worldListener)

    val pauseBounds =
        Rectangle(Vector2D(windowSize.width - 64, windowSize.height - 64), Size(64, 64))
    val resumeBounds =
        Rectangle(Vector2D(windowSize.width / 2 - 96, windowSize.height / 2), Size(192, 36))
    val quitBounds =
        Rectangle(Vector2D(windowSize.width / 2 - 96, windowSize.height / 2 - 36), Size(192, 36))

    override fun resume() {
        super.resume()
        renderer = WorldRenderer(game.glGraphics, batcher, world)
    }

    override fun update(deltaTime: Float) {
        var delta = deltaTime
        if (delta > .1f) delta = .1f

        if (state == GameState.Ready) updateReady()

        super.update(delta)

        when (state) {
            GameState.Running -> updateRunning(delta)
            GameState.LevelEnd -> updateLevelEnd()
            GameState.GameOver -> updateGameOver()
        }
    }

    override fun onTouchUp(deltaTime: Float, position: Vector2D) {
        when (state) {
            GameState.Running -> updateRunningTouch(position)
            GameState.Paused -> updatePaused(position)
            GameState.LevelEnd -> updateLevelEnd()
            GameState.GameOver -> updateGameOver()
        }
    }

    override fun present(deltaTime: Float) {
        game.glGraphics.gl.apply {
            glClear(GL10.GL_COLOR_BUFFER_BIT)
            glEnable(GL10.GL_TEXTURE_2D)

            renderer.render()
            guiCamera.setViewportAndMatrices()

            glEnable(GL10.GL_BLEND)
            glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA)

            draw()
        }
    }

    override fun GL10.draw() {
        batcher.batch(Assets.items) {
            when (state) {
                GameState.Ready -> presentReady()
                GameState.Running -> presentRunning()
                GameState.Paused -> presentPaused()
                GameState.LevelEnd -> presentLevelEnd()
                GameState.GameOver -> presentGameOver()
            }
        }
        glDisable(GL10.GL_BLEND)
    }

    override fun pause() {
        if (state == GameState.Running) state = GameState.Paused
    }

    private fun updateReady() {
        if (game.input.getTouchEvents().isNotEmpty()) state = GameState.Running
    }

    private fun updateRunningTouch(position: Vector2D) {
        if (position.inRectangle(pauseBounds)) {
            Assets.playSound(Assets.clickSound)
            state = GameState.Paused
        }
    }

    private fun updateRunning(deltaTime: Number) {
        world.update(deltaTime, game.input.accel.x / 20)
        if (world.score != lastScore) {
            lastScore = world.score
            scoreString = "score: $lastScore"
        }

        if (world.state == WorldState.NextLevel) state = GameState.LevelEnd

        if (world.state == WorldState.GameOver) {
            state = GameState.GameOver
            scoreString = if (lastScore >= Settings.highscores.last()) "new highscore: $lastScore"
            else "score: $lastScore"

            Settings.addScore(lastScore.toInt())
            Settings.save(game.fileIO)
        }
    }

    private fun updatePaused(position: Vector2D) {
        when {
            position.inRectangle(resumeBounds) -> {
                Assets.playSound(Assets.clickSound)
                state = GameState.Running
            }
            position.inRectangle(quitBounds) -> {
                Assets.playSound(Assets.clickSound)
                game.screen = ScreenMainMenu(game)
            }
        }
    }

    private fun updateLevelEnd() {
        world = World(worldListener)
        renderer = WorldRenderer(game.glGraphics, batcher, world)
        world.score = lastScore
        state = GameState.Ready
    }

    private fun updateGameOver() {
        game.screen = ScreenMainMenu(game)
    }

    private fun presentReady() {
        batcher.draw(
            Vector2D(windowSize.width / 2, windowSize.height / 2),
            Size(192, 32),
            Assets.ready
        )
    }

    private fun presentRunning() {
        batcher.draw(
            Vector2D(windowSize.width - 32, windowSize.height - 32),
            baseSize * 2,
            Assets.pause
        )
        Assets.font.draw(batcher, scoreString, Vector2D(16, windowSize.height - 20))
    }

    private fun presentPaused() {
        batcher.draw(
            Vector2D(windowSize.width / 2, windowSize.height / 2),
            Size(192, 96),
            Assets.pauseMenu
        )
        Assets.font.draw(batcher, scoreString, Vector2D(16, windowSize.height - 20))
    }

    private fun presentLevelEnd() {
        val topText = "the princess is ..."
        val bottomText = "in another castle!"

        val topWidth = Assets.font.glyphSize.width * topText.length
        val bottomWidth = Assets.font.glyphSize.width * bottomText.length

        Assets.font.draw(
            batcher,
            topText,
            Vector2D(windowSize.width / 2 - topWidth / 2, windowSize.height - 40)
        )
        Assets.font.draw(batcher, bottomText, Vector2D(windowSize.width / 2 - bottomWidth / 2, 40))
    }

    private fun presentGameOver() {
        batcher.draw(
            Vector2D(windowSize.width / 2, windowSize.height / 2),
            Size(160, 96),
            Assets.gameOver
        )

        val scoreWidth = Assets.font.glyphSize.width * scoreString.length
        Assets.font.draw(
            batcher,
            scoreString,
            Vector2D(windowSize.width - scoreWidth / 2, windowSize.height - 20)
        )
    }
}
