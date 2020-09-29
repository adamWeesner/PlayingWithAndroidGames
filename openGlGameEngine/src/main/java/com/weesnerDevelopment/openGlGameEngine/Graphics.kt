package com.weesnerDevelopment.openGlGameEngine

import android.opengl.GLSurfaceView
import com.weesnerDevelopment.gameEngine.util.Size
import javax.microedition.khronos.opengles.GL10

class GlGraphics(
    val glView: GLSurfaceView
) {
    lateinit var gl: GL10
    lateinit var size: Size
}