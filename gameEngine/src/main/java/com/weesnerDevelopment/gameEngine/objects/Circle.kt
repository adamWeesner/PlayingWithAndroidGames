package com.weesnerDevelopment.gameEngine.objects

import com.weesnerDevelopment.gameEngine.math.*

data class Circle(
    val center: Vector,
    val radius: Number
) : Transform(center) {
    internal fun overlapRectangle(other: Rectangle): Boolean {
        var closestX = center.x
        var closestY = center.y

        closestX = when {
            center.x < other.lowerLeft.x -> other.lowerLeft.x
            center.x > other.lowerLeft.x + other.size.width -> other.lowerLeft.x + other.size.width
            else -> closestX
        }

        closestY = when {
            center.y < other.lowerLeft.y -> other.lowerLeft.y
            center.y > other.lowerLeft.y + other.size.height -> other.lowerLeft.y + other.size.height
            else -> closestY
        }

        return center.distance(Vector(closestX, closestY)) < radius.pow(2)
    }
}