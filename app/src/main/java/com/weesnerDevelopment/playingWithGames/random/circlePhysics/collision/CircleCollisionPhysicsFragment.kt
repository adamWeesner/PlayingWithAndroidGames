package com.weesnerDevelopment.playingWithGames.random.circlePhysics.collision

import com.weesnerDevelopment.gameEngine.math.Vector
import com.weesnerDevelopment.playingWithGames.GameFragment
import com.weesnerDevelopment.playingWithGames.game.GameVariables
import com.weesnerDevelopment.playingWithGames.objects.PhysicsBall
import kotlin.random.Random.Default.nextInt

class CircleCollisionPhysicsFragment : GameFragment() {
    override fun create() {
        gameView = CircleCollisionPhysicsSurfaceView(requireContext()).apply {
            hardwareAccelerated = true
            loopType = GameVariables.looper.value

            setOnClickListener {
                for (i in 0 until GameVariables.itemsPerClick.value.toInt())
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
