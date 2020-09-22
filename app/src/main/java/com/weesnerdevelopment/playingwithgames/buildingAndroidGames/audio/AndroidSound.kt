package com.weesnerdevelopment.playingwithgames.buildingAndroidGames.audio

import android.media.SoundPool

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
