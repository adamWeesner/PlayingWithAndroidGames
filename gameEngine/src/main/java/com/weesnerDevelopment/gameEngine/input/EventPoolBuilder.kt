package com.weesnerDevelopment.gameEngine.input

import com.weesnerDevelopment.gameEngine.util.Pool
import com.weesnerDevelopment.gameEngine.util.PoolObjectFactory

interface PoolBuilder<T> {
    val pool: Pool<T>
    val events: ArrayList<T>
    val buffer: ArrayList<T>
}

class EventPoolBuilder<T>(
    obj: T
) : PoolBuilder<T> {
    private val factory = object : PoolObjectFactory<T> {
        override fun createObject(): T = obj
    }

    override val pool: Pool<T> = Pool(factory, 100)
    override val events: ArrayList<T> = arrayListOf()
    override val buffer: ArrayList<T> = arrayListOf()
    
    fun updateEvents(): ArrayList<T> {
        for (i in 0 until events.size) pool.free(events[i])
        events.clear()
        events.addAll(buffer)
        buffer.clear()
        return events
    }
}
