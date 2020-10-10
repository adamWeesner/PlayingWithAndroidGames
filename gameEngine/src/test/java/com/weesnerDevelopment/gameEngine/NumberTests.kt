package com.weesnerDevelopment.gameEngine

import com.weesnerDevelopment.gameEngine.math.div
import com.weesnerDevelopment.gameEngine.math.minus
import com.weesnerDevelopment.gameEngine.math.plus
import com.weesnerDevelopment.gameEngine.math.times
import org.junit.Test

class NumberTests {
    val numbers =
        listOf<Number>(
            1, 2, 3, 3,
            1.0, 2.0, 3.0, 3.0,
            1f, 2f, 3f, 3f
        )

    @Test
    fun `verify less than for two numbers`() {
        numbers[0] shouldBeLessThan numbers[1]
        numbers[2] shouldNotBeLessThan numbers[0]
        numbers[2] shouldNotBeLessThan numbers[3]

        numbers[0] shouldBeLessThan numbers[1 + 8]
        numbers[2 + 8] shouldNotBeLessThan numbers[0]
        numbers[2] shouldNotBeLessThan numbers[3 + 8]

        numbers[0 + 4] shouldBeLessThan numbers[1 + 4]
        numbers[2 + 4] shouldNotBeLessThan numbers[0 + 4]
        numbers[2 + 4] shouldNotBeLessThan numbers[3 + 4]

        numbers[0 + 8] shouldBeLessThan numbers[1 + 8]
        numbers[2 + 8] shouldNotBeLessThan numbers[0 + 8]
        numbers[2 + 8] shouldNotBeLessThan numbers[3 + 8]
    }

    @Test
    fun `verify less than or equal to for two numbers`() {
        numbers[0] shouldBeLessThanOrEqualTo numbers[1]
        numbers[2] shouldNotBeLessThanOrEqualTo numbers[1]
        numbers[2] shouldBeLessThanOrEqualTo numbers[3]

        numbers[0] shouldBeLessThanOrEqualTo numbers[1 + 8]
        numbers[2 + 8] shouldNotBeLessThanOrEqualTo numbers[1]
        numbers[2] shouldBeLessThanOrEqualTo numbers[3 + 8]

        numbers[0 + 4] shouldBeLessThanOrEqualTo numbers[1 + 4]
        numbers[2 + 4] shouldNotBeLessThanOrEqualTo numbers[1 + 4]
        numbers[2 + 4] shouldBeLessThanOrEqualTo numbers[3 + 4]

        numbers[0 + 8] shouldBeLessThanOrEqualTo numbers[1 + 8]
        numbers[2 + 8] shouldNotBeLessThanOrEqualTo numbers[1 + 8]
        numbers[2 + 8] shouldBeLessThanOrEqualTo numbers[3 + 8]
    }

    @Test
    fun `verify greater than for two numbers`() {
        numbers[0] shouldNotBeGreaterThan numbers[1]
        numbers[2] shouldBeGreaterThan numbers[0]
        numbers[2] shouldNotBeGreaterThan numbers[3]

        numbers[0] shouldNotBeGreaterThan numbers[1 + 8]
        numbers[2 + 8] shouldBeGreaterThan numbers[0]
        numbers[2] shouldNotBeGreaterThan numbers[3 + 8]

        numbers[0 + 8] shouldNotBeGreaterThan numbers[1 + 8]
        numbers[2 + 8] shouldBeGreaterThan numbers[0 + 8]
        numbers[2 + 8] shouldNotBeGreaterThan numbers[3 + 8]

        numbers[0 + 4] shouldNotBeGreaterThan numbers[1 + 4]
        numbers[2 + 4] shouldBeGreaterThan numbers[0 + 4]
        numbers[2 + 4] shouldNotBeGreaterThan numbers[3 + 4]
    }

    @Test
    fun `verify greater than or equal to for two numbers`() {
        numbers[0] shouldNotBeGreaterThanOrEqualTo numbers[1]
        numbers[2] shouldBeGreaterThanOrEqualTo numbers[0]
        numbers[2] shouldBeGreaterThanOrEqualTo numbers[3]

        numbers[0] shouldNotBeGreaterThanOrEqualTo numbers[1 + 8]
        numbers[2 + 8] shouldBeGreaterThanOrEqualTo numbers[0]
        numbers[2] shouldBeGreaterThanOrEqualTo numbers[3 + 8]

        numbers[0 + 8] shouldNotBeGreaterThanOrEqualTo numbers[1 + 8]
        numbers[2 + 8] shouldBeGreaterThanOrEqualTo numbers[0 + 8]
        numbers[2 + 8] shouldBeGreaterThanOrEqualTo numbers[3 + 8]

        numbers[0 + 4] shouldNotBeGreaterThanOrEqualTo numbers[1 + 4]
        numbers[2 + 4] shouldBeGreaterThanOrEqualTo numbers[0 + 4]
        numbers[2 + 4] shouldBeGreaterThanOrEqualTo numbers[3 + 4]
    }

    @Test
    fun `verify adding numbers`() {
        numbers[0] + numbers[0 + 8] + numbers[0 + 4] shouldBe 3.0
    }

    @Test
    fun `verify subtracting numbers`() {
        numbers[0] - numbers[0 + 8] - numbers[0 + 4] shouldBe -1.0
    }

    @Test
    fun `verify dividing numbers`() {
        numbers[1] / numbers[1 + 8] / numbers[1 + 4] shouldBe .5
        numbers[1] / numbers[1 + 8] shouldBe 1.0f
        numbers[1 + 8] / numbers[1] shouldBe 1.0f
        numbers[0 + 8] / numbers[1 + 8] shouldBe .5f
        numbers[1] / numbers[1 + 4] shouldBe 1.0
        numbers[0 + 4] / numbers[1 + 4] shouldBe .5
        numbers[0] / numbers[1] shouldBe .5
    }

    @Test
    fun `verify multiply numbers`() {
        numbers[1] * numbers[1 + 8] * numbers[1 + 4] shouldBe 8.0
        numbers[1] * numbers[1 + 8] shouldBe 4.0f
        numbers[0 + 8] * numbers[1 + 8] shouldBe 2.0f
        numbers[1] * numbers[1 + 4] shouldBe 4.0
        numbers[0 + 4] * numbers[1 + 4] shouldBe 2.0

    }
}