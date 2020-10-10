package com.weesnerDevelopent.superJumper.objects

import com.weesnerDevelopent.superJumper.Assets.frameDuration
import com.weesnerDevelopent.superJumper.World
import com.weesnerDevelopment.gameEngine.math.*
import com.weesnerDevelopment.gameEngine.objects.DynamicGameObject

enum class PlatformType {
    Static,
    Moving
}

enum class PlatformState {
    Normal,
    Pulverizing
}

data class Platform(
    override var position: Vector2D,
    val type: PlatformType
) : DynamicGameObject(position, size) {
    companion object {
        val size = Size(2, .5)
    }

    val pulverizeTime = frameDuration * 4
    private val platformVelocity: Number = 2

    var state = PlatformState.Normal
    var startTime: Number = 0

    init {
        if (type == PlatformType.Moving) velocity.x = platformVelocity
    }

    override fun update(deltaTime: Number) {
        if (type == PlatformType.Moving) {
            position += Vector2D(velocity.x * deltaTime, 0)
            bounds.lowerLeft = position - size.toVector2D() / 2

            if (position.x < size.width / 2) {
                velocity.x = -velocity.x
                position.x = size.width / 2
            }

            if (position.x > World.size.width - size.width / 2) {
                velocity.x = -velocity.x
                position.x = World.size.width - size.width / 2
            }
        }

        startTime += deltaTime
    }

    fun pulverize() {
        state = PlatformState.Pulverizing
        startTime = 0
        velocity.x = 0
    }
}