package com.weesnerdevelopment.playingwithgames.natureOfCode.randomWalker

import android.graphics.*
import math.Noise
import math.map

data class Walker(
    val pos: PointF,
    val speed: Int = 5,
    val radius: Float = 20f
) {
    private var paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
    }
    private val trailPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.argb(100, 100, 100, 100)
    }
    private var timeX = 0.0
    private var timeY = 10_000.0

    private fun step(width: Int, height: Int) {
        // random walking
        //pos.x += Random.nextInt(-speed, speed + 1)
        //pos.y += Random.nextInt(-speed, speed + 1)

        pos.x = Noise.perlin(timeX, timeY).map(0, 1, 0, width) as Float
        pos.y = Noise.perlin(timeY).map(0, 1, 0, height) as Float
        timeX += 0.01
        timeY += 0.01
    }

    fun update(width: Int, height: Int) {
        step(width, height)
    }

    fun updateTrail(path: Path) {
        path.addCircle(pos.x, pos.y, radius, Path.Direction.CW)
    }

    fun draw(canvas: Canvas) {
        canvas.drawCircle(pos.x, pos.y, radius, paint)
    }


    fun drawTrail(path: Path, canvas: Canvas) {
        canvas.drawPath(path, trailPaint)
    }
}

