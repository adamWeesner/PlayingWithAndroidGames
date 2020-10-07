package com.weesnerDevelopment.openGlGameEngine

import android.graphics.BitmapFactory
import android.opengl.GLUtils
import com.weesnerDevelopment.gameEngine.file.FileIO
import com.weesnerDevelopment.gameEngine.util.Size
import java.io.IOException
import java.io.InputStream
import javax.microedition.khronos.opengles.GL10
import javax.microedition.khronos.opengles.GL10.*

class Texture(
    private val glGame: GlGame,
    private val fileName: String
) {
    private val gl: GL10 by lazy { glGame.glGraphics.gl }
    private val fileIO: FileIO by lazy { glGame.fileIO }

    private var textureId: Int = -1
    private var minFilter: Int = -1
    private var magFilter: Int = -1
    lateinit var size: Size

    init {
        load()
    }

    private fun load() {
        val textureIds = IntArray(1)

        gl.glGenTextures(1, textureIds, 0)
        textureId = textureIds[0]

        var inputStream: InputStream? = null
        try {
            inputStream = fileIO.readAsset(fileName)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            size = Size(bitmap.width, bitmap.height)

            gl.glBindTexture(GL_TEXTURE_2D, textureId)
            GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0)
            setFilters(GL_NEAREST, GL_NEAREST)
            gl.glBindTexture(GL_TEXTURE_2D, 0)
        } catch (e: IOException) {
            throw RuntimeException("Couldn't load texture $fileName", e)
        } finally {
            inputStream?.close()
        }
    }

    fun reload() {
        load()
        bind()
        setFilters(minFilter, magFilter)
        gl.glBindTexture(GL_TEXTURE_2D, 0)
    }

    fun setFilters(minFilter: Int, magFilter: Int) {
        this.minFilter = minFilter
        this.magFilter = magFilter

        gl.glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, minFilter.toFloat())
        gl.glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, minFilter.toFloat())
    }

    fun bind() {
        gl.glBindTexture(GL_TEXTURE_2D, textureId)
    }

    fun dispose() {
        gl.glBindTexture(GL_TEXTURE_2D, textureId)
        val textureIds = IntArray(1) { textureId }
        gl.glDeleteTextures(1, textureIds, 0)
    }
}