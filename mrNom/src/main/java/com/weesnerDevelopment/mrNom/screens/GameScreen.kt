package com.weesnerDevelopment.mrNom.screens

import android.graphics.Color
import com.weesnerDevelopment.gameEngine.game.Game
import com.weesnerDevelopment.gameEngine.game.Image
import com.weesnerDevelopment.gameEngine.graphics.Pixmap
import com.weesnerDevelopment.gameEngine.math.Vector2D
import com.weesnerDevelopment.gameEngine.math.div
import com.weesnerDevelopment.gameEngine.math.minus
import com.weesnerDevelopment.gameEngine.math.plus
import com.weesnerDevelopment.mrNom.MrNomAssets
import com.weesnerDevelopment.mrNom.Settings
import com.weesnerDevelopment.mrNom.objects.SnakeDir
import com.weesnerDevelopment.mrNom.objects.StainType
import com.weesnerDevelopment.mrNom.objects.World
import com.weesnerDevelopment.mrNom.screens.Units.large1
import com.weesnerDevelopment.mrNom.screens.Units.large4
import com.weesnerDevelopment.mrNom.screens.Units.large7
import com.weesnerDevelopment.mrNom.screens.Units.medium
import com.weesnerDevelopment.mrNom.screens.Units.medium1
import com.weesnerDevelopment.mrNom.screens.Units.medium2
import com.weesnerDevelopment.mrNom.screens.Units.medium4
import com.weesnerDevelopment.mrNom.screens.Units.medium5
import com.weesnerDevelopment.mrNom.screens.Units.small
import com.weesnerDevelopment.mrNom.screens.Units.small1
import com.weesnerDevelopment.mrNom.screens.Units.small2
import com.weesnerDevelopment.mrNom.screens.Units.small7

enum class State {
    Ready,
    Running,
    Paused,
    GameOver
}

val Game.ScreenGame: MrNomScreen
    get() {
        val gameOverMenuLoc = Vector2D(medium4, large1)

        var state = State.Ready
        val world = World()
        var oldScore: Number = 0
        var score = "0"

        val pauseButton = Image(MrNomAssets.pause, Vector2D(0, 0))
        val closeButton = Image(MrNomAssets.close, gameOverMenuLoc)
        val resumeButton = Image(MrNomAssets.resume, Vector2D(medium, medium1))
        val quitButton = Image(MrNomAssets.quit, Vector2D(medium5, medium2))

        fun drawWorld(world: World) {
            val scalar = Units.small3
            val stainPixmap: Pixmap = when (world.stain.type) {
                StainType.Type1 -> MrNomAssets.stain1
                StainType.Type2 -> MrNomAssets.stain2
                StainType.Type3 -> MrNomAssets.stain3
            }
            graphics.drawPixmap(stainPixmap, world.stain.position * scalar)

            for (i in 1 until world.snake.parts.size) {
                graphics.drawPixmap(
                    MrNomAssets.tail,
                    world.snake.parts[i].position * scalar
                )
            }

            val headPixmap: Pixmap = when (world.snake.direction) {
                SnakeDir.Up.value -> MrNomAssets.headUp
                SnakeDir.Left.value -> MrNomAssets.headLeft
                SnakeDir.Down.value -> MrNomAssets.headDown
                SnakeDir.Right.value -> MrNomAssets.headRight
                else -> throw IllegalArgumentException("Somehow we have a bad snake direction...")
            }

            val headScaled = world.snake.head * scalar
            graphics.drawPixmap(
                headPixmap,
                Vector2D(
                    headScaled.x + Units.small6 - headPixmap.size.width / 2,
                    headScaled.y + Units.small6 - headPixmap.size.height / 2
                )
            )
        }

        return createMrNomScreen(
            game = this,
            present = {
                graphics.apply {
                    drawPixmap(MrNomAssets.background, Vector2D(0, 0))
                    drawWorld(world)

                    when (state) {
                        State.Ready -> {
                            drawPixmap(MrNomAssets.readyText, Vector2D(small7, medium1))
                            drawLine(Vector2D(0, large4), Vector2D(large7, large4), Color.BLACK)
                        }
                        State.Running -> {
                            pauseButton.draw(this)
                            drawLine(Vector2D(0, large4), Vector2D(large7, large4), Color.BLACK)
                            backImage.draw(this)
                            forwardImage.draw(this)
                        }
                        State.Paused -> {
                            resumeButton.draw(this)
                            quitButton.draw(this)
                            drawLine(Vector2D(0, large4), Vector2D(large7, large4), Color.BLACK)
                        }
                        State.GameOver -> {
                            drawPixmap(MrNomAssets.gameOverText, Vector2D(small, medium1))
                            closeButton.draw(this)
                            drawLine(Vector2D(0, large4), Vector2D(large7, large4), Color.BLACK)
                        }
                    }
                    drawText(
                        this,
                        score,
                        Vector2D(
                            game.graphics.size.width / 2 - score.length * small1 / 2,
                            game.graphics.size.height - small2
                        )
                    )
                }
            },
            touchUp = { event ->
                when (state) {
                    State.Ready -> TODO()
                    State.Running -> {
                        pauseButton.onClick(event) {
                            playClick()
                            state = State.Paused
                        }
                    }
                    State.Paused -> {
                        resumeButton.onClick(event) {
                            playClick()
                            state = State.Running
                        }

                        quitButton.onClick(event) {
                            playClick()
                            game.screen = game.ScreenMainMenu
                        }
                    }
                    State.GameOver -> {
                        closeButton.onClick(event) {
                            playClick()
                            game.screen = game.ScreenMainMenu
                        }
                    }
                }
            },
            touchDown = { event ->
                when (state) {
                    State.Ready, State.Paused, State.GameOver -> Unit
                    State.Running -> {
                        backImage.onClick(event) {
                            playClick()
                            world.snake.turnLeft()
                        }
                        forwardImage.onClick(event) {
                            playClick()
                            world.snake.turnRight()
                        }
                    }
                }
            },
            update = { deltaTime ->
                when (state) {
                    State.Ready -> state = State.Running
                    State.Running -> {
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
                    State.Paused, State.GameOver -> Unit
                }
            },
            pause = {
                if (state == State.Running)
                    state = State.Paused

                if (world.gameOver) {
                    Settings.addScore(world.score)
                    Settings.save(game.fileIO)
                }
            }
        )
    }