package com.weesnerDevelopment.openGlGameEngine

import com.weesnerDevelopment.gameEngine.math.div
import kotlin.math.min

class Animation(
    private val frameDuration: Number,
    private vararg val keyFrames: TextureRegion
) {
    enum class State {
        Looping,
        NonLooping
    }

    fun getKeyFrame(startTime: Number, mode: State): TextureRegion {
        var frameNumber = (startTime / frameDuration).toInt()

        when(mode){
            State.NonLooping ->
                frameNumber = min(keyFrames.lastIndex, frameNumber)
            else ->
                frameNumber %= keyFrames.size
        }

        return keyFrames[frameNumber]
    }
}