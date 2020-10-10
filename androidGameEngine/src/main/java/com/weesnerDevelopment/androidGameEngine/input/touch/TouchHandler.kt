package com.weesnerDevelopment.androidGameEngine.input.touch

import android.view.View
import com.weesnerDevelopment.gameEngine.input.Input
import com.weesnerDevelopment.gameEngine.math.Vector2D

interface TouchHandler : View.OnTouchListener {
    fun isTouchDown(pointer: Int): Boolean
    fun getTouch(pointer: Int): Vector2D
    fun getTouchEvents(): List<Input.TouchEvent>
}
