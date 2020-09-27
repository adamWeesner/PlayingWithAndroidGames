package com.weesnerDevelopment.gameEngine.game

import com.weesnerDevelopment.gameEngine.input.Input
import com.weesnerDevelopment.gameEngine.math.Vector
import com.weesnerDevelopment.gameEngine.math.compareTo
import com.weesnerDevelopment.gameEngine.math.minus
import com.weesnerDevelopment.gameEngine.math.plus
import com.weesnerDevelopment.gameEngine.util.Size

abstract class Screen(game: Game) {
    abstract fun update(deltaTime: Float)
    abstract fun present(deltaTime: Float)

    abstract fun pause()
    abstract fun resume()

    abstract fun dispose()

    fun inBounds(
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
