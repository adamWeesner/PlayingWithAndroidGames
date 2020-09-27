package com.weesnerDevelopment.gameEngine.graphics

import com.weesnerDevelopment.gameEngine.util.Size

interface Pixmap {
    val size: Size
    val format: Graphics.PixmapFormat

    fun dispose()
}
