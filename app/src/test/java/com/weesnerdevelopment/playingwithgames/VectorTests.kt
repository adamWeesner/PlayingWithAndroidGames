package com.weesnerdevelopment.playingwithgames

import com.weesnerdevelopment.playingwithgames.objects.Vector
import com.weesnerdevelopment.playingwithgames.objects.Vector3D
import org.junit.Assert
import org.junit.Test

class VectorTests {
    @Test
    fun `adding two vectors`() {
        val first = Vector(1, 1)
        val second = Vector(1, 1)

        Assert.assertEquals(Vector(2, 2), first + second)
    }

    @Test
    fun `adding two 3d vectors`() {
        val first = Vector3D(1, 1, 1)
        val second = Vector3D(1, 1, 1)


        Assert.assertEquals(Vector3D(2, 2, 2), first + second)
    }

    @Test
    fun `adding 2d and 3d vectors`() {
        val first = Vector(1, 1)
        val second = Vector3D(1, 1, 1)


        Assert.assertEquals(Vector(2, 2), first + second)
    }

    @Test
    fun `adding 3d and 2d vectors`() {
        val first = Vector(1, 1)
        val second = Vector3D(1, 1, 1)


        Assert.assertEquals(Vector3D(2, 2, 1), second + first)
    }
}