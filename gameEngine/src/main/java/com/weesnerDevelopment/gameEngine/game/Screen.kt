package com.weesnerDevelopment.gameEngine.game

import com.weesnerDevelopment.gameEngine.input.Input
import com.weesnerDevelopment.gameEngine.math.*

abstract class Screen(game: Game) {
    open fun update(deltaTime: Float) {}
    open fun present(deltaTime: Float) {}

    open fun pause() {}
    open fun resume() {}

    open fun dispose() {}

    fun inBounds(
        event: Input.TouchEvent,
        position: Vector2D,
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
