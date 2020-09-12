package com.weesnerdevelopment.playingwithgames.random.bouncingBall

import android.graphics.Path
import android.graphics.PointF
import com.weesnerdevelopment.playingwithgames.random.ViewItem
import kotlin.random.Random

data class Ball(
    val startPos: PointF,
    override val speed: PointF = PointF(Random.nextInt(10).toFloat(), Random.nextInt(10).toFloat())
) : ViewItem(startPos, speed) {

    var path: Path? = null

    override fun draw(pathData: Path, interpolation: Float) {
        super.draw(pathData, interpolation)
        if (path == null) path = pathData

        pathData.addCircle(currentOffset.x, currentOffset.y, radius.toFloat(), Path.Direction.CW)
    }
}

