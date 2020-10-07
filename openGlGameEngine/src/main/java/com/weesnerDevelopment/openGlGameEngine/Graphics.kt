package com.weesnerDevelopment.openGlGameEngine

import android.opengl.GLSurfaceView
import android.view.View
import com.weesnerDevelopment.gameEngine.util.Size
import javax.microedition.khronos.opengles.GL10

class GlGraphics(
    glView: GLSurfaceView
) {
    private val parent = glView.parent as View
    lateinit var gl: GL10
    var size: Size = Size(parent.width, parent.height)
}