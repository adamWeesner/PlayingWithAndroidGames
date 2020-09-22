package com.weesnerdevelopment.playingwithgames.natureOfCode.perlinBackground

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import androidx.core.graphics.set
import com.weesnerdevelopment.playingwithgames.game.GameSurfaceView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.weesnerdevelopment.playingwithgames.math.Noise
import com.weesnerdevelopment.playingwithgames.math.map
import com.weesnerdevelopment.playingwithgames.math.plus

class PerlinBackgroundSurfaceView(context: Context) : GameSurfaceView(context) {
    private var bitmap: Bitmap? = null
    private var xOff: Number = 0
    private var yOff: Number = 0

    override fun resume() {
        super.resume()
        scope?.launch {
            withContext(Dispatchers.IO) {
                if (bitmap == null)
                    bitmap = Bitmap.createBitmap(screenWidth!!, screenHeight!!, Bitmap.Config.ARGB_8888)

                bitmap?.let {
                    for (j in 0 until it.height) {
                        for (i in 0 until it.width) {
                            val r = Noise.perlin(xOff, yOff, i + j.toDouble()).map(0, 1, 0, 255)
                                .map(0, 1, 0, 255)
                            it[i, j] = Color.argb(r.toFloat(), r.toFloat(), r.toFloat(), r.toFloat())
                            xOff += 0.01
                        }
                        yOff += 0.01
                    }
                }
            }
        }

    }

    override fun onRender(canvas: Canvas) {
        canvas.drawColor(Color.WHITE)

        bitmap?.let {
            val rect = Rect(0, 0, screenWidth!!, screenHeight!!)
            canvas.drawBitmap(it, rect, rect, null)
        }

        canvas.drawFPSInfo("")
    }

    override fun clear() {
        super.clear()
        bitmap = null
    }
}
