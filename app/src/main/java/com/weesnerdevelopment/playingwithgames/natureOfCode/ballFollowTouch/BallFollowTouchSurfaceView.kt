package com.weesnerdevelopment.playingwithgames.natureOfCode.ballFollowTouch

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.view.MotionEvent
import com.weesnerdevelopment.playingwithgames.game.GameSurfaceView
import com.weesnerdevelopment.playingwithgames.objects.Ball
import com.weesnerdevelopment.playingwithgames.objects.Vector

class BallFollowTouchSurfaceView(context: Context) : GameSurfaceView(context) {
    private val balls = (0 until 15).map { Ball(Vector.random, Vector(10, 10)) }
    private var touchPoint = Vector(0, 0)
    private val magnitude = 0.25

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        touchPoint = Vector(event?.x ?: 0, event?.y ?: 0)
        return super.onTouchEvent(event)
    }

    override fun onSurfaceChanged() {
        touchPoint = Vector(screenWidth!! / 2, screenHeight!! / 2)
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
            val dir = Vector.minus(touchPoint, ball.pos)
            dir.normalize()
            dir.times(magnitude)
            ball.updateTowardPoint(dir)
        }
    }
}
