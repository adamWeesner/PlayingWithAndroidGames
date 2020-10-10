package com.weesnerDevelopment.natureOfCode.randomWalker

import com.weesnerDevelopment.randomGameEngine.GameFragment
import com.weesnerDevelopment.randomGameEngine.game.GameVariables

class WalkerFragment : GameFragment() {
    override fun create() {
        gameView = WalkerSurfaceView(requireContext()).apply {
            hardwareAccelerated = true
            loopType = GameVariables.looper.value!!
        }
    }
}
