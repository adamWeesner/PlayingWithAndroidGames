package com.weesnerdevelopment.playingwithgames.buildingAndroidGames.mrNom.screens

import android.graphics.Color
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.game.Game
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.graphics.Pixmap
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.input.Input
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.mrNom.MrNomAssets
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.mrNom.Settings
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.mrNom.objects.SnakeDir
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.mrNom.objects.StainType
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.mrNom.objects.World
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.mrNom.screens.MrNomScreen.Units.large
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.mrNom.screens.MrNomScreen.Units.large1
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.mrNom.screens.MrNomScreen.Units.large4
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.mrNom.screens.MrNomScreen.Units.large7
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.mrNom.screens.MrNomScreen.Units.medium
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.mrNom.screens.MrNomScreen.Units.medium1
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.mrNom.screens.MrNomScreen.Units.medium2
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.mrNom.screens.MrNomScreen.Units.medium4
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.mrNom.screens.MrNomScreen.Units.medium5
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.mrNom.screens.MrNomScreen.Units.small
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.mrNom.screens.MrNomScreen.Units.small1
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.mrNom.screens.MrNomScreen.Units.small2
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.mrNom.screens.MrNomScreen.Units.small7
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.util.Size
import com.weesnerdevelopment.playingwithgames.math.div
import com.weesnerdevelopment.playingwithgames.math.minus
import com.weesnerdevelopment.playingwithgames.math.plus
import com.weesnerdevelopment.playingwithgames.objects.Vector


class GameScreen(
    override val game: Game
) : MrNomScreen(game) {
    enum class State {
        Ready,
        Running,
        Paused,
        GameOver
    }

    private val gameOverMenuLoc = Vector(medium4, large1)
    private val gameOverMenuSize = Vector(0, medium4)

    private var state = State.Ready
    private val world = World()
    private var oldScore: Number = 0
    private var score = "0"

    override fun update(deltaTime: Float) {
        val touchEvents = game.input.getTouchEvents()
        game.input.getKeyEvents()

        when (state) {
            State.Ready -> updateReady(touchEvents)
            State.Running -> updateRunning(touchEvents, deltaTime)
            State.Paused -> updatePaused(touchEvents)
            State.GameOver -> updateGameOver(touchEvents)
        }
    }

    override fun present(deltaTime: Float) {
        game.graphics.drawPixmap(
            MrNomAssets.background,
            Vector.zero
        )
        drawWorld(world)
        when (state) {
            State.Ready -> drawReadyUi()
            State.Running -> drawRunningUi()
            State.Paused -> drawPausedUi()
            State.GameOver -> drawGameOverUi()
        }
        drawText(
            game.graphics,
            score,
            Vector(
                game.graphics.size.width / 2 - score.length * small1 / 2,
                game.graphics.size.height - small2
            )
        )
    }

    override fun pause() {
        if (state == State.Running)
            state = State.Paused

        if (world.gameOver) {
            Settings.addScore(world.score)
            Settings.save(game.fileIO)
        }
    }

    override fun resume() {}
    override fun dispose() {}

    private fun updateReady(touchEvents: List<Input.TouchEvent>) {
        if (touchEvents.isNotEmpty())
            state = State.Running
    }

    private fun updateRunning(touchEvents: List<Input.TouchEvent>, deltaTime: Number) {
        for (i in touchEvents.indices) {
            val event = touchEvents[i]
            if (event.type == Input.TouchEventType.Up) {
                // pause button
                inBounds(
                    event,
                    Vector.zero,
                    squareSize
                ) {
                    playClick()
                    state = State.Paused
                }
            }
            if (event.type == Input.TouchEventType.Down) {
                // left button
                inBounds(
                    event,
                    backButtonLoc,
                    squareSize
                ) {
                    playClick()
                    world.snake.turnLeft()
                }

                // right button
                inBounds(
                    event,
                    forwardButtonLoc,
                    squareSize
                ) {
                    playClick()
                    world.snake.turnRight()
                }
            }
        }

        world.update(deltaTime)
        if (world.gameOver) {
            if (Settings.soundEnabled)
                MrNomAssets.bitten.play(100)
            state = State.GameOver
        }

        if (oldScore != world.score) {
            oldScore = world.score
            score = oldScore.toString()
            if (Settings.soundEnabled)
                MrNomAssets.eat.play(100)
        }
    }

    private fun updatePaused(touchEvents: List<Input.TouchEvent>) {
        for (i in touchEvents.indices) {
            val event = touchEvents[i]
            if (event.type == Input.TouchEventType.Up) {
                println("event ${event.position}")
                inBounds(
                    event,
                    Vector(medium, medium1),
                    MrNomAssets.pause.size
                ) {
                    // resume button
                    inBounds(
                        event,
                        Vector(medium, medium1),
                        Size(medium2, small2)
                    ) {
                        playClick()
                        state = State.Running
                        return@inBounds
                    }

                    // quit button
                    inBounds(
                        event,
                        Vector(medium5, medium2),
                        Size(medium2, small2)
                    ) {
                        playClick()
                        game.screen = MainMenuScreen(game)
                        return@inBounds
                    }
                }
            }
        }
    }

    private fun updateGameOver(touchEvents: List<Input.TouchEvent>) {
        for (i in touchEvents.indices) {
            val event = touchEvents[i]

            if (event.type == Input.TouchEventType.Up) {
                // quit button
                inBounds(
                    event,
                    Vector(medium4, large),
                    squareSize
                ) {
                    playClick()
                    game.screen = MainMenuScreen(game)
                }
            }
        }
    }

    private fun drawWorld(world: World) {
        val scalar = Units.small3
        val stainPixmap: Pixmap = when (world.stain.type) {
            StainType.Type1 -> MrNomAssets.stain1
            StainType.Type2 -> MrNomAssets.stain2
            StainType.Type3 -> MrNomAssets.stain3
        }
        game.graphics.drawPixmap(
            stainPixmap,
            Vector.times(world.stain.position, scalar)
        )

        for (i in 1 until world.snake.parts.size) {
            game.graphics.drawPixmap(
                MrNomAssets.tail,
                Vector.times(world.snake.parts[i].position, scalar)
            )
        }

        val headPixmap: Pixmap = when (world.snake.direction) {
            SnakeDir.Up.value -> MrNomAssets.headUp
            SnakeDir.Left.value -> MrNomAssets.headLeft
            SnakeDir.Down.value -> MrNomAssets.headDown
            SnakeDir.Right.value -> MrNomAssets.headRight
            else -> throw IllegalArgumentException("Somehow we have a bad snake direction...")
        }

        val headScaled = Vector.times(world.snake.head, scalar)
        game.graphics.drawPixmap(
            headPixmap,
            Vector(
                headScaled.x + Units.small6 - headPixmap.size.width / 2,
                headScaled.y + Units.small6 - headPixmap.size.height / 2
            )
        )
    }

    private fun drawReadyUi() {
        game.graphics.drawPixmap(
            MrNomAssets.ready,
            Vector(small7, medium1)
        )
        game.graphics.drawLine(
            Vector(0, large4),
            Vector(large7, large4),
            Color.BLACK
        )
    }

    private fun drawRunningUi() {
        game.graphics.drawPixmap(
            MrNomAssets.buttons,
            Vector.zero,
            Vector(small, medium4),
            squareSize
        )
        game.graphics.drawLine(
            Vector(0, large4),
            Vector(large7, large4),
            Color.BLACK
        )
        game.graphics.drawBack()
        game.graphics.drawForward()
    }

    private fun drawPausedUi() {
        game.graphics.drawPixmap(
            MrNomAssets.pause,
            Vector(medium, medium1)
        )
        game.graphics.drawLine(
            Vector(0, large4),
            Vector(large7, large4),
            Color.BLACK
        )
    }

    private fun drawGameOverUi() {
        game.graphics.drawPixmap(
            MrNomAssets.gameOver,
            Vector(small, medium1)
        )
        game.graphics.drawPixmap(
            MrNomAssets.buttons,
            gameOverMenuLoc,
            gameOverMenuSize,
            squareSize
        )
        game.graphics.drawLine(
            Vector(0, large4),
            Vector(large7, large4),
            Color.BLACK
        )
    }
}