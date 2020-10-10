package com.weesnerDevelopment.gameEngine.input

import com.weesnerDevelopment.gameEngine.math.Vector2D
import com.weesnerDevelopment.gameEngine.math.Vector3D

interface Input {
    enum class KeyEventType(val value: Int) {
        Down(0),
        Up(1),
    }

    class KeyEvent {
        lateinit var type: KeyEventType
        var keyCode: Int = -1
        var keyChar: Char = ' '
    }

    enum class TouchEventType(val value: Int) {
        Down(0),
        Up(1),
        Dragged(2),
    }

    class TouchEvent {
        lateinit var type: TouchEventType
        lateinit var position: Vector2D
        var pointer: Int = -1
    }

    val accel: Vector3D

    fun isKeyPressed(keyCode: Int): Boolean
    fun getKeyEvents(): List<KeyEvent>

    fun isTouchDown(pointer: Int): Boolean
    fun getTouch(pointer: Int): Vector2D
    fun getTouchEvents(): List<TouchEvent>
}
