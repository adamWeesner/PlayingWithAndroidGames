package com.weesnerDevelopment.randoms.bouncingBall

import com.weesnerDevelopment.gameEngine.math.Vector2D
import com.weesnerDevelopment.randomGameEngine.game.GameVariables
import com.weesnerDevelopment.randomGameEngine.objects.Ball
import com.weesnerDevelopment.randomGameEngine.GameFragment
import kotlin.random.Random.Default.nextInt

class BallBounceFragment : GameFragment() {
    override fun create() {
        gameView = BallBounceSurfaceView(requireContext()).apply {
            hardwareAccelerated = true
            loopType = GameVariables.looper.value!!

            setOnClickListener {
                for (i in 0 until GameVariables.itemsPerClick.value!!)
                    addBall(
                        Ball(
                            Vector2D(nextInt(measuredWidth), nextInt(measuredHeight)),
                            Vector2D(nextInt(10), nextInt(10))
                        )
                    )
            }
        }
    }
}
