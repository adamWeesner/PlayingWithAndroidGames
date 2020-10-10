package com.weesnerDevelopment.gameEngine

import com.weesnerDevelopment.gameEngine.math.compareTo
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals

infix fun <T> T.shouldBe(other: T) =
    assertEquals(other, this)

infix fun <T> T.shouldNotBe(other: T) =
    assertNotEquals(other, this)

infix fun <T : Number> T.shouldBeLessThan(other: T) =
    assert(this < other)

infix fun <T : Number> T.shouldNotBeLessThan(other: T) =
    assert(!(this < other))

infix fun <T : Number> T.shouldBeLessThanOrEqualTo(other: T) =
    assert(this <= other)

infix fun <T : Number> T.shouldNotBeLessThanOrEqualTo(other: T) =
    assert(!(this <= other))

infix fun <T : Number> T.shouldBeGreaterThan(other: T) =
    assert(this > other)

infix fun <T : Number> T.shouldNotBeGreaterThan(other: T) =
    assert(!(this > other))

infix fun <T : Number> T.shouldBeGreaterThanOrEqualTo(other: T) =
    assert(this >= other)

infix fun <T : Number> T.shouldNotBeGreaterThanOrEqualTo(other: T) =
    assert(!(this >= other))

