package com.weesnerdevelopment.playingwithgames

import math.compareTo
import math.plus
import org.junit.Assert
import org.junit.Test

class NumberTests {
    @Test
    fun `verify less than for two numbers that are ints`() {
        val first: Number = 1
        val second: Number = 2
        val third: Number = 3
        val fourth: Number = 3

        Assert.assertEquals(first < second, true)
        Assert.assertEquals(third < first, false)
        Assert.assertEquals(third < fourth, false)
    }

    @Test
    fun `verify greater than for two numbers that are ints`() {
        val first: Number = 1
        val second: Number = 2
        val third: Number = 3
        val fourth: Number = 3

        Assert.assertEquals(first > second, false)
        Assert.assertEquals(third > first, true)
        Assert.assertEquals(third > fourth, false)
    }

    @Test
    fun `verify equals for two numbers that are ints`() {
        val first: Number = 1
        val second: Number = 1

        Assert.assertEquals(first == second, true)
        Assert.assertEquals(first != second, false)
    }

    @Test
    fun `verify less than or equal to for two numbers that are ints`() {
        val first: Number = 1
        val second: Number = 2
        val third: Number = 3
        val fourth: Number = 3

        Assert.assertEquals(first <= second, true)
        Assert.assertEquals(third <= second, false)
        Assert.assertEquals(third <= fourth, true)
    }

    @Test
    fun `verify greater than or equal to for two numbers that are ints`() {
        val first: Number = 1
        val second: Number = 2
        val third: Number = 3
        val fourth: Number = 3

        Assert.assertEquals(first >= second, false)
        Assert.assertEquals(third >= second, true)
        Assert.assertEquals(third >= fourth, true)
    }

    @Test
    fun `verify less than for two numbers that are floats`() {
        val first: Number = 1f
        val second: Number = 2f
        val third: Number = 3f
        val fourth: Number = 3f

        Assert.assertEquals(first < second, true)
        Assert.assertEquals(third < first, false)
        Assert.assertEquals(third < fourth, false)
    }

    @Test
    fun `verify greater than for two numbers that are floats`() {
        val first: Number = 1f
        val second: Number = 2f
        val third: Number = 3f
        val fourth: Number = 3f

        Assert.assertEquals(first > second, false)
        Assert.assertEquals(third > first, true)
        Assert.assertEquals(third > fourth, false)
    }

    @Test
    fun `verify equals for two numbers that are floats`() {
        val first: Number = 1f
        val second: Number = 1f

        Assert.assertEquals(first == second, true)
        Assert.assertEquals(first != second, false)
    }

    @Test
    fun `verify less than or equal to for two numbers that are floats`() {
        val first: Number = 1f
        val second: Number = 2f
        val third: Number = 3f
        val fourth: Number = 3f

        Assert.assertEquals(first <= second, true)
        Assert.assertEquals(third <= second, false)
        Assert.assertEquals(third <= fourth, true)
    }

    @Test
    fun `verify greater than or equal to for two numbers that are floats`() {
        val first: Number = 1f
        val second: Number = 2f
        val third: Number = 3f
        val fourth: Number = 3f

        Assert.assertEquals(first >= second, false)
        Assert.assertEquals(third >= second, true)
        Assert.assertEquals(third >= fourth, true)
    }

    @Test
    fun `verify less than for two numbers that are floats and ints`() {
        val first: Number = 1f
        val second: Number = 2
        val third: Number = 3f
        val fourth: Number = 3

        Assert.assertEquals(first < second, true)
        Assert.assertEquals(third < first, false)
        Assert.assertEquals(third < fourth, false)
    }

    @Test
    fun `verify greater than for two numbers that are floats and ints`() {
        val first: Number = 1f
        val second: Number = 2
        val third: Number = 3f
        val fourth: Number = 3

        Assert.assertEquals(first > second, false)
        Assert.assertEquals(third > first, true)
        Assert.assertEquals(third > fourth, false)
    }

    @Test
    fun `verify equals for two numbers that are floats and ints`() {
        val first: Number = 1f
        val second: Number = 1

        Assert.assertEquals(first == second, false)
        Assert.assertEquals(first != second, true)
    }

    @Test
    fun `verify less than or equal to for two numbers that are floats and ints`() {
        val first: Number = 1f
        val second: Number = 2
        val third: Number = 3f
        val fourth: Number = 3

        Assert.assertEquals(first <= second, true)
        Assert.assertEquals(third <= second, false)
        Assert.assertEquals(third <= fourth, true)
    }

    @Test
    fun `verify greater than or equal to for two numbers that are floats and ints`() {
        val first: Number = 1f
        val second: Number = 2
        val third: Number = 3f
        val fourth: Number = 3

        Assert.assertEquals(first >= second, false)
        Assert.assertEquals(third >= second, true)
        Assert.assertEquals(third >= fourth, true)
    }

    @Test
    fun `verify adding numbers`(){
        val first: Number = 1
        val second: Number = 2.3
        val third: Number = 3.5f

        Assert.assertEquals(first + second + third, 6.8 as Number)
    }

    @Test
    fun `verify adding ints`(){
        val first: Number = 1
        val second: Number = 2
        val third: Number = 3

        Assert.assertEquals(first + second + third, 6 as Number)
    }
}