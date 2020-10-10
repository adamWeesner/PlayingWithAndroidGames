package com.weesnerDevelopment.mrNom.screens

import com.weesnerDevelopment.gameEngine.game.Game
import com.weesnerDevelopment.gameEngine.game.Image
import com.weesnerDevelopment.gameEngine.math.Vector2D
import com.weesnerDevelopment.gameEngine.math.plus
import com.weesnerDevelopment.mrNom.MrNomAssets
import com.weesnerDevelopment.mrNom.Settings

val Game.ScreenMainMenu: MrNomScreen
    get() {
        val soundButton = Image(
            MrNomAssets.buttons,
            Vector2D(0, Units.large4),
            squareSize,
            if (Settings.soundEnabled) Vector2D(0, 0) else Vector2D(Units.small, 0)
        )
        val playButton = Image(MrNomAssets.playText, generalPos)
        val highScoreButton =
            Image(MrNomAssets.highScoreText, generalPos.copy(y = generalPos.y + Units.small2))
        val helpButton = Image(
            MrNomAssets.helpText,
            generalPos.copy(y = generalPos.y + Units.small2 + Units.small2),
        )

        return createMrNomScreen(
            game = this,
            present = {
                game.graphics.apply {
                    drawPixmap(MrNomAssets.background, Vector2D(0, 0))
                    drawPixmap(MrNomAssets.logo, Vector2D(Units.small3, Units.small4))
                    playButton.draw(this)
                    highScoreButton.draw(this)
                    helpButton.draw(this)

                    soundButton.offset =
                        if (Settings.soundEnabled) Vector2D(0, 0) else Vector2D(Units.small, 0)
                    soundButton.draw(this)
                }
            },
            touchUp = { event ->
                soundButton.onClick(event) {
                    Settings.soundEnabled = !Settings.soundEnabled

                    playClick()
                }

                playButton.onClick(event) {
                    playClick()
                    game.screen = game.ScreenGame
                }

                highScoreButton.onClick(event) {
                    playClick()
                    game.screen = game.ScreenHighScores
                }

                helpButton.onClick(event) {
                    playClick()
                    game.screen = game.ScreenHelp1
                }
            },
            pause = {
                Settings.save(game.fileIO)
            }
        )
    }