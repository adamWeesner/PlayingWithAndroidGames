package com.weesnerdevelopment.playingwithgames.random.circlePhysics.collision

import com.weesnerdevelopment.playingwithgames.GameFragment
import com.weesnerdevelopment.playingwithgames.game.GameVariables
import com.weesnerdevelopment.playingwithgames.objects.PhysicsBall
import com.weesnerdevelopment.playingwithgames.objects.Vector
import kotlin.random.Random.Default.nextInt

class CircleCollisionPhysicsFragment : GameFragment() {
    override fun create() {
        gameView = CircleCollisionPhysicsSurfaceView(requireContext()).apply {
            hardwareAccelerated = true
            loopType = GameVariables.looper.value

            setOnClickListener {
                for (i in 0 until GameVariables.itemsPerClick.value)
                    addCircle(
                        PhysicsBall(
                            Vector(nextInt(measuredWidth), nextInt(measuredHeight)),
                            nextInt(50, 100)
                        )
                    )
            }
        }
    }
}
