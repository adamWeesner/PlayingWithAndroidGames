package com.weesnerdevelopment.playingwithgames.natureOfCode.randomWalker

import com.weesnerdevelopment.playingwithgames.GameFragment
import com.weesnerdevelopment.playingwithgames.game.GameVariables

class WalkerFragment : GameFragment() {
    override fun create() {
        gameView = WalkerSurfaceView(requireContext()).apply {
            hardwareAccelerated = true
            loopType = GameVariables.looper.value
        }
    }
}
