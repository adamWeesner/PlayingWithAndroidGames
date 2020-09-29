package com.weesnerDevelopment.openGlGameEngine

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer
import javax.microedition.khronos.opengles.GL10.*

data class Vertices(
    private val glGraphics: GlGraphics,
    private val maxVertices: Int,
    private val maxIndices: Int,
    private val hasColor: Boolean,
    private val hasTexCoords: Boolean
) {
    private val vertexSize = (2 +
            (if (hasColor) 4 else 0) +
            (if (hasTexCoords) 2 else 0)
            ) * 4
    private var buffer = ByteBuffer.allocateDirect(maxVertices * vertexSize).apply {
        order(ByteOrder.nativeOrder())
    }
    private val vertices: FloatBuffer = buffer.asFloatBuffer()
    private val indices: ShortBuffer?

    init {
        if (maxIndices > 0) {
            buffer = ByteBuffer.allocateDirect(maxIndices * Short.SIZE_BYTES / 8).apply {
                order(ByteOrder.nativeOrder())
            }
            indices = buffer.asShortBuffer()
        } else {
            indices = null
        }
    }

    fun setVertices(verticesArray: FloatArray, offset: Int, length: Int) {
        vertices.apply {
            clear()
            put(verticesArray, offset, length)
            flip()
        }
    }

    fun seIndices(indicesArray: ShortArray, offset: Int, length: Int) {
        indices?.apply {
            clear()
            put(indicesArray, offset, length)
            flip()
        }
    }

    fun bind() {
        glGraphics.gl.glEnableClientState(GL_VERTEX_ARRAY)
        vertices.position(0)
        glGraphics.gl.glVertexPointer(2, GL_FLOAT, vertexSize, vertices)

        if (hasColor) {
            glGraphics.gl.glEnableClientState(GL_COLOR_ARRAY)
            vertices.position(2)
            glGraphics.gl.glColorPointer(4, GL_FLOAT, vertexSize, vertices)
        }

        if (hasTexCoords) {
            glGraphics.gl.glEnableClientState(GL_TEXTURE_COORD_ARRAY)
            vertices.position(if (hasColor) 6 else 2)
            glGraphics.gl.glColorPointer(2, GL_FLOAT, vertexSize, vertices)
        }
    }

    fun unbind() {
        if (hasTexCoords) glGraphics.gl.glDisableClientState(GL_TEXTURE_COORD_ARRAY)
        if (hasColor) glGraphics.gl.glDisableClientState(GL_COLOR_ARRAY)
    }

    fun draw(primitiveType: Int, offset: Int, numVertices: Int) {
        if (indices != null) {
            indices.position(offset)
            glGraphics.gl.glDrawElements(primitiveType, numVertices, GL_UNSIGNED_SHORT, indices)
        } else {
            glGraphics.gl.glDrawArrays(primitiveType, offset, numVertices)
        }
    }
}