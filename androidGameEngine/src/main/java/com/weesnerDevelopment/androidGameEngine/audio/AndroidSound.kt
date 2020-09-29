package com.weesnerDevelopment.androidGameEngine.audio

import android.media.SoundPool
import com.weesnerDevelopment.gameEngine.audio.Sound
import com.weesnerDevelopment.gameEngine.audio.toVolume

class AndroidSound(
    private val soundPool: SoundPool,
    private val soundId: Int
) : Sound {
    override fun play(volume: Number) {
        soundPool.play(soundId, volume.toVolume, volume.toVolume, 0, 0, 1f)
    }

    override fun dispose() {
        soundPool.unload(soundId)
    }
}
