package com.weesnerdevelopment.playingwithgames.random.circlePhysics.collision

import android.graphics.PointF
import com.weesnerdevelopment.playingwithgames.GameFragment
import com.weesnerdevelopment.playingwithgames.random.circlePhysics.Circle
import com.weesnerdevelopment.playingwithgames.game.GameVariables
import kotlin.random.Random.Default.nextInt

class CircleCollisionPhysicsFragment : GameFragment() {
    override fun create() {
        gameView = CircleCollisionPhysicsSurfaceView(requireContext()).apply {
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
                            nextInt(50, 100).toFloat()
                        )
                    )
            }
        }
    }
}
