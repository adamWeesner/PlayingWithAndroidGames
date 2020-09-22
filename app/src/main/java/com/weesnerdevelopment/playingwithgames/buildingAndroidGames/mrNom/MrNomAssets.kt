package com.weesnerdevelopment.playingwithgames.buildingAndroidGames.mrNom

import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.audio.Sound
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.game.Game
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.graphics.Graphics
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.graphics.Pixmap

object MrNomAssets {
    fun String.getPixmap(game: Game, format: Graphics.PixmapFormat = Graphics.PixmapFormat.ARGB8888, fileFormat: String = "png"): Pixmap =
        game.graphics.newPixmap("mrNom/$this.$fileFormat", format)

    fun String.getSound(game: Game, fileFormat: String = "ogg") =
        game.audio.newSound("mrNom/$this.$fileFormat")

    lateinit var background: Pixmap
    lateinit var logo: Pixmap
    lateinit var mainMenu: Pixmap
    lateinit var buttons: Pixmap
    lateinit var help1: Pixmap
    lateinit var help2: Pixmap
    lateinit var help3: Pixmap
    lateinit var numbers: Pixmap
    lateinit var ready: Pixmap
    lateinit var pause: Pixmap
    lateinit var gameOver: Pixmap
    lateinit var headUp: Pixmap
    lateinit var headLeft: Pixmap
    lateinit var headDown: Pixmap
    lateinit var headRight: Pixmap
    lateinit var tail: Pixmap
    lateinit var stain1: Pixmap
    lateinit var stain2: Pixmap
    lateinit var stain3: Pixmap

    lateinit var click: Sound
    lateinit var eat: Sound
    lateinit var bitten: Sound
}