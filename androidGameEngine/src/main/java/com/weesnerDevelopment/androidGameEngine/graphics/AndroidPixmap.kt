package com.weesnerDevelopment.androidGameEngine.graphics

import android.graphics.Bitmap
import com.weesnerDevelopment.gameEngine.graphics.Graphics
import com.weesnerDevelopment.gameEngine.graphics.Pixmap
import com.weesnerDevelopment.gameEngine.util.Size

class AndroidPixmap(
    val bitmap: Bitmap,
    override val format: Graphics.PixmapFormat
) : Pixmap {
    override val size: Size = Size(
        bitmap.width,
        bitmap.height
    )

    override fun dispose() {
        bitmap.recycle()
    }
}
