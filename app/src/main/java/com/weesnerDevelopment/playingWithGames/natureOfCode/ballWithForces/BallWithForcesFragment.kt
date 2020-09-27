package com.weesnerDevelopment.playingWithGames.natureOfCode.ballWithForces

import com.weesnerDevelopment.playingWithGames.GameFragment
import com.weesnerDevelopment.playingWithGames.game.GameVariables

class BallWithForcesFragment : GameFragment() {
    override fun create() {
        gameView = BallWithForcesSurfaceView(requireContext()).apply {
            hardwareAccelerated = true
            loopType = GameVariables.looper.value
        }
    }
}
