package com.weesnerDevelopment.androidGameEngine.audio

import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import com.weesnerDevelopment.gameEngine.audio.Music
import com.weesnerDevelopment.gameEngine.audio.toVolume
import java.io.IOException

class AndroidMusic(
    asset: AssetFileDescriptor
) : Music, MediaPlayer.OnCompletionListener {
    private val mediaPlayer: MediaPlayer = MediaPlayer()
    private var isPrepared: Boolean = false

    init {
        try {
            mediaPlayer.setDataSource(asset.fileDescriptor, asset.startOffset, asset.length)
            mediaPlayer.prepare()
            isPrepared = true
            mediaPlayer.setOnCompletionListener(this)
        } catch (e: Exception) {
            throw RuntimeException("Could not load music")
        }
    }

    override val looping: Boolean
        get() = mediaPlayer.isLooping

    override val playing: Boolean
        get() = mediaPlayer.isPlaying

    override val stopped: Boolean
        get() = !isPrepared

    override fun play() {
        if (mediaPlayer.isPlaying) return

        try {
            synchronized(this) {
                if (!isPrepared) mediaPlayer.prepare()

                mediaPlayer.start()
            }
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun stop() {
        mediaPlayer.stop()
        synchronized(this) {
            isPrepared = false
        }
    }

    override fun pause() {
        if (mediaPlayer.isPlaying) mediaPlayer.pause()
    }

    override fun setLooping(looping: Boolean) {
        mediaPlayer.isLooping = looping
    }

    override fun setVolume(volume: Number) {
        mediaPlayer.setVolume(volume.toVolume, volume.toVolume)
    }

    override fun dispose() {
        if (mediaPlayer.isPlaying) mediaPlayer.stop()

        mediaPlayer.release()
    }

    override fun onCompletion(mediaPlayer: MediaPlayer?) {
        synchronized(this) {
            isPrepared = false
        }
    }
}
