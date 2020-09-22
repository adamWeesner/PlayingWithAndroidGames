package com.weesnerdevelopment.playingwithgames.buildingAndroidGames.input

import android.content.Context
import android.view.View
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.util.Size
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.input.touch.MultiTouchHandler
import com.weesnerdevelopment.playingwithgames.objects.Vector
import com.weesnerdevelopment.playingwithgames.objects.Vector3D

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
    override fun getTouch(pointer: Int): Vector = touchHandler.getTouch(pointer)
    override fun getTouchEvents(): List<Input.TouchEvent> = touchHandler.getTouchEvents()
}
