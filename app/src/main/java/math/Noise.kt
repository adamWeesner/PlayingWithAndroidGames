package math

import com.weesnerdevelopment.playingwithgames.natureOfCode.objects.Vector
import com.weesnerdevelopment.playingwithgames.natureOfCode.objects.Vector3D

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
    private var perlinAmplitudeFalloff: Number = 0.5 // 50% reduction/octave

    private val Number.scaledCosine: Number get() = 0.5 * (1.0 - cos(this * Math.PI))

    // will be initialized lazily by [perlin]
    private var perlin: Array<Number>? = null

    private fun perlinValue(of: Number, extra: Number = 0) =
        perlin!![(of + extra).and(perlinSize)]

    fun perlin(value: Number, valueY: Number = 0, valueZ: Number = 0): Number {
        var r: Number = 0
        var amplitude: Number = 0.5
        val startPoint = Vector3D(value, valueY, valueZ).apply {
            toAbs()
        }
        val startFloored = Vector3D(
            floor(startPoint.x),
            floor(startPoint.y),
            floor(startPoint.z)
        )
        val startMinusFloor = startPoint.minus(startFloored)

        if (perlin == null)
            perlin = Array(perlinSize + 1) { Math.random() }


        for (i in 0 until perlinOctaves) {
            var of: Number =
                startFloored.x + startFloored.y.shl(perlinYWrapB) + startFloored.z.shl(perlinZWrapB)

            val pointF = Vector(startMinusFloor.x.scaledCosine, startMinusFloor.y.scaledCosine)
            val point3DN = Vector3D(
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

            startFloored.x = startFloored.x.shl(1)
            startMinusFloor.x *= 2
            if (startMinusFloor.x >= 1) {
                startFloored.x++
                startMinusFloor.x--
            }

            startFloored.y = startFloored.y.shl(1)
            startMinusFloor.y *= 2
            if (startMinusFloor.y >= 1) {
                startFloored.y++
                startMinusFloor.y--
            }

            startFloored.z = startFloored.z.shl(1)
            startMinusFloor.z *= 2
            if (startMinusFloor.z >= 1) {
                startFloored.z++
                startMinusFloor.z--
            }
        }
        return r
    }
}