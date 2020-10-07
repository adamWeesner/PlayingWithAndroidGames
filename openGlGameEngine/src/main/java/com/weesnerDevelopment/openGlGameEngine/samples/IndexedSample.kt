package com.weesnerDevelopment.openGlGameEngine.samples

import com.weesnerDevelopment.gameEngine.game.Screen
import com.weesnerDevelopment.openGlGameEngine.GlGame
import com.weesnerDevelopment.openGlGameEngine.Texture
import com.weesnerDevelopment.openGlGameEngine.Vertices
import javax.microedition.khronos.opengles.GL10.*


class IndexedSample : GlGame() {
    override var startScreen: Screen = IndexedScreen(this)
}

private class IndexedScreen(
    private val game: GlGame
) : Screen(game) {
    lateinit var vertices: Vertices
    lateinit var texture: Texture

    override fun resume() {
        game.glGraphics.apply {
            gl.glViewport(0, 0, size.width.toInt(), size.height.toInt())
            gl.glClear(GL_COLOR_BUFFER_BIT)
            gl.glMatrixMode(GL_PROJECTION)
            gl.glLoadIdentity()
            gl.glOrthof(0f, 320f, 0f, 480f, 1f, -1f)

            gl.glEnable(GL_TEXTURE_2D)
        }
        texture = Texture(game, "bobrgb888.png").apply {
            bind()
        }
        vertices = Vertices(
            glGraphics = game.glGraphics,
            maxVertices = 4,
            maxIndices = 6,
            hasColor = false,
            hasTexCoords = true
        ).apply {
            setVertices(
                floatArrayOf(
                    100f, 100f, 0f, 1f,
                    228f, 100f, 1f, 1f,
                    228f, 228f, 1f, 0f,
                    100f, 228f, 0f, 0f
                )
            )
            setIndices(
                shortArrayOf(
                    0, 1, 2,
                    2, 3, 0
                )
            )
            bind()
        }
    }

    override fun present(deltaTime: Float) {
        vertices.draw(GL_TRIANGLES, 0, 6)
    }

    override fun pause() {
        texture.dispose()
        vertices.unbind()
    }
}