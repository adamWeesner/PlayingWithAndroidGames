package math

import android.graphics.PointF
import kotlin.math.cos

/**
 * Noise referenced from https://github.com/processing/p5.js/blob/1.1.9/src/math/noise.js#L36.
 */
object Noise {
    private const val perlinYWrapB = 4
    private const val perlinYWrap = 1.shl(perlinYWrapB)
    private const val perlinZWrapB = 8
    private const val perlinZWrap = 1.shl(perlinZWrapB)
    private const val perlinSize = 4095

    private var perlinOctaves = 4 // default to medium smooth
    private var perlinAmplitudeFalloff = 0.5f // 50% reduction/octave

    private val Float.scaledCosine: Float get() = (0.5 * (1.0 - cos(this * Math.PI))).toFloat()
    private var perlin: Array<Float>? =
        null // will be initialized lazily by noise() or noiseSeed()

    private fun perlinValue(of: Int, extra: Int = 0) =
        perlin!![(of + extra).and(perlinSize)]

    fun perlin(valueX: Int, valueY: Int = 0, valueZ: Int = 0) =
        perlin(valueX.toFloat(), valueY.toFloat(), valueZ.toFloat())

    fun perlin(valueX: Double, valueY: Double = 0.0, valueZ: Double = 0.0) =
        perlin(valueX.toFloat(), valueY.toFloat(), valueZ.toFloat())

    fun perlin(value: Float, valueY: Float, valueZ: Float): Float {
        var r = 0f
        var amplitude = 0.5f
        val startPoint = PointF3D(value, valueY, valueZ).apply {
            toAbs()
        }
        val startFloored = startPoint.toPoint3D()
        val startMinusFloor = startPoint.minus(startFloored.asPointF3D())

        if (perlin == null)
            perlin = Array(perlinSize + 1) { Math.random().toFloat() }


        for (i in 0 until perlinOctaves) {
            var of =
                startFloored.x + startFloored.y.shl(perlinYWrapB) + startFloored.z.shl(perlinZWrapB)

            val pointF = PointF(startMinusFloor.x.scaledCosine, startMinusFloor.y.scaledCosine)
            val point3DN = PointF3D(
                perlinValue(of),
                perlinValue(of, perlinYWrap),
                perlinValue(of, perlinYWrap)
            )

            point3DN.apply {
                x += pointF.x * (perlinValue(of, 1) - point3DN.x)
                y += pointF.y * (perlinValue(of, perlinYWrap + 1) - y)
                x += pointF.y * (y - x)
            }

            of += perlinZWrap

            point3DN.apply {
                y = perlinValue(of)
                y += pointF.x * (perlinValue(of, 1) - y)
                z += pointF.x * (perlinValue(of, perlinYWrap + 1) - z)
                y += pointF.y * (z - y)
                x += startMinusFloor.z.scaledCosine * (y - x)
            }

            r += point3DN.x * amplitude
            amplitude *= perlinAmplitudeFalloff

            startFloored.shift(startMinusFloor)
        }
        return r
    }
}