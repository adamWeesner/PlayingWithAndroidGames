package com.weesnerDevelopment.playingWithGames.natureOfCode.perlinBackground

import com.weesnerDevelopment.playingWithGames.GameFragment
import com.weesnerDevelopment.playingWithGames.game.GameVariables

class PerlinBackgroundFragment : GameFragment() {
    override fun create() {
        gameView = PerlinBackgroundSurfaceView(requireContext()).apply {
            hardwareAccelerated = true
            loopType = GameVariables.looper.value
        }
    }
}
