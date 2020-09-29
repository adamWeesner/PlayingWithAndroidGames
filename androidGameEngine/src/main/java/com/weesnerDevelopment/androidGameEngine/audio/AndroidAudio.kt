package com.weesnerDevelopment.androidGameEngine.audio

import android.app.Activity
import android.content.res.AssetManager
import android.media.SoundPool
import com.weesnerDevelopment.gameEngine.audio.*
import java.io.IOException

class AndroidAudio(
    activity: Activity
) : Audio {
    private val assetManager: AssetManager = activity.assets
    private val soundPool: SoundPool = SoundPool.Builder().build()

    override fun newMusic(fileName: String): Music {
        try {
            val asset = assetManager.openFd(fileName)
            return AndroidMusic(asset)
        } catch (e: IOException) {
            throw RuntimeException("Could not load music $fileName")
        }
    }

    override fun newSound(fileName: String): Sound {
        try {
            val asset = assetManager.openFd(fileName)
            val soundId = soundPool.load(asset, 0)
            return AndroidSound(soundPool, soundId)
        } catch (e: IOException) {
            throw RuntimeException("Could not load sound $fileName")
        }
    }
}
