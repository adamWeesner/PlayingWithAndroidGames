package com.weesnerdevelopment.playingwithgames.natureOfCode.ballFollowTouch

import com.weesnerdevelopment.playingwithgames.GameFragment
import com.weesnerdevelopment.playingwithgames.game.GameVariables

class BallFollowTouchFragment : GameFragment() {
    override fun create() {
        gameView = BallFollowTouchSurfaceView(requireContext()).apply {
            hardwareAccelerated = true
            loopType = GameVariables.looper.value
        }
    }
}
