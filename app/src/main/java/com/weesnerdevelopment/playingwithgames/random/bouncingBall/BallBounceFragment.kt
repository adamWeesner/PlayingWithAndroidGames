package com.weesnerdevelopment.playingwithgames.random.bouncingBall

import android.graphics.PointF
import com.weesnerdevelopment.playingwithgames.GameFragment
import com.weesnerdevelopment.playingwithgames.game.GameVariables
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
                            PointF(
                                nextInt(measuredWidth).toFloat(),
                                nextInt(measuredHeight).toFloat()
                            )
                        )
                    )
            }
        }
    }
}
