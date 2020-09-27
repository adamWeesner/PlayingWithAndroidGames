package com.weesnerDevelopment.playingWithGames.natureOfCode.bouncingBall

import com.weesnerDevelopment.playingWithGames.GameFragment
import com.weesnerDevelopment.playingWithGames.game.GameVariables

class BouncingBallFragment : GameFragment() {
    override fun create() {
        gameView = BouncingBallSurfaceView(requireContext()).apply {
            hardwareAccelerated = true
            loopType = GameVariables.looper.value
        }
    }
}