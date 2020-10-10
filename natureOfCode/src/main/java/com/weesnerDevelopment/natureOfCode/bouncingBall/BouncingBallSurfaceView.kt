package com.weesnerDevelopment.natureOfCode.bouncingBall

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import com.weesnerDevelopment.gameEngine.math.Vector2D
import com.weesnerDevelopment.randomGameEngine.game.GameSurfaceView
import com.weesnerDevelopment.randomGameEngine.objects.Ball

class BouncingBallSurfaceView(context: Context) : GameSurfaceView(context) {
    private val ball = Ball(Vector2D(500, 500), Vector2D(10, 30.3))

    override fun onRender(canvas: Canvas) {
        canvas.drawColor(Color.WHITE)

        ball.draw(canvas)
        canvas.drawFPSInfo("")
    }

    override fun onUpdate() {
        super.onUpdate()
        ball.updatePos()
        ball.updateWrap(screenWidth!!, screenHeight!!)
    }

    override fun clear() {
        super.clear()
        ball.reset()
    }
}
