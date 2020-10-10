package com.weesnerDevelopment.mrNom

import com.weesnerDevelopment.gameEngine.audio.Sound
import com.weesnerDevelopment.gameEngine.game.Game
import com.weesnerDevelopment.gameEngine.graphics.Graphics
import com.weesnerDevelopment.gameEngine.graphics.Pixmap
import com.weesnerDevelopment.gameEngine.math.Size
import com.weesnerDevelopment.gameEngine.math.Vector2D

object MrNomAssets {
    fun String.getPixmap(
        game: Game,
        format: Graphics.PixmapFormat = Graphics.PixmapFormat.ARGB8888,
        fileFormat: String = "png"
    ): Pixmap =
        game.graphics.newPixmap("$this.$fileFormat", format)

    fun Pixmap.slice(
        game: Game,
        position: Vector2D,
        size: Size,
        format: Graphics.PixmapFormat = Graphics.PixmapFormat.ARGB8888
    ) =
        game.graphics.slicePixmap(this, position, size, format)

    fun String.getSound(game: Game, fileFormat: String = "ogg") =
        game.audio.newSound("$this.$fileFormat")

    lateinit var background: Pixmap
    lateinit var logo: Pixmap
    lateinit var mainMenu: Pixmap
    lateinit var buttons: Pixmap
    lateinit var help1: Pixmap
    lateinit var help2: Pixmap
    lateinit var help3: Pixmap
    lateinit var numbers: Pixmap
    lateinit var readyText: Pixmap
    lateinit var pauseMenu: Pixmap
    lateinit var gameOverText: Pixmap
    lateinit var headUp: Pixmap
    lateinit var headLeft: Pixmap
    lateinit var headDown: Pixmap
    lateinit var headRight: Pixmap
    lateinit var tail: Pixmap
    lateinit var stain1: Pixmap
    lateinit var stain2: Pixmap
    lateinit var stain3: Pixmap

    lateinit var playText: Pixmap
    lateinit var highScoreText: Pixmap
    lateinit var helpText: Pixmap

    lateinit var pause: Pixmap
    lateinit var forward: Pixmap
    lateinit var back: Pixmap
    lateinit var soundOn: Pixmap
    lateinit var soundOff: Pixmap
    lateinit var close: Pixmap

    lateinit var resume: Pixmap
    lateinit var quit: Pixmap

    lateinit var click: Sound
    lateinit var eat: Sound
    lateinit var bitten: Sound
}