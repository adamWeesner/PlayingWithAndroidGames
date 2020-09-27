package com.weesnerDevelopment.playingWithGames.random.bouncingBall

import com.weesnerDevelopment.gameEngine.math.Vector
import com.weesnerDevelopment.playingWithGames.GameFragment
import com.weesnerDevelopment.playingWithGames.game.GameVariables
import com.weesnerDevelopment.playingWithGames.objects.Ball
import kotlin.random.Random.Default.nextInt

class BallBounceFragment : GameFragment() {
    override fun create() {
        gameView = BallBounceSurfaceView(requireContext()).apply {
            hardwareAccelerated = true
            loopType = GameVariables.looper.value

            setOnClickListener {
                for (i in 0 until GameVariables.itemsPerClick.value)
                    addBall(
                        Ball(
                            Vector(nextInt(measuredWidth), nextInt(measuredHeight)),
                            Vector(nextInt(10), nextInt(10))
                        )
                    )
            }
        }
    }
}
