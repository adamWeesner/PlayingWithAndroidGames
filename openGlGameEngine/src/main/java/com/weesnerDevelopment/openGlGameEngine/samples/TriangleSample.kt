package com.weesnerDevelopment.openGlGameEngine.samples

import com.weesnerDevelopment.gameEngine.game.Screen
import com.weesnerDevelopment.openGlGameEngine.GlGame
import com.weesnerDevelopment.openGlGameEngine.Vertices
import javax.microedition.khronos.opengles.GL10.*

class TriangleSample : GlGame() {
    override var startScreen: Screen = FirstTriangleScreen(this)
}

private class FirstTriangleScreen(
    private val game: GlGame
) : Screen(game) {
    private lateinit var vertices: Vertices

    override fun resume() {
        game.glGraphics.apply {
            gl.glViewport(0, 0, size.width.toInt(), size.height.toInt())
            gl.glClear(GL_COLOR_BUFFER_BIT)
            gl.glMatrixMode(GL_PROJECTION)
            gl.glLoadIdentity()
            gl.glOrthof(0f, 320f, 0f, 480f, 1f, -1f)
        }
        vertices = Vertices(
            glGraphics = game.glGraphics,
            maxVertices = 3,
            maxIndices = 0,
            hasColor = false,
            hasTexCoords = false
        ).apply {
            setVertices(
                floatArrayOf(
                    0f, 0f,
                    319f, 0f,
                    160f, 479f
                )
            )
            bind()
        }

    }

    override fun present(deltaTime: Float) {
        game.glGraphics.gl.glColor4f(1f, 0f, 0f, 1f)

        vertices.draw(GL_TRIANGLES, 0, 3)
    }

    override fun update(deltaTime: Float) {
        game.input.getTouchEvents()
        game.input.getKeyEvents()
    }

    override fun pause() {
        vertices.unbind()
    }
}