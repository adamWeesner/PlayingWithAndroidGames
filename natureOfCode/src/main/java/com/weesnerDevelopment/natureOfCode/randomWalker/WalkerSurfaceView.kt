package com.weesnerDevelopment.natureOfCode.randomWalker

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Path
import com.weesnerDevelopment.gameEngine.math.Vector2D
import com.weesnerDevelopment.randomGameEngine.game.GameSurfaceView
import com.weesnerDevelopment.randomGameEngine.objects.Walker
import kotlinx.coroutines.launch

class WalkerSurfaceView(context: Context) : GameSurfaceView(context) {
    private lateinit var walker: Walker

    private var path: Path = Path()

    override fun resume() {
        super.resume()
        walker = Walker(Vector2D(measuredWidth.toFloat() / 2, measuredHeight.toFloat() / 2))
    }

    override fun onRender(canvas: Canvas) {
        canvas.drawColor(Color.WHITE)

        walker.draw(canvas)
        walker.drawTrail(path, canvas)
        canvas.drawFPSInfo("")

    }

    override fun onUpdate() {
        scope?.launch {
            walker.update(screenWidth!!, screenHeight!!)
            walker.updateTrail(path)
        }
    }
}
