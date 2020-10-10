package com.weesnerDevelopent.superJumper.objects

import com.weesnerDevelopent.superJumper.World
import com.weesnerDevelopment.gameEngine.math.*
import com.weesnerDevelopment.gameEngine.objects.DynamicGameObject

enum class BobState {
    Jump,
    Fall,
    Hit
}

class Bob(
    override var position: Vector2D
) : DynamicGameObject(position, Size(.8, .8)) {
    val jumpVelocity: Number = 11
    val moveVelocity: Number = 20

    var state = BobState.Fall
    var startTime: Number = 0

    override fun update(deltaTime: Number) {
        velocity += World.gravity * deltaTime
        position += velocity * deltaTime

        bounds.lowerLeft = position - bounds.size.toVector2D() / 2

        if (velocity.y > 0 && state != BobState.Hit) {
            if (state != BobState.Jump) {
                state = BobState.Jump
                startTime = 0
            }
        }

        if (velocity.y < 0 && state != BobState.Hit) {
            if (state != BobState.Fall) {
                state = BobState.Fall
                startTime = 0
            }
        }

        if (position.x < 0) position.x = World.size.width
        if (position.x > World.size.width) position.x = 0

        startTime += deltaTime
    }

    fun hitSquirrel() {
        velocity = Vector2D(0, 0)
        state = BobState.Hit
        startTime = 0
    }

    fun hitPlatform() {
        velocity.y = jumpVelocity
        state = BobState.Jump
        startTime = 0
    }

    fun hitSpring() {
        velocity.y = jumpVelocity * 1.5
        state = BobState.Jump
        startTime = 0
    }
}