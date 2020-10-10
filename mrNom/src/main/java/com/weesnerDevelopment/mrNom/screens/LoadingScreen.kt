package com.weesnerDevelopment.mrNom.screens

import com.weesnerDevelopment.gameEngine.game.Game
import com.weesnerDevelopment.gameEngine.graphics.Graphics
import com.weesnerDevelopment.gameEngine.math.Size
import com.weesnerDevelopment.gameEngine.math.Vector2D
import com.weesnerDevelopment.mrNom.MrNomAssets
import com.weesnerDevelopment.mrNom.Settings

class LoadingScreen(
    override val game: Game
) : MrNomScreen(game) {
    override fun update(deltaTime: Float) {
        MrNomAssets.apply {
            logo = "logo".getPixmap(game)
            background = "background".getPixmap(game, Graphics.PixmapFormat.RGB565)
            buttons = "buttons".getPixmap(game)
            help1 = "help1".getPixmap(game)
            help2 = "help2".getPixmap(game)
            help3 = "help3".getPixmap(game)
            numbers = "numbers".getPixmap(game)
            readyText = "ready".getPixmap(game)
            pauseMenu = "pausemenu".getPixmap(game)
            mainMenu = "mainmenu".getPixmap(game)
            headUp = "headup".getPixmap(game)
            headLeft = "headleft".getPixmap(game)
            headDown = "headdown".getPixmap(game)
            headRight = "headright".getPixmap(game)
            tail = "tail".getPixmap(game)
            stain1 = "stain1".getPixmap(game)
            stain2 = "stain2".getPixmap(game)
            stain3 = "stain3".getPixmap(game)
            gameOverText = "gameover".getPixmap(game)

            playText = mainMenu.slice(game, Vector2D(0, 0), generalSize)
            highScoreText = mainMenu.slice(game, Vector2D(0, Units.small2), generalSize)
            helpText = mainMenu.slice(game, Vector2D(0, Units.small2 + Units.small2), generalSize)

            pause = buttons.slice(game, Vector2D(Units.small, Units.medium4), squareSize)
            back = buttons.slice(game, Vector2D(Units.small, Units.small), squareSize)
            forward = buttons.slice(game, Vector2D(0, Units.small), squareSize)
            soundOn = buttons.slice(game, Vector2D(0, 0), squareSize)
            soundOff = buttons.slice(game, Vector2D(0, Units.small), squareSize)
            close = buttons.slice(game, Vector2D(0, Units.medium4), squareSize)

            resume = pauseMenu.slice(game, Vector2D(0, 0), Size(Units.large8, Units.small2))
            quit =
                pauseMenu.slice(game, Vector2D(0, Units.small7), Size(Units.large8, Units.small2))

            click = "click".getSound(game)
            eat = "eat".getSound(game)
            bitten = "bitten".getSound(game)
        }
        Settings.load(game.fileIO)
        game.screen = game.ScreenMainMenu
    }

    override fun present(deltaTime: Float) {}
    override fun pause() {}
    override fun resume() {}
    override fun dispose() {}
}