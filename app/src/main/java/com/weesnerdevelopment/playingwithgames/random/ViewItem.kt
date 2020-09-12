package com.weesnerdevelopment.playingwithgames.random

import android.graphics.Path
import android.graphics.PointF
import kotlin.math.absoluteValue
import kotlin.math.pow

abstract class ViewItem(
    startPos: PointF,
    open val speed: PointF,
    open val radius: Int = 40
) {

    private var negativeX = false
    private var negativeY = false

    var currentOffset: PointF = startPos
    var checkedCollision = false

    private fun updateDirection(width: Int, height: Int, isX: Boolean = true): Float {
        val updatingValue = if (isX) currentOffset.x else currentOffset.y
        val increment = if (isX) speed.x else speed.y
        val max = if (isX) width else height
        val negative = if (isX) negativeX else negativeY

        return if (negative) {
            if (updatingValue - increment >= radius) {
                updatingValue - increment
            } else {
                updateNegative(false, isX)
                updatingValue + increment
            }
        } else {
            if (updatingValue + increment <= max - radius) {
                updatingValue + increment
            } else {
                updateNegative(true, isX)
                updatingValue - increment
            }
        }
    }

    private fun updateNegative(value: Boolean, isX: Boolean) =
        if (isX) negativeX = value else negativeY = value

    fun updateOffset(width: Int, height: Int) {
        val updatedX = updateDirection(width, height)
        val updatedY = updateDirection(width, height, false)

        currentOffset = PointF(updatedX, updatedY)
    }

    fun checkCollision(others: List<ViewItem>) {
//        if (!checkedCollision) {
            others.forEach {
                it.checkedCollision = true
                val xDistance = (currentOffset.x - it.currentOffset.x).toDouble().absoluteValue
                val yDistance = (currentOffset.y - it.currentOffset.y).toDouble().absoluteValue

                val distance = Math.sqrt(xDistance.pow(2) + yDistance.pow(2))

                if (distance <= radius * 2) {
                    println("intersected...")
                    val xAdjustment = if (negativeX) speed.x else -speed.x
                    val yAdjustment = if (negativeY) speed.y else -speed.y
                    negativeX = !negativeX
                    negativeY = !negativeY
                    currentOffset = PointF(currentOffset.x + xAdjustment, currentOffset.y + yAdjustment)
                }
//            }
        }
    }

    open fun draw(pathData: Path, interpolation: Float = 0f) {
        checkedCollision = false
        currentOffset = PointF(currentOffset.x + interpolation, currentOffset.y + interpolation)
    }
}
