package com.weesnerDevelopment.mrNom.screens

import com.weesnerDevelopment.gameEngine.game.Game
import com.weesnerDevelopment.gameEngine.math.Vector
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
                    drawPixmap(MrNomAssets.background, Vector.zero)
                    drawPixmap(MrNomAssets.mainMenu, Vector(Units.small, Units.small4), Vector(0, Units.small2), generalSize)

                    var y = Units.medium1
                    for (i in 0 until Settings.highScoresCount) {
                        drawText(this, lines[i], Vector(Units.small4, y))
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