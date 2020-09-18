package com.weesnerdevelopment.playingwithgames.natureOfCode.ballWithForces

import com.weesnerdevelopment.playingwithgames.GameFragment
import com.weesnerdevelopment.playingwithgames.game.GameVariables

class BallWithForcesFragment : GameFragment() {
    override fun create() {
        gameView = BallWithForcesSurfaceView(requireContext()).apply {
            hardwareAccelerated = true
            loopType = GameVariables.looper.value
        }
    }
}
