package com.weesnerDevelopment.playingWithGames.natureOfCode.randomWalker

import com.weesnerDevelopment.playingWithGames.GameFragment
import com.weesnerDevelopment.playingWithGames.game.GameVariables

class WalkerFragment : GameFragment() {
    override fun create() {
        gameView = WalkerSurfaceView(requireContext()).apply {
            hardwareAccelerated = true
            loopType = GameVariables.looper.value
        }
    }
}
