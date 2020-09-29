package com.weesnerDevelopment.androidGameEngine.input.touch

import android.view.MotionEvent
import android.view.View
import com.weesnerDevelopment.gameEngine.input.EventPoolBuilder
import com.weesnerDevelopment.gameEngine.input.Input
import com.weesnerDevelopment.gameEngine.util.Size
import com.weesnerDevelopment.gameEngine.math.Vector

class SingleTouchHandler(
    view: View,
    private val scale: Size
) : TouchHandler {
    private val touchEventBuilder = EventPoolBuilder(Input.TouchEvent())
    var isTouched: Boolean = false
    lateinit var touch: Vector

    init {
        view.setOnTouchListener(this)
    }

    override fun isTouchDown(pointer: Int): Boolean = synchronized(this) {
        if (pointer == 0) isTouched else false
    }

    override fun getTouch(pointer: Int): Vector = synchronized(this) {
        touch
    }

    override fun getTouchEvents(): List<Input.TouchEvent> = synchronized(this) {
        touchEventBuilder.updateEvents()
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean = synchronized(this) {
        val touchEvent = touchEventBuilder.pool.newObject()

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchEvent.type = Input.TouchEventType.Down
                isTouched = true
            }
            MotionEvent.ACTION_MOVE -> {
                touchEvent.type = Input.TouchEventType.Dragged
                isTouched = true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                touchEvent.type = Input.TouchEventType.Up
                isTouched = false
            }
        }

        touchEvent.position = Vector(
            event.x * scale.width.toFloat(),
            event.y * scale.height.toFloat()
        )
        touchEventBuilder.buffer.add(touchEvent)

        true
    }
}