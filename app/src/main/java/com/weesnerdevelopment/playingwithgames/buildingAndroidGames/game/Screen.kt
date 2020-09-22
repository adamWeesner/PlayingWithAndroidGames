package com.weesnerdevelopment.playingwithgames.buildingAndroidGames.game

import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.input.Input
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.util.Size
import com.weesnerdevelopment.playingwithgames.math.compareTo
import com.weesnerdevelopment.playingwithgames.math.minus
import com.weesnerdevelopment.playingwithgames.math.plus
import com.weesnerdevelopment.playingwithgames.objects.Vector

abstract class Screen(game: Game) {
    abstract fun update(deltaTime: Float)
    abstract fun present(deltaTime: Float)

    abstract fun pause()
    abstract fun resume()

    abstract fun dispose()

    protected fun inBounds(
        event: Input.TouchEvent,
        position: Vector,
        size: Size,
        truth: () -> Unit
    ) {
        val pos = event.position
        if (pos.x > position.x && pos.x < position.x + size.width - 1 &&
            pos.y > position.y && pos.y < position.y + size.height - 1
        )
            truth()
    }
}
