package com.weesnerdevelopment.playingwithgames.game

import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow

data class GameVariable<T : Any>(val initialValue: T) {
    private val channel = BroadcastChannel<T>(Channel.CONFLATED)
    val flow = channel.asFlow()

    var value: T = initialValue
        set(value) {
            field = value
            channel.offer(value)
        }
}
