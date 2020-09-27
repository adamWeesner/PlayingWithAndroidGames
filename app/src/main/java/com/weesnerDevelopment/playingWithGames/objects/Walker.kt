package com.weesnerDevelopment.playingWithGames.objects

import android.graphics.*
import com.weesnerDevelopment.gameEngine.math.Noise
import com.weesnerDevelopment.gameEngine.math.Vector
import com.weesnerDevelopment.gameEngine.math.map
import com.weesnerDevelopment.gameEngine.math.plus

data class Walker(
    override var pos: Vector,
    override var velocity: Vector = Vector(5, 5),
    override val radius: Number = 20
): Circle(pos, velocity, radius) {
    override var paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
    }
    private val trailPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.argb(100, 100, 100, 100)
    }
    private var timeX: Number = 0
    private var timeY: Number = 10_000

    private fun step(width: Number, height: Number) {
        // random walking
        //pos.x += Random.nextInt(-speed, speed + 1)
        //pos.y += Random.nextInt(-speed, speed + 1)

        pos.x = Noise.perlin(timeX, timeY).map(0, 1, 0, width)
        pos.y = Noise.perlin(timeY).map(0, 1, 0, height)
        timeX += 0.01
        timeY += 0.01
    }

    override fun update(width: Number, height: Number) {
        step(width, height)
    }

    fun updateTrail(path: Path) {
        addToPath(path)
    }

    override fun draw(canvas: Canvas) {
        canvas.drawCircle()
    }


    fun drawTrail(path: Path, canvas: Canvas) {
        canvas.drawPath(path, trailPaint)
    }
}

