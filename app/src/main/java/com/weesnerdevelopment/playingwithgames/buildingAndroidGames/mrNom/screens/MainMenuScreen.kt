package com.weesnerdevelopment.playingwithgames.buildingAndroidGames.mrNom.screens

import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.game.Game
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.input.Input
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.mrNom.MrNomAssets
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.mrNom.Settings
import com.weesnerdevelopment.playingwithgames.math.minus
import com.weesnerdevelopment.playingwithgames.math.plus
import com.weesnerdevelopment.playingwithgames.objects.Vector

class MainMenuScreen(
    override val game: Game
) : MrNomScreen(game) {
    companion object {
        private var instance: MainMenuScreen? = null
        fun instance(game: Game): MainMenuScreen {
            synchronized(this) {
                if (instance == null) {
                    synchronized(this) {
                        instance = MainMenuScreen(game)
                    }
                }
                return instance!!
            }
        }
    }

    override fun update(deltaTime: Float) {
        val graphics = game.graphics
        val touchEvents = game.input.getTouchEvents()
        game.input.getKeyEvents()

        for (i in touchEvents.indices) {
            val event = touchEvents[i]

            if (event.type == Input.TouchEventType.Up) {
                // sound button
                inBounds(
                    event,
                    Vector(0, graphics.size.height - squareSize.height),
                    squareSize
                ) {
                    Settings.soundEnabled = !Settings.soundEnabled
                    playClick()
                }

                // play button
                inBounds(
                    event,
                    generalPos,
                    generalSize
                ) {
                    game.screen = GameScreen(game)
                    playClick()
                    return@inBounds
                }

                // high score button
                inBounds(
                    event,
                    generalPos.copy(y = generalPos.y + Units.small2),
                    generalSize
                ) {
                    game.screen = HighScoreScreen.instance(game)
                    playClick()
                    return@inBounds
                }

                // help button
                inBounds(
                    event,
                    generalPos.copy(y = generalPos.y + Units.small2 + Units.small2),
                    generalSize
                ) {
                    game.screen = HelpScreen.instance(game)
                    playClick()
                    return@inBounds
                }
            }
        }
    }

    override fun present(deltaTime: Float) {
        game.graphics.apply {
            drawPixmap(MrNomAssets.background, Vector.zero)
            drawPixmap(MrNomAssets.logo, Vector(Units.small3, Units.small4))
            drawPixmap(MrNomAssets.mainMenu, generalPos)
            drawPixmap(
                MrNomAssets.buttons,
                Vector(0, Units.large4),
                if (Settings.soundEnabled) Vector.zero else Vector(Units.small, 0),
                squareSize
            )
        }
    }

    override fun pause() {
        Settings.save(game.fileIO)
    }

    override fun resume() {}
    override fun dispose() {}
}