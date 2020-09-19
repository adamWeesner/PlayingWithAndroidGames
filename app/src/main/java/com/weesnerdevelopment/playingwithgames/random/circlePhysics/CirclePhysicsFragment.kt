package com.weesnerdevelopment.playingwithgames.random.circlePhysics

import com.weesnerdevelopment.playingwithgames.GameFragment
import com.weesnerdevelopment.playingwithgames.game.GameVariables
import com.weesnerdevelopment.playingwithgames.objects.PhysicsBall
import com.weesnerdevelopment.playingwithgames.objects.Vector
import kotlin.random.Random.Default.nextInt

class CirclePhysicsFragment : GameFragment() {
    override fun create() {
        gameView = CirclePhysicsSurfaceView(requireContext()).apply {
            hardwareAccelerated = true
            loopType = GameVariables.looper.value

            setOnClickListener {
                for (i in 0 until GameVariables.itemsPerClick.value.toInt())
                    addCircle(
                        PhysicsBall(
                            Vector(nextInt(measuredWidth), nextInt(measuredHeight)),
                            nextInt(10, 50)
                        )
                    )
            }
        }
    }
}
