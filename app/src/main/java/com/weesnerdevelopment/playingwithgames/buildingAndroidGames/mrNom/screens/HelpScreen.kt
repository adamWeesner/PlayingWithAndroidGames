package com.weesnerdevelopment.playingwithgames.buildingAndroidGames.mrNom.screens

import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.game.Game
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.game.Screen
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.graphics.Pixmap
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.input.Input
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.mrNom.MrNomAssets
import com.weesnerdevelopment.playingwithgames.math.compareTo
import com.weesnerdevelopment.playingwithgames.math.minus
import com.weesnerdevelopment.playingwithgames.objects.Vector

abstract class HelpScreenBase(
    override val game: Game
) : MrNomScreen(game) {
    override fun update(deltaTime: Float) {
        val graphics = game.graphics
        val touchEvents = game.input.getTouchEvents()
        game.input.getKeyEvents()

        for (i in touchEvents.indices) {
            val event = touchEvents[i]

            if (event.type == Input.TouchEventType.Up) {
                // back button
                inBounds(
                    event,
                    Vector(0, graphics.size.height - squareSize.height),
                    squareSize
                ) {
                    game.back()
                    playClick()
                    return@inBounds
                }
                // forward button
                inBounds(
                    event,
                    Vector(
                        graphics.size.width - squareSize.width,
                        graphics.size.height - squareSize.height
                    ),
                    squareSize
                ) {
                    game.screen = nextScreen
                    playClick()
                    return@inBounds
                }
                if (event.position.x < Units.small && event.position.y > Units.large4) {

                }
                if (event.position.x > Units.large3 && event.position.y > Units.large4) {

                }
            }
        }
    }

    override fun present(deltaTime: Float) {
        game.graphics.apply {
            drawPixmap(MrNomAssets.background, Vector.zero)
            drawPixmap(helpImage, Vector(Units.small, Units.medium1))
            drawBack()
            drawForward()
        }
    }

    override fun pause() {}
    override fun resume() {}
    override fun dispose() {}

    abstract val helpImage: Pixmap
    abstract val nextScreen: Screen
}

class HelpScreen(
    override val game: Game,
    override val helpImage: Pixmap = MrNomAssets.help1,
    override val nextScreen: Screen = HelpScreen2.instance(game)
) : HelpScreenBase(game) {
    companion object {
        @Volatile
        private var instance: HelpScreenBase? = null

        fun instance(game: Game): HelpScreenBase {
            synchronized(this) {
                if (instance == null) {
                    synchronized(this) {
                        instance = HelpScreen(game)
                    }
                }

                return instance!!
            }
        }
    }
}

class HelpScreen2(
    override val game: Game,
    override val helpImage: Pixmap = MrNomAssets.help2,
    override val nextScreen: Screen = HelpScreen3.instance(game)
) : HelpScreenBase(game) {
    companion object {
        @Volatile
        private var instance: HelpScreenBase? = null

        fun instance(game: Game): HelpScreenBase {
            synchronized(this) {
                if (instance == null) {
                    synchronized(this) {
                        instance = HelpScreen2(game)
                    }
                }

                return instance!!
            }
        }
    }
}

class HelpScreen3(
    override val game: Game,
    override val helpImage: Pixmap = MrNomAssets.help3,
    override val nextScreen: Screen = MainMenuScreen.instance(game)
) : HelpScreenBase(game) {
    companion object {
        @Volatile
        private var instance: HelpScreenBase? = null

        fun instance(game: Game): HelpScreenBase {
            synchronized(this) {
                if (instance == null) {
                    synchronized(this) {
                        instance = HelpScreen3(game)
                    }
                }

                return instance!!
            }
        }
    }
}