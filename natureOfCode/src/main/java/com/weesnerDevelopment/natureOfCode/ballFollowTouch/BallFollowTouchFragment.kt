package com.weesnerDevelopment.natureOfCode.ballFollowTouch

import com.weesnerDevelopment.randomGameEngine.GameFragment
import com.weesnerDevelopment.randomGameEngine.game.GameVariables

class BallFollowTouchFragment : GameFragment() {
    override fun create() {
        gameView = BallFollowTouchSurfaceView(requireContext()).apply {
            hardwareAccelerated = true
            loopType = GameVariables.looper.value!!
        }
    }
}
