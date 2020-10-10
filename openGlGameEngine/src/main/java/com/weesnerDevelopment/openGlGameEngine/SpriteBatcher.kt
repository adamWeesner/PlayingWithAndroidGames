package com.weesnerDevelopment.openGlGameEngine

import com.weesnerDevelopment.gameEngine.math.*
import com.weesnerDevelopment.gameEngine.math.Size
import javax.microedition.khronos.opengles.GL10.GL_TRIANGLES

class SpriteBatcher(glGraphics: GlGraphics, maxSprites: Int) {
    private val verticesBuffer: FloatArray = FloatArray(maxSprites * 4 * 4)
    private val Size.halfSize get() = Size(width / 2f, height / 2f)

    val vertices: Vertices = Vertices(
        glGraphics = glGraphics,
        maxVertices = maxSprites * 4,
        maxIndices = maxSprites * 6,
        hasColor = false,
        hasTexCoords = true
    )

    var numSprites: Int = 0
    var bufferIndex: Int = 0

    private fun beginBatch(texture: Texture) {
        texture.bind()
        numSprites = 0
        bufferIndex = 0
    }

    private fun endBatch() {
        vertices.setVertices(verticesBuffer, 0, bufferIndex)
        vertices.bind()
        vertices.draw(GL_TRIANGLES, 0, numSprites * 6)
        vertices.unbind()
    }

    fun batch(texture: Texture, batchJob: SpriteBatcher.() -> Unit) {
        beginBatch(texture)
        batchJob()
        endBatch()
    }

    fun draw(position: Vector2D, size: Size, region: TextureRegion) {
        val halfSize = size.halfSize
        val pos1 = Vector2D(position.x - halfSize.width, position.y - halfSize.height)
        val pos2 = Vector2D(position.x + halfSize.width, position.y + halfSize.height)

        verticesBuffer[bufferIndex++] = pos1.x.toFloat()
        verticesBuffer[bufferIndex++] = pos1.y.toFloat()
        verticesBuffer[bufferIndex++] = region.textureStart.u.toFloat()
        verticesBuffer[bufferIndex++] = region.textureEnd.v.toFloat()
        verticesBuffer[bufferIndex++] = pos2.x.toFloat()
        verticesBuffer[bufferIndex++] = pos1.y.toFloat()
        verticesBuffer[bufferIndex++] = region.textureEnd.u.toFloat()
        verticesBuffer[bufferIndex++] = region.textureEnd.v.toFloat()
        verticesBuffer[bufferIndex++] = pos2.x.toFloat()
        verticesBuffer[bufferIndex++] = pos2.y.toFloat()
        verticesBuffer[bufferIndex++] = region.textureEnd.u.toFloat()
        verticesBuffer[bufferIndex++] = region.textureStart.v.toFloat()
        verticesBuffer[bufferIndex++] = pos1.x.toFloat()
        verticesBuffer[bufferIndex++] = pos2.y.toFloat()
        verticesBuffer[bufferIndex++] = region.textureStart.u.toFloat()
        verticesBuffer[bufferIndex++] = region.textureStart.v.toFloat()

        numSprites++
    }

    fun draw(position: Vector2D, size: Size, angle: Number, region: TextureRegion) {
        val halfSize = size.halfSize
        val rad = angle.toRadians
        val cos = cos(rad)
        val sin = sin(rad)

        val pos1 = Vector2D(
            -halfSize.width * cos - -halfSize.height * sin,
            -halfSize.width * sin + -halfSize.height * cos
        ) + position
        val pos2 = Vector2D(
            halfSize.width * cos - -halfSize.height * sin,
            halfSize.width * sin + -halfSize.height * cos
        ) + position
        val pos3 = Vector2D(
            halfSize.width * cos - halfSize.height * sin,
            halfSize.width * sin + halfSize.height * cos
        ) + position
        val pos4 = Vector2D(
            -halfSize.width * cos - halfSize.height * sin,
            -halfSize.width * sin + halfSize.height * cos
        ) + position

        verticesBuffer[bufferIndex++] = pos1.x.toFloat()
        verticesBuffer[bufferIndex++] = pos1.y.toFloat()
        verticesBuffer[bufferIndex++] = region.textureStart.u.toFloat()
        verticesBuffer[bufferIndex++] = region.textureEnd.v.toFloat()
        verticesBuffer[bufferIndex++] = pos2.x.toFloat()
        verticesBuffer[bufferIndex++] = pos2.y.toFloat()
        verticesBuffer[bufferIndex++] = region.textureEnd.u.toFloat()
        verticesBuffer[bufferIndex++] = region.textureEnd.v.toFloat()
        verticesBuffer[bufferIndex++] = pos3.x.toFloat()
        verticesBuffer[bufferIndex++] = pos3.y.toFloat()
        verticesBuffer[bufferIndex++] = region.textureEnd.u.toFloat()
        verticesBuffer[bufferIndex++] = region.textureStart.v.toFloat()
        verticesBuffer[bufferIndex++] = pos4.x.toFloat()
        verticesBuffer[bufferIndex++] = pos4.y.toFloat()
        verticesBuffer[bufferIndex++] = region.textureStart.u.toFloat()
        verticesBuffer[bufferIndex++] = region.textureStart.v.toFloat()

        numSprites++
    }

    init {
        bufferIndex = 0
        numSprites = 0
        val indices = ShortArray(maxSprites * 6)
        var j = 0
        var i = 0
        while (i < indices.size) {
            indices[i + 0] = (j + 0).toShort()
            indices[i + 1] = (j + 1).toShort()
            indices[i + 2] = (j + 2).toShort()
            indices[i + 3] = (j + 2).toShort()
            indices[i + 4] = (j + 3).toShort()
            indices[i + 5] = (j + 0).toShort()
            i += 6
            j += 4
        }
        vertices.setIndices(indices)
    }
}
