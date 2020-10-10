package com.weesnerDevelopment.natureOfCode.ballWithForces

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import com.weesnerDevelopment.gameEngine.math.Vector2D
import com.weesnerDevelopment.randomGameEngine.game.GameSurfaceView
import com.weesnerDevelopment.randomGameEngine.objects.ForceBall
import kotlin.random.Random

class BallWithForcesSurfaceView(context: Context) : GameSurfaceView(context) {
    private val ball = ForceBall(Vector2D(Random.nextInt(10, 1000),Random.nextInt(10, 1000)))
    private val wind = Vector2D(0.01, 0)
    private val gravity = Vector2D(0, 0.75)

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
