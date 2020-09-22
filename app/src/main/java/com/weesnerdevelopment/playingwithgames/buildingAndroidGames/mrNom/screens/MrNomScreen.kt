package com.weesnerdevelopment.playingwithgames.buildingAndroidGames.mrNom.screens

import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.game.Game
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.game.Screen
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.graphics.Graphics
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.mrNom.MrNomAssets
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.mrNom.Settings
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.util.Size
import com.weesnerdevelopment.playingwithgames.math.plus
import com.weesnerdevelopment.playingwithgames.objects.Vector

abstract class MrNomScreen(
    protected open val game: Game
) : Screen(game) {
    object Units {
        /**
         * 47 pixels (not scaled)
         */
        const val small7 = 47

        /**
         * 16 pixels (not scaled)
         */
        const val small6 = 16

        /**
         * 10 pixels (not scaled)
         */
        const val small5 = 10

        /**
         * 20 pixels (not scaled)
         */
        const val small4 = 20

        /**
         * 32 pixels (not scaled)
         */
        const val small3 = 32

        /**
         * 42 pixels (not scaled)
         */
        const val small2 = 42

        /**
         * 50 pixels (not scaled)
         */
        const val small1 = 50

        /**
         * 64 pixels (not scaled)
         */
        const val small = 64

        /**
         * 80 pixels (not scaled)
         */
        const val medium = 80

        /**
         * 100 pixels (not scaled)
         */
        const val medium1 = 100

        /**
         * 148 pixels (not scaled)
         */
        const val medium2 = 148

        /**
         * 196 pixels (not scaled)
         */
        const val medium3 = 196

        /**
         * 128 pixels (not scaled)
         */
        const val medium4 = 128

        /**
         * 90 pixels (not scaled).
         */
        const val medium5 = 90

        /**
         * 192 pixels (not scaled)
         */
        const val large = 192

        /**
         * 200 pixels (not scaled)
         */
        const val large1 = 200

        /**
         * 220 pixels (not scaled)
         */
        const val large2 = 220

        /**
         * 256 pixels (not scaled)
         */
        const val large3 = 256

        /**
         * 416 pixels (not scaled)
         */
        const val large4 = 416

        /**
         * 240 pixels (not scaled)
         */
        const val large5 = 240

        /**
         * 264 pixels (not scaled)
         */
        const val large6 = 264

        /**
         * 480 pixels (not scaled)
         */
        const val large7 = 480
    }

    /**
     * [Vector] of [Units.small], [Units.large2]
     */
    val generalPos = Vector(Units.small, Units.large2)

    /**
     * [Vector] of [Units.small], [Units.small]
     */
    val squarePos = Vector(Units.small, Units.small)

    /**
     * [Size] of [Units.large], [Units.small2]
     */
    val generalSize = Size(Units.large, Units.small2)

    /**
     * [Size] of [Units.small], [Units.small]
     */
    val squareSize = Size(Units.small, Units.small)

    val backButtonLoc = Vector(0, Units.large4)
    val forwardButtonLoc = Vector(Units.large3, Units.large4)

    fun Graphics.drawBack() {
        drawPixmap(
            MrNomAssets.buttons,
            backButtonLoc,
            squarePos,
            squareSize
        )
    }

    fun Graphics.drawForward() {
        drawPixmap(
            MrNomAssets.buttons,
            forwardButtonLoc,
            Vector(0, Units.small),
            squareSize
        )
    }

    protected fun playClick() {
        if (Settings.soundEnabled)
            MrNomAssets.click.play(100)
    }

    protected fun drawText(graphics: Graphics, line: String, position: Vector) {
        for (i in line.indices) {
            val char = line[i]

            if (char == ' ') {
                position.x += Units.small4
                continue
            }

            val srcX: Int
            val srcWidth: Int
            if (char == '.') {
                srcX = Units.large1
                srcWidth = Units.small5
            } else {
                srcX = (char - '0') * Units.small4
                srcWidth = Units.small4
            }

            graphics.drawPixmap(
                MrNomAssets.numbers,
                position,
                Vector(srcX, 0),
                Size(srcWidth, Units.small3)
            )
            position.x += srcWidth
        }
    }
}