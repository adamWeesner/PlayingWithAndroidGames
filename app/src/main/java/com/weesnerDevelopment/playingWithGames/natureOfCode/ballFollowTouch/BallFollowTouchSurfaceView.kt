package com.weesnerDevelopment.playingWithGames.natureOfCode.ballFollowTouch

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.view.MotionEvent
import com.weesnerDevelopment.gameEngine.math.Vector
import com.weesnerDevelopment.playingWithGames.game.GameSurfaceView
import com.weesnerDevelopment.playingWithGames.objects.Ball

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
