package com.weesnerdevelopment.playingwithgames.objects

import math.absoluteValue
import math.minus
import math.plus

sealed class Vec {
    open lateinit var x: Number
    open lateinit var y: Number

    abstract operator fun plus(other: Vec): Vec
    abstract fun minus(other: Vec): Vec

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

    override operator fun plus(other: Vec): Vector = Vector(
        x + other.x,
        y + other.y
    )

    override fun minus(other: Vec): Vector = Vector(
        x - other.x,
        y - other.y
    )
}

data class Vector3D(
    override var x: Number,
    override var y: Number,
    var z: Number
) : Vec() {
    companion object {
        val zero = Vector3D(0, 0, 0)
    }

    override operator fun plus(other: Vec): Vector3D = Vector3D(
        x + other.x,
        y + other.y,
        if (other is Vector3D) z + other.z else z
    )

    override fun minus(other: Vec): Vector3D = Vector3D(
        x - other.x,
        y - other.y,
        if (other is Vector3D) z - other.z else z
    )

    override fun toAbs(): Vector3D = super.toAbs().apply {
        z = z.absoluteValue
    } as Vector3D
}
