package com.weesnerdevelopment.playingwithgames.natureOfCode.bouncingBall

import com.weesnerdevelopment.playingwithgames.GameFragment
import com.weesnerdevelopment.playingwithgames.game.GameVariables

class BouncingBallFragment : GameFragment() {
    override fun create() {
        gameView = BouncingBallSurfaceView(requireContext()).apply {
            hardwareAccelerated = true
            loopType = GameVariables.looper.value
        }
    }
}