package com.weesnerDevelopment.openGlGameEngine.samples

import com.weesnerDevelopment.gameEngine.game.Screen
import com.weesnerDevelopment.openGlGameEngine.GlGame
import com.weesnerDevelopment.openGlGameEngine.Vertices
import javax.microedition.khronos.opengles.GL10
import javax.microedition.khronos.opengles.GL10.GL_TRIANGLES


class ColoredTriangleSample : GlGame() {
    override var startScreen: Screen = ColoredTriangleScreen(this)
}

private class ColoredTriangleScreen(
    private val game: GlGame
) : Screen(game) {
    lateinit var vertices: Vertices

    override fun resume() {
        game.glGraphics.apply {
            gl.glViewport(0, 0, size.width.toInt(), size.height.toInt())
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT)
            gl.glMatrixMode(GL10.GL_PROJECTION)
            gl.glLoadIdentity()
            gl.glOrthof(0f, 320f, 0f, 480f, 1f, -1f)
        }
        vertices = Vertices(
            glGraphics = game.glGraphics,
            maxVertices = 3,
            maxIndices = 0,
            hasColor = true,
            hasTexCoords = false
        ).apply {
            setVertices(
                floatArrayOf(
                    0f, 0f, 1f, 0f, 0f, 1f,
                    319f, 0f, 0f, 1f, 0f, 1f,
                    160f, 479f, 0f, 0f, 1f, 1f
                )
            )
            bind()
        }
    }

    override fun present(deltaTime: Float) {
        vertices.draw(GL_TRIANGLES, 0, 3)
    }

    override fun pause() {
        vertices.unbind()
    }
}