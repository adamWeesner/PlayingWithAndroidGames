package com.weesnerdevelopment.playingwithgames.buildingAndroidGames.input.touch

import android.view.View
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.input.Input
import com.weesnerdevelopment.playingwithgames.objects.Vector

interface TouchHandler : View.OnTouchListener {
    fun isTouchDown(pointer: Int): Boolean
    fun getTouch(pointer: Int): Vector
    fun getTouchEvents(): List<Input.TouchEvent>
}
