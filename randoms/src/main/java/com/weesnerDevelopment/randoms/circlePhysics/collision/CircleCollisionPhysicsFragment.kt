package com.weesnerDevelopment.randoms.circlePhysics.collision

import com.weesnerDevelopment.gameEngine.math.Vector2D
import com.weesnerDevelopment.randomGameEngine.game.GameVariables
import com.weesnerDevelopment.randomGameEngine.objects.PhysicsBall
import com.weesnerDevelopment.randomGameEngine.GameFragment
import kotlin.random.Random.Default.nextInt

class CircleCollisionPhysicsFragment : GameFragment() {
    override fun create() {
        gameView = CircleCollisionPhysicsSurfaceView(requireContext()).apply {
            hardwareAccelerated = true
            loopType = GameVariables.looper.value!!

            setOnClickListener {
                for (i in 0 until GameVariables.itemsPerClick.value!!.toInt())
                    addCircle(
                        PhysicsBall(
                            Vector2D(nextInt(measuredWidth), nextInt(measuredHeight)),
                            nextInt(50, 100)
                        )
                    )
            }
        }
    }
}
