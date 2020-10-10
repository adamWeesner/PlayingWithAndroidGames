package com.weesnerDevelopment.gameEngine

import com.weesnerDevelopment.gameEngine.math.Vector2D
import org.junit.Test

class VectorTests {
    @Test
    fun `adding two vectors of integers`() {
        val first = Vector2D(1, 1)
        val second = Vector2D(1, 1)

        first + second shouldBe Vector2D(2, 2)
    }

    @Test
    fun `adding two vectors of decimals`() {
        val first = Vector2D(1.5, 1.5)
        val second = Vector2D(1.5, 1.5)

        first + second shouldBe Vector2D(3.0, 3.0)
    }

    @Test
    fun `adding two vectors, integer and decimal`() {
        val first = Vector2D(1.5, 1)
        val second = Vector2D(1, 1.5)

        first + second shouldBe Vector2D(2.5, 2.5)
    }

    @Test
    fun `subtracting two vectors of integers`() {
        val first = Vector2D(1, 1)
        val second = Vector2D(1, 1)

        first - second shouldBe Vector2D(0, 0)
    }

    @Test
    fun `subtracting two vectors of decimals`() {
        val first = Vector2D(2.5, 1.5)
        val second = Vector2D(1.5, 2.5)

        first - second shouldBe Vector2D(1.0, -1.0)
    }

    @Test
    fun `subtracting two vectors, integer and decimal`() {
        val first = Vector2D(1.5, 1)
        val second = Vector2D(1, 1.5)

        first - second shouldBe Vector2D(.5, -.5)
    }

    @Test
    fun `divide vectors of integers`() {
        val first = Vector2D(1, 1)

        first / 2 shouldBe Vector2D(.5, .5)
    }

    @Test
    fun `divide vectors of decimals`() {
        val first = Vector2D(2.5, 2.5)

        first / .5 shouldBe Vector2D(5.0, 5.0)
    }

    @Test
    fun `multiply vectors of integers`() {
        val first = Vector2D(1, 1)

        first * 2 shouldBe Vector2D(2, 2)
    }

    @Test
    fun `multiply vectors of decimals`() {
        val first = Vector2D(2.5, 2.5)

        first * .5 shouldBe Vector2D(1.25, 1.25)
    }
}