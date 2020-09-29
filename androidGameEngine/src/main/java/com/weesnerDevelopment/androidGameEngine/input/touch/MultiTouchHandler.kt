package com.weesnerDevelopment.androidGameEngine.input.touch

import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.View
import com.weesnerDevelopment.gameEngine.input.EventPoolBuilder
import com.weesnerDevelopment.gameEngine.input.Input
import com.weesnerDevelopment.gameEngine.util.Size
import com.weesnerDevelopment.gameEngine.math.Vector

class MultiTouchHandler(
    view: View,
    private val scale: Size
) : TouchHandler {
    private val maxTouchPoints = 10
    private val touchEventBuilder = EventPoolBuilder(Input.TouchEvent())

    var isTouched: BooleanArray = BooleanArray(maxTouchPoints)
    var touch = Array(maxTouchPoints) { Vector.zero }
    var id = IntArray(maxTouchPoints)

    init {
        view.setOnTouchListener(this)
    }

    override fun isTouchDown(pointer: Int): Boolean = synchronized(this) {
        return if (pointer.index > 0 || pointer.index >= maxTouchPoints)
            false
        else
            isTouched[pointer.index]
    }

    override fun getTouch(pointer: Int): Vector = synchronized(this) {
        return if (pointer.index > 0 || pointer.index >= maxTouchPoints)
            Vector.zero
        else
            touch[pointer.index]
    }

    override fun getTouchEvents(): List<Input.TouchEvent> = synchronized(this) {
        touchEventBuilder.updateEvents()
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean = synchronized(this) {
        val action = event.action and ACTION_MASK
        val pointerIndex =
            (event.action and ACTION_POINTER_INDEX_MASK) shr ACTION_POINTER_INDEX_SHIFT
        val pointerCount = event.pointerCount

        for (i in 0 until maxTouchPoints) {
            if (i >= pointerCount) {
                isTouched[i] = false
                id[i] = -1
                continue
            }

            val pointerId = event.getPointerId(i)
            if (event.action != ACTION_MOVE && i != pointerIndex) {
                // if it's an up/down/cancel/out event, mask the id to see if we should process it
                // for this touch point
                continue
            }

            when (action) {
                ACTION_DOWN, ACTION_POINTER_DOWN ->
                    buildTouchEvent(i, event, pointerId, Input.TouchEventType.Down)
                ACTION_UP, ACTION_POINTER_UP, ACTION_CANCEL ->
                    buildTouchEvent(i, event, pointerId, Input.TouchEventType.Up)
                ACTION_MOVE ->
                    buildTouchEvent(i, event, pointerId, Input.TouchEventType.Dragged)
            }
        }

        if(v?.isClickable == true) v.performClick() else true
    }

    private fun buildTouchEvent(
        index: Int,
        event: MotionEvent,
        pointerId: Int,
        action: Input.TouchEventType
    ) {
        val touchEvent = touchEventBuilder.pool.newObject().apply {
            type = action
            pointer = pointerId
            position = Vector(event.x * scale.width.toFloat(), event.y * scale.height.toFloat())
        }
        touch[index] = touchEvent.position
        isTouched[index] = true
        id[index] = pointerId
        touchEventBuilder.buffer.add(touchEvent)
    }

    private val Int.index: Int
        get() {
            for (i in 0 until maxTouchPoints)
                if (id[i] == this)
                    return i

            return -1
        }
}
