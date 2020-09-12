package com.weesnerdevelopment.playingwithgames.random.circlePhysics

import android.graphics.Path
import android.graphics.PointF
import kotlin.math.absoluteValue
import kotlin.math.pow
import kotlin.math.sqrt

data class Circle(
    var pos: PointF,
    val radius: Float,
    val gravity: Float = radius * .01f,
    var velocity: Float = 1f
) {
    var path: Path? = null

    var hitBottom = false
    var collided = false

    fun update(interpolation: Float) {
        if (hitBottom)
            velocity = 0f
        else
            velocity += gravity + interpolation

        pos.y += velocity
    }

    fun checkCollision(other: Circle) {
        val xDistance = (pos.x - other.pos.x).absoluteValue
        val yDistance = (pos.y - other.pos.y).absoluteValue

        val distance = sqrt(xDistance.pow(2) + yDistance.pow(2))

        println("distance $distance | radii ${radius + other.radius}")

        if (distance <= radius + other.radius) {
            collided = true
            other.collided = true
            println("collided...")
        }
    }

    fun checkHitBottom(bottom: Int) {
        if (pos.y - radius >= bottom) {
            hitBottom = true
            pos.y = bottom - radius
        }
    }

    fun addToPath(selectedPath: Path) {
        if (path == null) path = selectedPath

        selectedPath.addCircle(pos.x, pos.y, radius, Path.Direction.CW)
    }
}
