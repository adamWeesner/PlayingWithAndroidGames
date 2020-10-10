package com.weesnerDevelopment.gameEngine.objects

import com.weesnerDevelopment.gameEngine.math.Vector2D
import com.weesnerDevelopment.gameEngine.math.Size

data class Rectangle(
    var lowerLeft: Vector2D,
    val size: Size
) : Transform(lowerLeft)