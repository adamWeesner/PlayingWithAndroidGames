package com.weesnerDevelopment.openGlGameEngine.samples

import com.weesnerDevelopment.gameEngine.game.Screen
import com.weesnerDevelopment.gameEngine.math.Vector2D
import com.weesnerDevelopment.gameEngine.math.compareTo
import com.weesnerDevelopment.gameEngine.math.times
import com.weesnerDevelopment.gameEngine.math.Size
import com.weesnerDevelopment.openGlGameEngine.FpsCounter
import com.weesnerDevelopment.openGlGameEngine.GlGame
import com.weesnerDevelopment.openGlGameEngine.Texture
import com.weesnerDevelopment.openGlGameEngine.Vertices
import javax.microedition.khronos.opengles.GL10.*
import kotlin.random.Random


class BobsSample : GlGame() {
    override var startScreen: Screen = BobsScreen(this)
}

private class BobsScreen(
    private val game: GlGame
) : Screen(game) {
    private val numBobs = 100
    private lateinit var bobTexture: Texture
    private lateinit var bobModel: Vertices
    private val bobs = Array(numBobs) { Bob() }
    private val fpsCount = FpsCounter()

    override fun resume() {
        bobTexture = Texture(game, "bobrgb888.png")
        bobModel = Vertices(
            glGraphics = game.glGraphics,
            maxVertices = 4,
            maxIndices = 12,
            hasColor = false,
            hasTexCoords = true
        ).apply {
            setVertices(
                floatArrayOf(
                    -16f, -16f, 0f, 1f,
                    16f, -16f, 1f, 1f,
                    16f, 16f, 1f, 0f,
                    -16f, 16f, 0f, 0f
                )
            )
            setIndices(
                shortArrayOf(
                    0, 1, 2, 2, 3, 0
                ), 0, 6
            )
            bind()
        }
        game.glGraphics.apply {
            gl.glMatrixMode(GL_PROJECTION)
            gl.glLoadIdentity()
            gl.glOrthof(
                0f,
                bobs[0].maxSize.width.toFloat(),
                0f,
                bobs[0].maxSize.height.toFloat(),
                1f,
                -1f
            )
            gl.glEnable(GL_TEXTURE_2D)
        }
    }

    override fun update(deltaTime: Float) {
        game.input.getTouchEvents()
        game.input.getKeyEvents()

        for (bob in bobs)
            bob.update(deltaTime)
    }

    override fun present(deltaTime: Float) {
        fpsCount.logFrame()
        game.glGraphics.apply {
            gl.glClearColor(1f, 0f, 0f, 1f)
            gl.glClear(GL_COLOR_BUFFER_BIT)

            bobTexture.bind()

            gl.glMatrixMode(GL_MODELVIEW)
            for (bob in bobs) {
                gl.glLoadIdentity()
                gl.glTranslatef(bob.position.x.toFloat(), bob.position.y.toFloat(), 0f)
                bobModel.draw(GL_TRIANGLES, 0, 6)
            }
        }
    }
}

class Bob {
    val maxSize = Size(320, 480)
    val position: Vector2D =
        Vector2D(Random.nextInt(maxSize.width.toInt()), Random.nextInt(maxSize.height.toInt()))
    val direction: Vector2D = Vector2D(Random.nextInt(10,100), Random.nextInt(10,100))

    fun update(deltaTime: Float) {
        position + direction * deltaTime

        if (position.x < 0) {
            position.x = 0
            direction.x *= -1
        }

        if (position.x > maxSize.width) {
            position.x = maxSize.width
            direction.x *= -1
        }

        if (position.y < 0) {
            position.y = 0
            direction.y *= -1
        }

        if (position.y > maxSize.height) {
            position.y = maxSize.height
            direction.y *= -1
        }
    }
}