package com.weesnerDevelopment.natureOfCode.bouncingBall

import com.weesnerDevelopment.randomGameEngine.GameFragment
import com.weesnerDevelopment.randomGameEngine.game.GameVariables

class BouncingBallFragment : GameFragment() {
    override fun create() {
        gameView = BouncingBallSurfaceView(requireContext()).apply {
            hardwareAccelerated = true
            loopType = GameVariables.looper.value!!
        }
    }
}