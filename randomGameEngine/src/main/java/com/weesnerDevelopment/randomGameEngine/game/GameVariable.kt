package com.weesnerDevelopment.randomGameEngine.game

import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow

data class GameVariable<T : Any?>(val initialValue: T?) {
    private val channel = ConflatedBroadcastChannel<T>().apply {
        initialValue?.let { offer(it) }
    }
    val flow = channel.asFlow()

    var value: T? = initialValue
        set(value) {
            field = value
            value?.let { channel.offer(it) }
        }
}
