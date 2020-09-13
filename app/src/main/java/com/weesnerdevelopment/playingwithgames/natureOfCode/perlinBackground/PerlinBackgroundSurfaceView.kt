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
import math.Noise
import math.map

class PerlinBackgroundSurfaceView(context: Context) : GameSurfaceView(context) {
    private var bitmap: Bitmap? = null
    private var xOff: Double = 0.0
    private var yOff: Double = 0.0

    override fun onResume() {
        super.onResume()
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

    override fun onClear() {
        bitmap = null
    }
}
