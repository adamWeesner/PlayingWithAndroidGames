package com.weesnerDevelopent.superJumper.objects

import com.weesnerDevelopment.gameEngine.math.Size
import com.weesnerDevelopment.gameEngine.math.Vector2D
import com.weesnerDevelopment.gameEngine.math.plus
import com.weesnerDevelopment.gameEngine.objects.GameObject

class Spring(
    override val position: Vector2D
) : GameObject() {
    companion object {
        val size = Size(.3, .3)
    }

    override val size = Spring.size
}

class Coin(
    override val position: Vector2D
) : GameObject() {
    companion object {
        val size = Size(.5, .8)
        val score = 10
    }

    override val size = Coin.size

    var startTime: Number = 0

    override fun update(deltaTime: Number) {
        startTime += deltaTime
    }
}

class Castle(
    override val position: Vector2D
) : GameObject() {
    companion object {
        val size = Size(1.7, 1.7)
    }

    override val size = Castle.size
}