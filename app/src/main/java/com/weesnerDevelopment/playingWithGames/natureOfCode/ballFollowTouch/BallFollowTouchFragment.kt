package com.weesnerDevelopment.playingWithGames.natureOfCode.ballFollowTouch

import com.weesnerDevelopment.playingWithGames.GameFragment
import com.weesnerDevelopment.playingWithGames.game.GameVariables

class BallFollowTouchFragment : GameFragment() {
    override fun create() {
        gameView = BallFollowTouchSurfaceView(requireContext()).apply {
            hardwareAccelerated = true
            loopType = GameVariables.looper.value
        }
    }
}
