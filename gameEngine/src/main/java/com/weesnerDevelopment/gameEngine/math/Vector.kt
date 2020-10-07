package com.weesnerDevelopment.gameEngine.math

import kotlin.random.Random.Default.nextInt

sealed class Vec {
    open lateinit var x: Number
    open lateinit var y: Number

    abstract operator fun plus(other: Vec): Vec
    abstract operator fun minus(other: Vec): Vec
    abstract operator fun times(scalar: Number): Vec
    abstract operator fun div(scalar: Number): Vec

    abstract fun length(): Number
    abstract fun magnitudeSquare(): Number
    abstract fun distance(other: Vec): Number

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

sealed class Vec2D(
    override var x: Number,
    override var y: Number
) : Vec() {
    override operator fun plus(other: Vec): Vec2D {
        x += other.x
        y += other.y

        return this
    }

    override fun minus(other: Vec): Vec2D {
        x -= other.x
        y -= other.y

        return this
    }

    override fun times(scalar: Number): Vec2D {
        x *= scalar
        y *= scalar

        return this
    }

    override fun div(scalar: Number): Vec2D {
        x /= scalar
        y /= scalar

        return this
    }

    override fun length() = sqrt(x.pow(2) + y.pow(2))
    override fun magnitudeSquare() = x.pow(2) + y.pow(2)
    override fun distance(other: Vec) = sqrt((x - other.x).pow(2) + (y - other.y).pow(2))

    fun angle(): Number {
        var angle = atan2(y, x).toDegrees
        if (angle < 0) angle += 360
        return angle
    }

    fun rotate(angle: Number) {
        val rad: Number = angle.toRadians
        val cos = cos(rad)
        val sin = sin(rad)

        val newX = x * cos - y * sin
        val newY = x * sin + y * cos

        x = newX
        y = newY
    }
}

data class UV(
    var u: Number,
    var v: Number
) : Vec2D(u.toFloat(), v.toFloat()) {
    fun getU() = u.toFloat()
    fun getV() = v.toFloat()

    @Deprecated(
        level = DeprecationLevel.ERROR,
        message = ("Use `u` instead"),
        replaceWith = ReplaceWith("u")
    )
    override var x: Number = u

    @Deprecated(
        level = DeprecationLevel.ERROR,
        message = ("Use `v` instead"),
        replaceWith = ReplaceWith("v")
    )
    override var y: Number = v
}

data class Vector(
    override var x: Number,
    override var y: Number
) : Vec2D(x, y) {
    constructor(both: Number) : this(both, both)

    companion object {
        val zero get() = Vector(0, 0)
        val random get() = Vector(nextInt(1000), nextInt(1000))

        fun add(first: Vec2D, second: Vec2D) = Vector(
            first.x + second.x,
            first.y + second.y
        )

        fun minus(first: Vec2D, second: Vec2D) = Vector(
            first.x - second.x,
            first.y - second.y
        )

        fun times(vector: Vec2D, scalar: Number) = Vector(
            vector.x * scalar,
            vector.y * scalar
        )

        fun div(vector: Vec2D, scalar: Number) = Vector(
            vector.x / scalar,
            vector.y / scalar
        )
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

    override operator fun plus(other: Vec): Vector3D {
        x += other.x
        y += other.y
        if (other is Vector3D) z += other.z else z

        return this
    }

    override fun minus(other: Vec): Vector3D {
        x -= other.x
        y -= other.y
        if (other is Vector3D) z -= other.z else z

        return this
    }

    override fun times(scalar: Number): Vector3D {
        x *= scalar
        y *= scalar
        z *= scalar

        return this
    }

    override fun div(scalar: Number): Vector3D {
        x /= scalar
        y /= scalar
        z /= scalar

        return this
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
