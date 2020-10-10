package com.weesnerDevelopment.openGlGameEngine

import com.weesnerDevelopment.gameEngine.math.Size
import com.weesnerDevelopment.gameEngine.math.Vector2D
import com.weesnerDevelopment.gameEngine.math.plus
import com.weesnerDevelopment.gameEngine.math.times

class Font(
    texture: Texture,
    position: Vector2D,
    val glyphSize: Size,
    glyphsPerRow: Number,
) {
    private val currentPos = position.copy()

    val glyphs = Array(96) {
        val current = TextureRegion(texture, currentPos.copy(   ), glyphSize)
        currentPos.x += glyphSize.width
        if (currentPos.x == position.x + glyphsPerRow * glyphSize.width) {
            currentPos.x = position.x
            currentPos.y += glyphSize.height
        }
        current
    }

    fun draw(batcher: SpriteBatcher, text: String, position: Vector2D) {
        for (char in text) {
            val c = char - ' '

            if(c < 0 || c > glyphs.lastIndex) continue

            val glyph = glyphs[c]

            batcher.draw(position, glyphSize, glyph)
            position.x += glyphSize.width
        }
    }
}