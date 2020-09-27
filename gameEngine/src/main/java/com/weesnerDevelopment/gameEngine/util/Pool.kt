package com.weesnerDevelopment.gameEngine.util

class Pool<T>(
    private val factory: PoolObjectFactory<T>,
    private val maxSize: Int,
    private val freeObjects: ArrayList<T> = ArrayList(maxSize)
) {
    /**
     * Gets recycled com.weesnerDevelopment.mrNom.objects as long as the pool has some stored in the list, otherwise we create a
     * new one.
     */
    fun newObject(): T =
        if (freeObjects.isEmpty()) factory.createObject()
        else freeObjects.removeAt(freeObjects.size - 1)

    /**
     * Reinsert object that we no longer use, if the list is not full.
     */
    fun free(obj: T) {
        if (freeObjects.size < maxSize)
            freeObjects.add(obj)
    }
}

interface PoolObjectFactory<T> {
    fun createObject(): T
}
