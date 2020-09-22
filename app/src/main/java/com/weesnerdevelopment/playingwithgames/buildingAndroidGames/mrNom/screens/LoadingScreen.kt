package com.weesnerdevelopment.playingwithgames.buildingAndroidGames.mrNom.screens

import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.game.Game
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.graphics.Graphics
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.mrNom.MrNomAssets
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.mrNom.Settings

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
            ready = "ready".getPixmap(game)
            pause = "pausemenu".getPixmap(game)
            mainMenu = "mainmenu".getPixmap(game)
            headUp = "headup".getPixmap(game)
            headLeft = "headleft".getPixmap(game)
            headDown = "headdown".getPixmap(game)
            headRight = "headright".getPixmap(game)
            tail = "tail".getPixmap(game)
            stain1 = "stain1".getPixmap(game)
            stain2 = "stain2".getPixmap(game)
            stain3 = "stain3".getPixmap(game)
            gameOver = "gameover".getPixmap(game)

            click = "click".getSound(game)
            eat = "eat".getSound(game)
            bitten = "bitten".getSound(game)
        }
        Settings.load(game.fileIO)
        game.screen = MainMenuScreen.instance(game)
    }

    override fun present(deltaTime: Float) {}
    override fun pause() {}
    override fun resume() {}
    override fun dispose() {}
}