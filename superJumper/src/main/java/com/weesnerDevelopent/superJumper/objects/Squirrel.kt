package com.weesnerDevelopent.superJumper.objects

import com.weesnerDevelopent.superJumper.World
import com.weesnerDevelopment.gameEngine.math.*
import com.weesnerDevelopment.gameEngine.objects.DynamicGameObject

class Squirrel(
    override var position: Vector2D
) : DynamicGameObject(position, size) {
    companion object {
        val size = Size(1, .6)
    }

    private val squirrelVelocity = 3
    var startTime: Number = 0

    init {
        velocity.x = squirrelVelocity
    }

    override fun update(deltaTime: Number) {
        position += velocity * deltaTime
        bounds.lowerLeft = position - size.toVector2D() / 2

        if (position.x < size.width / 2) {
            position.x = size.width / 2
            velocity.x = squirrelVelocity
        }

        if (position.x > World.size.width - size.width / 2) {
            position.x = World.size.width - size.width / 2
            velocity.x = -squirrelVelocity
        }
        startTime += deltaTime
    }
}
