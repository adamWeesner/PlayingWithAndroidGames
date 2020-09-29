package com.weesnerDevelopment.androidGameEngine.input

import android.view.KeyEvent
import android.view.KeyEvent.ACTION_DOWN
import android.view.KeyEvent.ACTION_UP
import android.view.View
import com.weesnerDevelopment.gameEngine.input.EventPoolBuilder
import com.weesnerDevelopment.gameEngine.input.Input

class KeyboardHandler(
    view: View
) : View.OnKeyListener {
    private val keyEventBuilder = EventPoolBuilder(Input.KeyEvent())

    val pressedKeys = BooleanArray(128)

    init {
        view.apply {
            setOnKeyListener(this@KeyboardHandler)
            isFocusableInTouchMode = true
            requestFocus()
        }
    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean = synchronized(this) {
        val keyEvent = keyEventBuilder.pool.newObject().apply {
            this.keyCode = keyCode
            keyChar = event.unicodeChar.toChar()
        }

        if (event.action == ACTION_DOWN) {
            keyEvent.type = Input.KeyEventType.Down
            if (keyCode in 0..127)
                pressedKeys[keyCode] = true
        }

        if (event.action == ACTION_UP) {
            keyEvent.type = Input.KeyEventType.Up
            if (keyCode in 0..127)
                pressedKeys[keyCode] = false
        }

        keyEventBuilder.buffer.add(keyEvent)
        false
    }

    fun isKeyPressed(keyCode: Int): Boolean {
        if (keyCode in 0..127) return false

        return pressedKeys[keyCode]
    }

    fun getKeyEvents(): List<Input.KeyEvent> = synchronized(this) {
        keyEventBuilder.updateEvents()
    }
}
