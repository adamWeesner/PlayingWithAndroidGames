package com.weesnerDevelopment.openGlGameEngine.samples

import com.weesnerDevelopment.gameEngine.game.Screen
import com.weesnerDevelopment.openGlGameEngine.GlGame
import com.weesnerDevelopment.openGlGameEngine.Texture
import com.weesnerDevelopment.openGlGameEngine.Vertices
import javax.microedition.khronos.opengles.GL10.*

class BlendingSample : GlGame() {
    override var startScreen: Screen = BlendingScreen(this)
}

private class BlendingScreen(
    private val game: GlGame
) : Screen(game) {
    lateinit var textureRgb: Texture
    lateinit var textureRgba: Texture
    lateinit var vertices: Vertices

    override fun resume() {
        textureRgb = Texture(game, "bobrgb888.png")
        textureRgba = Texture(game, "bobargb8888.png")

        game.glGraphics.apply {
            gl.glViewport(0, 0, size.width.toInt(), size.height.toInt())
            gl.glMatrixMode(GL_PROJECTION)
            gl.glLoadIdentity()
            gl.glOrthof(0f, 320f, 0f, 480f, 1f, -1f)
        }

        vertices = Vertices(
            glGraphics = game.glGraphics,
            maxVertices = 8,
            maxIndices = 12,
            hasColor = true,
            hasTexCoords = true
        ).apply {
            setVertices(
                floatArrayOf(
                    100f, 100f, 1f, 1f, 1f, 0.5f, 0f, 1f,
                    228f, 100f, 1f, 1f, 1f, 0.5f, 1f, 1f,
                    228f, 228f, 1f, 1f, 1f, 0.5f, 1f, 0f,
                    100f, 228f, 1f, 1f, 1f, 0.5f, 0f, 0f,

                    100f, 300f, 1f, 1f, 1f, 1f, 0f, 1f,
                    228f, 300f, 1f, 1f, 1f, 1f, 1f, 1f,
                    228f, 428f, 1f, 1f, 1f, 1f, 1f, 0f,
                    100f, 428f, 1f, 1f, 1f, 1f, 0f, 0f
                )
            )
            setIndices(
                shortArrayOf(
                    0, 1, 2, 2, 3, 0,
                    4, 5, 6, 6, 7, 4
                )
            )
            bind()
        }
    }

    override fun present(deltaTime: Float) {
        game.glGraphics.apply {
            gl.glClearColor(1f,0f,0f,1f)
            gl.glClear(GL_COLOR_BUFFER_BIT)
            gl.glEnable(GL_BLEND)
            gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
            gl.glEnable(GL_TEXTURE_2D)
        }
        textureRgb.bind()
        vertices.draw(GL_TRIANGLES, 0,6)
        textureRgba.bind()
        vertices.draw(GL_TRIANGLES, 6,6)
    }
}