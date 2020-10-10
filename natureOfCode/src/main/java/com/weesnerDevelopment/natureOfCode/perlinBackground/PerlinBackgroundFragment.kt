package com.weesnerDevelopment.natureOfCode.perlinBackground

import com.weesnerDevelopment.randomGameEngine.GameFragment
import com.weesnerDevelopment.randomGameEngine.game.GameVariables

class PerlinBackgroundFragment : GameFragment() {
    override fun create() {
        gameView = PerlinBackgroundSurfaceView(requireContext()).apply {
            hardwareAccelerated = true
            loopType = GameVariables.looper.value!!
        }
    }
}
