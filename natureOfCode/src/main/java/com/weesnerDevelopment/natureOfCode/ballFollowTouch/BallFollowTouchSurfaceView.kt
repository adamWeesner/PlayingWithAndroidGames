package com.weesnerDevelopment.natureOfCode.ballFollowTouch

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.view.MotionEvent
import com.weesnerDevelopment.gameEngine.math.Vector2D
import com.weesnerDevelopment.randomGameEngine.game.GameSurfaceView
import com.weesnerDevelopment.randomGameEngine.objects.Ball
import kotlin.random.Random

class BallFollowTouchSurfaceView(context: Context) : GameSurfaceView(context) {
    private val balls = (0 until 15).map {
        Ball(
            Vector2D(Random.nextInt(10, 1000), Random.nextInt(10, 1000)),
            Vector2D(10, 10)
        )
    }
    private var touchPoint = Vector2D(0, 0)
    private val magnitude = 0.0005

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        touchPoint = Vector2D(event?.x ?: 0, event?.y ?: 0)
        return super.onTouchEvent(event)
    }

    override fun onSurfaceChanged() {
        touchPoint = Vector2D(screenWidth!! / 2, screenHeight!! / 2)
    }

    override fun onRender(canvas: Canvas) {
        canvas.drawColor(Color.WHITE)

        balls.forEach { ball ->
            ball.draw(canvas)
        }
        canvas.drawFPSInfo("")
    }

    override fun onUpdate() {
        balls.forEach { ball ->
            var dir = touchPoint - ball.position
            dir.normalize()
            dir *= magnitude
            ball.updateTowardPoint(dir)
        }
    }
}
