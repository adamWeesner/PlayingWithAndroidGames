package com.weesnerdevelopment.playingwithgames.random.circlePhysics

import android.graphics.PointF
import com.weesnerdevelopment.playingwithgames.GameFragment
import com.weesnerdevelopment.playingwithgames.game.GameVariables
import kotlin.random.Random.Default.nextInt

class CirclePhysicsFragment : GameFragment() {
    override fun create() {
        gameView = CirclePhysicsSurfaceView(requireContext()).apply {
            hardwareAccelerated = true
            loopType = GameVariables.looper.value

            setOnClickListener {
                for (i in 0 until GameVariables.itemsPerClick.value)
                    addCircle(
                        Circle(
                            PointF(
                                nextInt(measuredWidth).toFloat(),
                                nextInt(measuredHeight).toFloat()
                            ),
                            nextInt(10, 50).toFloat()
                        )
                    )
            }
        }
    }
}
