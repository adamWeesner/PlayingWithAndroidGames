package com.weesnerdevelopment.playingwithgames.buildingAndroidGames.mrNom.screens

import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.game.Game
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.input.Input
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.mrNom.MrNomAssets
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.mrNom.Settings
import com.weesnerdevelopment.playingwithgames.math.compareTo
import com.weesnerdevelopment.playingwithgames.objects.Vector

class HighScoreScreen(
    override val game: Game
) : MrNomScreen(game) {
    companion object {
        private var instance: HighScoreScreen? = null
        fun instance(game: Game): HighScoreScreen {
            synchronized(this) {
                if (instance == null) {
                    synchronized(this) {
                        instance = HighScoreScreen(game)
                    }
                }
                return instance!!
            }
        }
    }

    private val lines = Array(Settings.highScoresCount) {
        "${it + 1}. ${Settings.highScores[it]}"
    }

    override fun update(deltaTime: Float) {
        val touchEvents = game.input.getTouchEvents()
        game.input.getKeyEvents()

        for (i in touchEvents.indices) {
            val event = touchEvents[i]

            if (event.type == Input.TouchEventType.Up) {
                if (event.position.x < Units.small && event.position.y > Units.large4) {
                    game.screen = MainMenuScreen(game)
                    playClick()
                    return
                }
            }
        }
    }

    override fun present(deltaTime: Float) {
        game.graphics.apply {
            drawPixmap(MrNomAssets.background, Vector.zero)
            drawPixmap(
                MrNomAssets.mainMenu,
                Vector(Units.small, Units.small4),
                Vector(0, Units.small2),
                generalSize
            )

            var y = Units.medium1
            for (i in 0 until Settings.highScoresCount) {
                drawText(this, lines[i], Vector(Units.small4, y))
                y += Units.small1
            }
            drawBack()
        }
    }

    override fun pause() {}
    override fun resume() {}
    override fun dispose() {}
}