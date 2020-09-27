package com.weesnerDevelopment.playingWithGames.natureOfCode.ballWithForces

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import com.weesnerDevelopment.gameEngine.math.Vector
import com.weesnerDevelopment.playingWithGames.game.GameSurfaceView
import com.weesnerDevelopment.playingWithGames.objects.ForceBall

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
