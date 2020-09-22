package com.weesnerdevelopment.playingwithgames.buildingAndroidGames.graphics

import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.util.Size

interface Pixmap {
    val size: Size
    val format: Graphics.PixmapFormat

    fun dispose()
}
