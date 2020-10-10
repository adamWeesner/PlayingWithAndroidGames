package com.weesnerDevelopment.gameEngine.math

 internal interface V<T> {
    var x: Number
    var y: Number

    fun length(): Number
    fun magnitudeSquare(): Number
    fun toAbs(): T

    operator fun plus(other: T): T
    operator fun minus(other: T): T
    operator fun times(scalar: Number): T
    operator fun div(scalar: Number): T
}

internal interface V2D<T : V2D<T>> : V<T> {
    override fun toAbs(): T

    override operator fun plus(other: T): T
    override operator fun minus(other: T): T
    override operator fun times(scalar: Number): T
    override operator fun div(scalar: Number): T

    fun normalize(): T {
        val mag = length()
        return if (mag != 0) div(mag) else div(1)
    }

    fun limit(max: Number): V2D<T> {
        val mag = magnitudeSquare()
        return if (mag > max.pow(2)) this / sqrt(mag) * max else this
    }

    fun distanceSquared(other: T) =
        (x - other.x).pow(2) + (y - other.y).pow(2)

    fun distance(other: T) =
        sqrt(distanceSquared(other))

    override fun length() =
        sqrt(x.pow(2) + y.pow(2))

    override fun magnitudeSquare() =
        x.pow(2) + y.pow(2)

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

data class Vector2D(
    override var x: Number,
    override var y: Number
) : V2D<Vector2D> {
    override fun plus(other: Vector2D) = Vector2D(x + other.x, y + other.y)

    override fun minus(other: Vector2D) = Vector2D(x - other.x, y - other.y)

    override fun times(scalar: Number) = Vector2D(x * scalar, y * scalar)

    override fun div(scalar: Number) = Vector2D(x / scalar, y / scalar)

    override fun toAbs() = Vector2D(x.absoluteValue, y.absoluteValue)
}

data class UV(
    var u: Number,
    var v: Number
) : V2D<UV> {
    @Deprecated(
        level = DeprecationLevel.ERROR,
        message = ("Use `u` instead"),
        replaceWith = ReplaceWith("u")
    )
    override var x: Number = 0
        get() = u

    @Deprecated(
        level = DeprecationLevel.ERROR,
        message = ("Use `v` instead"),
        replaceWith = ReplaceWith("v")
    )
    override var y: Number = 0
        get() = v

    override fun plus(other: UV) = UV(u + other.u, v + other.v)

    override fun minus(other: UV) = UV(u - other.u, v - other.v)

    override fun times(scalar: Number) = UV(u * scalar, v * scalar)

    override fun div(scalar: Number) = UV(u / scalar, v / scalar)

    override fun toAbs() = UV(u.absoluteValue, v.absoluteValue)
}

data class Size(
    val width: Number,
    val height: Number
) : V2D<Size> {
    @Deprecated(
        level = DeprecationLevel.ERROR,
        message = ("Use `width` instead"),
        replaceWith = ReplaceWith("width")
    )
    override var x: Number = width

    @Deprecated(
        level = DeprecationLevel.ERROR,
        message = ("Use `height` instead"),
        replaceWith = ReplaceWith("height")
    )
    override var y: Number = height

    override fun plus(other: Size) = Size(width + other.width, height + other.height)

    override fun minus(other: Size) = Size(width - other.width, height - other.height)

    override fun times(scalar: Number) = Size(width * scalar, height * scalar)

    override fun div(scalar: Number) = Size(width / scalar, height / scalar)

    override fun toAbs() = Size(width.absoluteValue, height.absoluteValue)

    fun toVector2D() = Vector2D(width, height)
}


internal interface V3D<T : V3D<T>> : V<T> {
    var z: Number

    override fun toAbs(): T

    override operator fun plus(other: T): T
    override operator fun minus(other: T): T
    override operator fun times(scalar: Number): T
    override operator fun div(scalar: Number): T

    fun normalize(): T {
        val mag = length()
        return if (mag != 0) div(mag) else div(1)
    }

    fun limit(max: Number): V3D<T> {
        val mag = magnitudeSquare()
        return if (mag > max.pow(2)) this / sqrt(mag) * max else this
    }

    fun distance(other: T) =
        sqrt((x - other.x).pow(2) + (y - other.y).pow(2) + (z - other.z).pow(2))

    override fun length() =
        sqrt(x.pow(2) + y.pow(2) + z.pow(2))

    override fun magnitudeSquare() =
        x.pow(2) + y.pow(2) + z.pow(2)

    fun angle(): Number {
        var angle = atan2(y, x).toDegrees
        if (angle < 0) angle += 360
        return angle
    }

    // todo figure out how to rotate a 3d object
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

data class Vector3D(
    override var x: Number,
    override var y: Number,
    override var z: Number
) : V3D<Vector3D> {
    override operator fun plus(other: Vector3D) =
        Vector3D(x + other.x, y + other.y, z + other.z)

    override fun minus(other: Vector3D) =
        Vector3D(x - other.x, y - other.y, z - other.z)

    override fun times(scalar: Number) =
        Vector3D(x * scalar, y * scalar, z * scalar)

    override fun div(scalar: Number) =
        Vector3D(x / scalar, y / scalar, z / scalar)

    override fun distance(other: Vector3D): Number {
        val distance = this - other

        return sqrt(distance.x.pow(2) + distance.y.pow(2) + distance.z.pow(2))
    }

    override fun toAbs() = Vector3D(x.absoluteValue, y.absoluteValue, z.absoluteValue)
}
