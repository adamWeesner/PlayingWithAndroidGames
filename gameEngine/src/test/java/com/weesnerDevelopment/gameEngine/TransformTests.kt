package com.weesnerDevelopment.gameEngine

import com.weesnerDevelopment.gameEngine.math.Size
import com.weesnerDevelopment.gameEngine.math.Vector2D
import com.weesnerDevelopment.gameEngine.objects.Circle
import com.weesnerDevelopment.gameEngine.objects.Rectangle
import com.weesnerDevelopment.gameEngine.objects.Transform.Companion.inCircle
import com.weesnerDevelopment.gameEngine.objects.Transform.Companion.inRectangle
import com.weesnerDevelopment.gameEngine.objects.Transform.Companion.isIn
import org.junit.Test

class TransformTests {
    @Test
    fun `circle should be in circle`() {
        val circle1 = Circle(Vector2D(.5, .5), 10)
        val circle2 = Circle(Vector2D(2, 2), 5)

        circle1.isOverlapping(circle2) shouldBe true
    }

    @Test
    fun `circle should not be in circle`() {
        val circle1 = Circle(Vector2D(2, 2), 2)
        val circle2 = Circle(Vector2D(5, 5), 2)

        circle1.isOverlapping(circle2) shouldBe false
    }

    @Test
    fun `point should be in circle`() {
        val vector = Vector2D(1, 1)
        val circle = Circle(Vector2D(.5, .5), 2)

        vector.inCircle(circle) shouldBe true
        vector.isIn(circle) shouldBe true
    }

    @Test
    fun `point should not be in circle`() {
        val vector = Vector2D(2, 2)
        val circle = Circle(Vector2D(.5, .5), 2)

        vector.inCircle(circle) shouldBe false
        vector.isIn(circle) shouldBe false
    }

    @Test
    fun `point should be in rectangle`() {
        val vector = Vector2D(1, 1)
        val rectangle = Rectangle(Vector2D(0, 0), Size(2, 2))

        vector.inRectangle(rectangle) shouldBe true
        vector.isIn(rectangle) shouldBe true
    }

    @Test
    fun `point should not be in rectangle`() {
        val vector = Vector2D(2.1, 2)
        val rectangle = Rectangle(Vector2D(0, 0), Size(2, 2))

        vector.inRectangle(rectangle) shouldBe false
        vector.isIn(rectangle) shouldBe false
    }

    @Test
    fun `circle should be in rectangle`() {
        val circle = Circle(Vector2D(.5, .5), 10)
        val rectangle = Rectangle(Vector2D(0, 0), Size(2, 2))

        circle.overlapRectangle(rectangle) shouldBe true
    }

    @Test
    fun `circle should not be in rectangle`() {
        val circle = Circle(Vector2D(10, 10), 2)
        val rectangle = Rectangle(Vector2D(0, 0), Size(2, 2))

        circle.overlapRectangle(rectangle) shouldBe false
    }

    @Test
    fun `rectangle should be in rectangle`() {
        val rectangle1 = Rectangle(Vector2D(0, 0), Size(2, 2))
        val rectangle2 = Rectangle(Vector2D(1, 1), Size(2, 2))

        rectangle1.isOverlapping(rectangle2) shouldBe true
    }

    @Test
    fun `rectangle should not be in rectangle`() {
        val rectangle1 = Rectangle(Vector2D(0, 0), Size(2, 2))
        val rectangle2 = Rectangle(Vector2D(3, 3), Size(2, 2))

        rectangle1.isOverlapping(rectangle2) shouldBe false
    }
}