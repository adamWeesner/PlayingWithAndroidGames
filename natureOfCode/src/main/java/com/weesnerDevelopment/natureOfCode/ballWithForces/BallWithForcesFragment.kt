package com.weesnerDevelopment.natureOfCode.ballWithForces

import com.weesnerDevelopment.randomGameEngine.GameFragment
import com.weesnerDevelopment.randomGameEngine.game.GameVariables

class BallWithForcesFragment : GameFragment() {
    override fun create() {
        gameView = BallWithForcesSurfaceView(requireContext()).apply {
            hardwareAccelerated = true
            loopType = GameVariables.looper.value!!
        }
    }
}
