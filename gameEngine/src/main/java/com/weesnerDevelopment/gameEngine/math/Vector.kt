package com.weesnerDevelopment.gameEngine.math

import kotlin.random.Random.Default.nextInt

sealed class Vec {
    open lateinit var x: Number
    open lateinit var y: Number

    abstract operator fun plus(other: Vec)
    abstract operator fun minus(other: Vec)
    abstract operator fun times(scalar: Number)
    abstract operator fun div(scalar: Number)

    abstract fun length(): Number
    abstract fun magnitudeSquare(): Number
    abstract fun distance(other: Vec): Number

    val toRadians: Number = (1 / 180f) * Math.PI.toFloat()
    val toDegrees: Number = (1 / Math.PI.toFloat()) * 180f

    open fun normalize() {
        val mag = length()
        if (mag != 0) div(mag)
    }

    open fun limit(max: Number): Vec {
        val mag = magnitudeSquare()
        if (mag > max.pow(2)) {
            this / sqrt(mag)
            this * max
        }

        return this
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

    constructor(both: Number) : this(both, both)

    companion object {
        val zero get() = Vector(0, 0)
        val random get() = Vector(nextInt(1000), nextInt(1000))

        fun add(first: Vector, second: Vector) = Vector(
            first.x + second.x,
            first.y + second.y
        )

        fun minus(first: Vector, second: Vector) = Vector(
            first.x - second.x,
            first.y - second.y
        )

        fun times(vector: Vector, scalar: Number) = Vector(
            vector.x * scalar,
            vector.y * scalar
        )

        fun div(vector: Vector, scalar: Number) = Vector(
            vector.x / scalar,
            vector.y / scalar
        )
    }

    override operator fun plus(other: Vec) {
        x += other.x
        y += other.y
    }

    override fun minus(other: Vec) {
        x -= other.x
        y -= other.y
    }

    override fun times(scalar: Number) {
        x *= scalar
        y *= scalar
    }

    override fun div(scalar: Number) {
        x /= scalar
        y /= scalar
    }

    override fun length() = sqrt(x.pow(2) + y.pow(2))
    override fun magnitudeSquare() = x.pow(2) + y.pow(2)
    override fun distance(other: Vec) = sqrt((x - other.x).pow(2) + (y - other.y).pow(2))

    fun angle(): Number {
        var angle = atan2(y, x) * toDegrees
        if (angle < 0) angle += 360
        return angle
    }

    fun rotate(angle: Number) {
        val rad: Number = angle * toRadians
        val cos = cos(rad)
        val sin = sin(rad)

        val newX = x * cos - y * sin
        val newY = x * sin + y * cos

        x = newX
        y = newY
    }
}

data class Vector3D(
    override var x: Number,
    override var y: Number,
    var z: Number
) : Vec() {
    companion object {
        val zero get() = Vector3D(0, 0, 0)

        fun plus(first: Vector3D, second: Vector3D) = Vector3D(
            first.x + second.x,
            first.y + second.y,
            first.z + second.z
        )

        fun minus(first: Vector3D, second: Vector3D) = Vector3D(
            first.x - second.x,
            first.y - second.y,
            first.z - second.z
        )

        fun times(vector: Vector3D, scalar: Number) = Vector3D(
            vector.x * scalar,
            vector.y * scalar,
            vector.z * scalar
        )

        fun div(vector: Vector3D, scalar: Number) = Vector3D(
            vector.x / scalar,
            vector.y / scalar,
            vector.z / scalar
        )
    }

    override operator fun plus(other: Vec) {
        x += other.x
        y += other.y
        if (other is Vector3D) z += other.z else z
    }

    override fun minus(other: Vec) {
        x -= other.x
        y -= other.y
        if (other is Vector3D) z -= other.z else z
    }

    override fun times(scalar: Number) {
        x *= scalar
        y *= scalar
        z *= scalar
    }

    override fun div(scalar: Number) {
        x /= scalar
        y /= scalar
        z /= scalar
    }

    override fun length() = sqrt(x.pow(2) + y.pow(2) + z.pow(2))
    override fun magnitudeSquare() = x.pow(2) + y.pow(2) + z.pow(2)
    override fun distance(other: Vec): Number {
        val distX = x - other.x
        val distY = y - other.y
        val distZ = if (other is Vector3D) z - other.z else z

        return sqrt(distX.pow(2) + distY.pow(2) + distZ.pow(2))
    }

    override fun toAbs(): Vector3D = super.toAbs().apply {
        z = z.absoluteValue
    } as Vector3D
}
