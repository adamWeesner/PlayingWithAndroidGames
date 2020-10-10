package com.weesnerDevelopment.randomGameEngine.objects

import android.graphics.*
import com.weesnerDevelopment.gameEngine.math.Noise
import com.weesnerDevelopment.gameEngine.math.Vector2D
import com.weesnerDevelopment.gameEngine.math.map
import com.weesnerDevelopment.gameEngine.math.plus

data class Walker(
    override var position: Vector2D,
    override var velocity: Vector2D = Vector2D(5, 5),
    override val radius: Number = 20
): Circle(position, velocity, radius) {
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
        //position.x += Random.nextInt(-speed, speed + 1)
        //position.y += Random.nextInt(-speed, speed + 1)

        position.x = Noise.perlin(timeX, timeY).map(0, 1, 0, width)
        position.y = Noise.perlin(timeY).map(0, 1, 0, height)
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

