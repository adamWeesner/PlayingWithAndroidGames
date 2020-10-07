package com.weesnerDevelopment.openGlGameEngine

import com.weesnerDevelopment.gameEngine.math.UV
import com.weesnerDevelopment.gameEngine.math.Vector
import com.weesnerDevelopment.gameEngine.math.div
import com.weesnerDevelopment.gameEngine.math.plus
import com.weesnerDevelopment.gameEngine.util.Size

class TextureRegion(
    texture: Texture,
    val start: Vector,
    size: Size
) {
    val textureStart = UV(
        start.x.toFloat() / texture.size.width,
        start.y.toFloat() / texture.size.height
    )

    val textureEnd = UV(
        textureStart.u + size.width / texture.size.width.toFloat(),
        textureStart.v + size.height / texture.size.height.toFloat()
    )
}
