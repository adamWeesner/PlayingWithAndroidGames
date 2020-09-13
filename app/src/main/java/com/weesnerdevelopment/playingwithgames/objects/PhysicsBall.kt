package com.weesnerdevelopment.playingwithgames.objects

import math.*

data class PhysicsBall(
    override var pos: Vector,
    override val radius: Number,
    val gravity: Number = radius * 0.01,
    var velocity: Number = 1,
    override var speed: Vector = Vector.zero
) : Circle(pos, speed, radius) {
    var hitBottom = false
    var collided = false

    override fun update(width: Number, height: Number) {
        if (hitBottom) velocity = 0f
        else velocity += gravity

        pos.y += velocity
    }

    override fun adjustForInterpolation(interpolation: Number) {
        if (!hitBottom) pos.y += interpolation
    }

    fun checkHitBottom(bottom: Number) {
        if (pos.y - radius >= bottom) {
            hitBottom = true
            pos.y = bottom - radius
        }
    }

    fun checkCollision(other: PhysicsBall) {
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
}