package com.weesnerdevelopment.playingwithgames.buildingAndroidGames.graphics

import android.graphics.Bitmap
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.util.Size

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
