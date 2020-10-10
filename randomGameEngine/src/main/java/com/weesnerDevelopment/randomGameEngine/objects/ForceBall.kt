package com.weesnerDevelopment.randomGameEngine.objects

import android.graphics.Canvas
import com.weesnerDevelopment.gameEngine.math.Vector2D
import kotlin.random.Random

data class ForceBall(
    @Volatile
    override var position: Vector2D,
    @Volatile
    override var velocity: Vector2D = Vector2D(0, 0),
    @Volatile
    var acceleration: Vector2D = Vector2D(0, 0),
    override val radius: Number = 50,
    val mass: Number = 2
) : Circle(position, velocity, radius) {
    override fun draw(canvas: Canvas) {
        canvas.drawCircle()
    }

    override fun update(width: Number, height: Number) {
        velocity += acceleration
        position += velocity
        acceleration *= 0
    }

    fun applyForce(force: Vector2D) {
        val forceToUse = force / mass
        acceleration += forceToUse
    }

    override fun reset() {
        position = Vector2D(Random.nextInt(10, 1000), Random.nextInt(10, 1000))
        velocity = Vector2D(0, 0)
        acceleration *= 0
    }
}
