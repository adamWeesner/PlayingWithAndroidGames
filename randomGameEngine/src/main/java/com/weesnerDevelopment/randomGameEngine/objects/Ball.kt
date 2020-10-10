package com.weesnerDevelopment.randomGameEngine.objects

import android.graphics.Canvas
import com.weesnerDevelopment.gameEngine.math.*
import com.weesnerDevelopment.gameEngine.objects.Rectangle

data class Ball(
    override var position: Vector2D,
    override var velocity: Vector2D,
    @Volatile
    var acceleration: Vector2D = Vector2D(-0.001, 0.01),
    override val radius: Number = 50,
    val topSpeed: Number = 10
) : Circle(position, velocity, radius) {
    override fun draw(canvas: Canvas) {
        canvas.drawCircle()
    }

    override fun update(width: Number, height: Number) {
        val rect = Rectangle(Vector2D(0, height), Size(width, -height))

        velocity += acceleration
        position += velocity

        if(position.inRectangle(rect)) return

        if(position.x - radius < 0) {
            position.x = radius + 1
            velocity.x *= -1
        }else if(position.x + radius > width) {
            position.x = width - radius - 1
            velocity.x *= -1
        }

        if(position.y - radius < 0) {
            position.y = radius + 1
            velocity.y *= -1
        }else if(position.y + radius > height) {
            position.y = height - radius - 1
            velocity.y *= -1
        }
    }

    fun updatePos() {
        updateTowardPoint(acceleration)
    }

    fun updateTowardPoint(point: Vector2D){
        velocity += point
        velocity = velocity.limit(topSpeed)
        position += velocity
    }

    fun updateWrap(width: Number, height: Number){
        updatePos()
        wrapEdges(width, height)
    }
}
