package com.weesnerDevelopment.mrNom.screens

import com.weesnerDevelopment.gameEngine.game.Game
import com.weesnerDevelopment.gameEngine.math.Vector2D
import com.weesnerDevelopment.mrNom.MrNomAssets
import com.weesnerDevelopment.mrNom.Settings

val Game.ScreenHighScores: MrNomScreen
    get() {
        val lines = Array(Settings.highScoresCount) {
            "${it + 1}. ${Settings.highScores[it]}"
        }

        return createMrNomScreen(
            game = this,
            present = {
                game.graphics.apply {
                    drawPixmap(MrNomAssets.background, Vector2D(0,0))
                    drawPixmap(MrNomAssets.mainMenu, Vector2D(Units.small, Units.small4), Vector2D(0, Units.small2), generalSize)

                    var y = Units.medium1
                    for (i in 0 until Settings.highScoresCount) {
                        drawText(this, lines[i], Vector2D(Units.small4, y))
                        y += Units.small1
                    }
                    backImage.draw(this)
                }
            },
            touchUp = { event ->
                backImage.onClick(event) {
                    playClick()
                    game.screen = game.ScreenMainMenu
                }
            }
        )
    }