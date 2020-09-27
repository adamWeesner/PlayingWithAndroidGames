package com.weesnerDevelopment.mrNom.screens

import com.weesnerDevelopment.gameEngine.game.Game
import com.weesnerDevelopment.gameEngine.game.Image
import com.weesnerDevelopment.gameEngine.game.Screen
import com.weesnerDevelopment.gameEngine.graphics.Graphics
import com.weesnerDevelopment.gameEngine.input.Input
import com.weesnerDevelopment.gameEngine.util.Size
import com.weesnerDevelopment.gameEngine.math.Vector
import com.weesnerDevelopment.gameEngine.math.plus
import com.weesnerDevelopment.mrNom.MrNomAssets
import com.weesnerDevelopment.mrNom.Settings

fun createMrNomScreen(
    game: Game,
    present: MrNomScreen.(deltaTime: Float) -> Unit = {},
    update: MrNomScreen.(deltaTime: Float) -> Unit = {},
    touchUp: MrNomScreen.(event: Input.TouchEvent) -> Unit = {},
    touchDown: MrNomScreen.(event: Input.TouchEvent) -> Unit = {},
    touchDrag: MrNomScreen.(event: Input.TouchEvent) -> Unit = {},
    resume: MrNomScreen.() -> Unit = {},
    pause: MrNomScreen.() -> Unit = {},
    dispose: MrNomScreen.() -> Unit = {},
): MrNomScreen = object : MrNomScreen(game) {
    override fun present(deltaTime: Float) {
        backImage = Image(MrNomAssets.back, backButtonLoc)
        forwardImage = Image(MrNomAssets.forward, forwardButtonLoc)
        present.invoke(this, deltaTime)
    }

    override fun update(deltaTime: Float) {
        val touchEvents = game.input.getTouchEvents()
        game.input.getKeyEvents()

        for (i in 0 until touchEvents.size) {
            if (touchEvents.isEmpty()) return

            val event = touchEvents[i]
            when (event.type) {
                Input.TouchEventType.Down ->
                    touchDown.invoke(this, event)
                Input.TouchEventType.Up ->
                    touchUp.invoke(this, event)
                Input.TouchEventType.Dragged ->
                    touchDrag.invoke(this, event)
            }
        }
        update.invoke(this, deltaTime)
    }

    override fun pause() {
        pause.invoke(this)
    }

    override fun resume() {
        resume.invoke(this)
    }

    override fun dispose() {
        dispose.invoke(this)
    }
}

internal object Units {
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

    /**
     * 160 pixels (not scaled).
     */
    const val large8 = 160
}

/**
 * [Vector] of [Units.small], [Units.large2]
 */
internal val generalPos = Vector(Units.small, Units.large2)

/**
 * [Vector] of [Units.small], [Units.small]
 */
internal val squarePos = Vector(Units.small, Units.small)

/**
 * [Size] of [Units.large], [Units.small2]
 */
internal val generalSize = Size(Units.large, Units.small2)

/**
 * [Size] of [Units.small], [Units.small]
 */
internal val squareSize = Size(Units.small, Units.small)

internal val backButtonLoc = Vector(0, Units.large4)
internal val forwardButtonLoc = Vector(Units.large3, Units.large4)

abstract class MrNomScreen(
    open val game: Game
) : Screen(game) {
    lateinit var backImage: Image
    lateinit var forwardImage: Image

    fun playClick() {
        if (Settings.soundEnabled)
            MrNomAssets.click.play(100)
    }

    fun drawText(graphics: Graphics, line: String, position: Vector) {
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