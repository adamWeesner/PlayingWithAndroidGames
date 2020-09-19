package com.weesnerdevelopment.playingwithgames.natureOfCode.ballWithForces

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import com.weesnerdevelopment.playingwithgames.game.GameSurfaceView
import com.weesnerdevelopment.playingwithgames.objects.ForceBall
import com.weesnerdevelopment.playingwithgames.objects.Vector

class BallWithForcesSurfaceView(context: Context) : GameSurfaceView(context) {
    private val ball = ForceBall(Vector.random)
    private val wind = Vector(0.01, 0)
    private val gravity = Vector(0, 0.75)

    override fun onUpdate() {
        ball.applyForce(wind)
        ball.applyForce(gravity)
        ball.checkEdges(screenWidth!!, screenHeight!!)
        ball.update(screenWidth!!, screenHeight!!)
    }

    override fun onRender(canvas: Canvas) {
        canvas.drawColor(Color.WHITE)

        ball.draw(canvas)
        canvas.drawFPSInfo("")
    }

    override fun clear() {
        super.clear()
        ball.reset()
    }
}
