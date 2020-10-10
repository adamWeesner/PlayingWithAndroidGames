package com.weesnerDevelopment.mrNom.screens

import com.weesnerDevelopment.gameEngine.game.Game
import com.weesnerDevelopment.gameEngine.game.Screen
import com.weesnerDevelopment.gameEngine.graphics.Pixmap
import com.weesnerDevelopment.gameEngine.math.Vector2D
import com.weesnerDevelopment.mrNom.MrNomAssets

private fun Game.screenHelpBase(helpImage: Pixmap, nextScreen: Screen) = createMrNomScreen(
    game = this,
    present = {
        game.graphics.apply {
            drawPixmap(MrNomAssets.background, Vector2D(0,0))
            drawPixmap(helpImage, Vector2D(Units.small, Units.medium1))
            backImage.draw(this)
            forwardImage.draw(this)
        }
    },
    touchUp = { event ->
        backImage.onClick(event) {
            playClick()
            game.back()
        }
        forwardImage.onClick(event) {
            playClick()
            game.screen = nextScreen
        }
    }
)

val Game.ScreenHelp1 get() = screenHelpBase(MrNomAssets.help1, ScreenHelp2)
val Game.ScreenHelp2 get() = screenHelpBase(MrNomAssets.help2, ScreenHelp3)
val Game.ScreenHelp3 get() = screenHelpBase(MrNomAssets.help3, ScreenMainMenu)