package com.weesnerDevelopment.gameEngine.objects

import com.weesnerDevelopment.gameEngine.math.Vector
import com.weesnerDevelopment.gameEngine.util.Size

data class Rectangle(
    var lowerLeft: Vector,
    val size: Size
) : Transform(lowerLeft)