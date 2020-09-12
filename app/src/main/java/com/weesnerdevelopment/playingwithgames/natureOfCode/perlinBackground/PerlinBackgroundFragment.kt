package com.weesnerdevelopment.playingwithgames.natureOfCode.perlinBackground

import com.weesnerdevelopment.playingwithgames.GameFragment
import com.weesnerdevelopment.playingwithgames.game.GameVariables

class PerlinBackgroundFragment : GameFragment() {
    override fun create() {
        gameView = PerlinBackgroundSurfaceView(requireContext()).apply {
            hardwareAccelerated = true
            loopType = GameVariables.looper.value
        }
    }
}
