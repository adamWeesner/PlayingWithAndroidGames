package com.weesnerDevelopment.gameEngine.objects

import com.weesnerDevelopment.gameEngine.math.*

open class Transform(
    open val position: Vector
) {
    open fun isOverlapping(other: Transform) = when (this) {
        is Circle -> {
            when (other) {
                is Circle -> {
                    val distance = center.distance(other.center)
                    distance <= (radius + other.radius).pow(2)
                }
                is Rectangle -> overlapRectangle(other)
                else -> throw IllegalArgumentException()
            }
        }
        is Rectangle -> {
            when (other) {
                is Circle -> {
                    other.overlapRectangle(this)
                }
                is Rectangle ->
                    lowerLeft.x < other.lowerLeft.x + other.size.width &&
                            lowerLeft.x + size.width > other.lowerLeft.x &&
                            lowerLeft.y < other.lowerLeft.y + other.size.height &&
                            lowerLeft.y + size.height > other.lowerLeft.y
                else -> throw IllegalArgumentException()
            }
        }
        else -> throw IllegalArgumentException()
    }

    fun inCircle(circle: Circle) =
        circle.center.distance(position) < circle.radius.pow(2)

    fun inRectangle(rectangle: Rectangle) = rectangle.lowerLeft.x <= position.x &&
            rectangle.lowerLeft.x + rectangle.size.width >= position.x &&
            rectangle.lowerLeft.y <= position.y &&
            rectangle.lowerLeft.y + rectangle.size.height >= position.y
}