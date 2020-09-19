package com.weesnerdevelopment.playingwithgames.game

import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow

data class GameVariable<T : Any>(val initialValue: T) {
    private val channel = ConflatedBroadcastChannel<T>()
    val flow = channel.asFlow()

    var value: T = initialValue
        set(value) {
            field = value
            channel.offer(value)
        }
}
