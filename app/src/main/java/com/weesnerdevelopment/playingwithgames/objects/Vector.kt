package com.weesnerdevelopment.playingwithgames.objects

import math.*

sealed class Vec {
    open lateinit var x: Number
    open lateinit var y: Number

    abstract operator fun plus(other: Vec): Vec
    abstract operator fun minus(other: Vec): Vec
    abstract operator fun times(scalar: Number): Vec
    abstract operator fun div(scalar: Number): Vec

    abstract fun magnitude(): Number

    open fun normalize() {
        val mag = magnitude()
        if (mag != 0) div(mag)
    }

    open fun toAbs(): Vec = apply {
        x = x.absoluteValue
        y = y.absoluteValue
    }
}

data class Vector(
    override var x: Number,
    override var y: Number
) : Vec() {
    companion object {
        val zero = Vector(0, 0)
    }

    override operator fun plus(other: Vec) = apply {
        x += other.x
        y += other.y
    }

    override fun minus(other: Vec) = apply {
        x -= other.x
        y -= other.y
    }

    override fun times(scalar: Number) = apply {
        x *= scalar
        y *= scalar
    }

    override fun div(scalar: Number) = apply {
        x /= scalar
        y /= scalar
    }

    override fun magnitude() = sqrt(x.pow(2) + y.pow(2))
}

data class Vector3D(
    override var x: Number,
    override var y: Number,
    var z: Number
) : Vec() {
    companion object {
        val zero = Vector3D(0, 0, 0)
    }

    override operator fun plus(other: Vec) = apply {
        x += other.x
        y += other.y
        if (other is Vector3D) z += other.z else z
    }

    override fun minus(other: Vec) = apply {
        x -= other.x
        y -= other.y
        if (other is Vector3D) z -= other.z else z
    }

    override fun times(scalar: Number) = apply {
        x *= scalar
        y *= scalar
        z *= scalar
    }

    override fun div(scalar: Number) = apply {
        x /= scalar
        y /= scalar
        z /= scalar
    }

    override fun magnitude() = sqrt(x.pow(2) + y.pow(2) + z.pow(2))

    override fun toAbs(): Vector3D = super.toAbs().apply {
        z = z.absoluteValue
    } as Vector3D
}
