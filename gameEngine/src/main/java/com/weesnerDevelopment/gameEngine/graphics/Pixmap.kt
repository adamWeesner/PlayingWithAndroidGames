package com.weesnerDevelopment.gameEngine.graphics

import com.weesnerDevelopment.gameEngine.math.Size

interface Pixmap {
    val size: Size
    val format: Graphics.PixmapFormat

    fun dispose()
}
