package com.weesnerDevelopment.openGlGameEngine

import android.util.Log

class FpsCounter {
    private var startTime = System.nanoTime()
    var frames = 0

    fun logFrame(){
        frames++
        if(System.nanoTime() - startTime >= 1_000_000_000){
            Log.d("FpsCounter", "FPS: $frames")
            frames = 0
            startTime = System.nanoTime()
        }
    }
}