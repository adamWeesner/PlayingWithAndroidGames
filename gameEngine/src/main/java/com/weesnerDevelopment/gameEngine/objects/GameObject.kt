package com.weesnerDevelopment.gameEngine.objects

import com.weesnerDevelopment.gameEngine.math.Vector
import com.weesnerDevelopment.gameEngine.math.div
import com.weesnerDevelopment.gameEngine.math.minus
import com.weesnerDevelopment.gameEngine.util.Size

abstract class GameObject {
    abstract val position: Vector
    abstract val size: Size
    val bounds
        get() = Rectangle(
            Vector(position.x - size.width / 2f, position.y - size.height / 2f),
            size
        )
}

data class StaticGameObject(
    override val position: Vector,
    override val size: Size
) : GameObject()

data class DynamicGameObject(
    override var position: Vector,
    override val size: Size
) : GameObject() {
    val velocity = Vector.zero
    val acceleration = Vector.zero
}
