package com.weesnerDevelopment.androidGameEngine.input

import android.content.Context
import android.view.View
import com.weesnerDevelopment.gameEngine.math.Size
import com.weesnerDevelopment.androidGameEngine.input.touch.MultiTouchHandler
import com.weesnerDevelopment.gameEngine.input.Input
import com.weesnerDevelopment.gameEngine.math.Vector2D
import com.weesnerDevelopment.gameEngine.math.Vector3D

class AndroidInput(
    context: Context,
    view: View,
    scale: Size
) : Input {
    private val accelHandler = AccelerometerHandler(context)
    private val keyHandler = KeyboardHandler(view)
    private val touchHandler = MultiTouchHandler(view, scale)

    override val accel: Vector3D
        get() = accelHandler.accel

    override fun isKeyPressed(keyCode: Int): Boolean = keyHandler.isKeyPressed(keyCode)
    override fun getKeyEvents(): List<Input.KeyEvent> = keyHandler.getKeyEvents()

    override fun isTouchDown(pointer: Int): Boolean = touchHandler.isTouchDown(pointer)
    override fun getTouch(pointer: Int): Vector2D = touchHandler.getTouch(pointer)
    override fun getTouchEvents(): List<Input.TouchEvent> = touchHandler.getTouchEvents()
}
