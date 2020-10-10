package com.weesnerDevelopment.openGlGameEngine

import com.weesnerDevelopment.gameEngine.math.UV
import com.weesnerDevelopment.gameEngine.math.Vector2D
import com.weesnerDevelopment.gameEngine.math.div
import com.weesnerDevelopment.gameEngine.math.plus
import com.weesnerDevelopment.gameEngine.math.Size

class TextureRegion(
    texture: Texture,
    val start: Vector2D,
    val size: Size
) {
    val textureStart = UV(
        start.x / texture.size.width,
        start.y / texture.size.height
    )

    val textureEnd = UV(
        textureStart.u + size.width / texture.size.width,
        textureStart.v + size.height / texture.size.height
    )
}
