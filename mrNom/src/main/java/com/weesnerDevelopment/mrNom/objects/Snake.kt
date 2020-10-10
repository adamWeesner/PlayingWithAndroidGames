package com.weesnerDevelopment.mrNom.objects

import com.weesnerDevelopment.mrNom.Settings
import com.weesnerDevelopment.gameEngine.math.Vector2D
import com.weesnerDevelopment.gameEngine.math.compareTo
import com.weesnerDevelopment.gameEngine.math.minus
import com.weesnerDevelopment.gameEngine.math.plus

data class SnakePart(
    var position: Vector2D
)

enum class SnakeDir(val value: Int) {
    Up(0),
    Left(1),
    Down(2),
    Right(3),
}

class Snake {
    val parts = arrayListOf(
        SnakePart(Vector2D(5, 6)),
        SnakePart(Vector2D(5, 7)),
        SnakePart(Vector2D(5, 8)),
    )
    val head get() = parts[0].position
    var direction: Int = SnakeDir.Up.value

    fun advance() {
        for (i in parts.lastIndex downTo 1) {
            val before = parts[i - 1]
            val part = parts[i]

            part.position = before.position.copy()
        }

        when (direction) {
            SnakeDir.Up.value -> head.y -= 1
            SnakeDir.Left.value -> head.x -= 1
            SnakeDir.Down.value -> head.y += 1
            SnakeDir.Right.value -> head.x += 1
        }

        if (head.x < 0) head.x = Settings.gridSize.width - 1
        if (head.x > Settings.gridSize.width - 1) head.x = 0

        if (head.y < 0) head.y = Settings.gridSize.height - 1
        if (head.y > Settings.gridSize.height - 1) head.y = 0
    }

    fun eat() {
        val end = parts[parts.lastIndex].position
        parts.add(SnakePart(end))
    }

    fun checkBitten(): Boolean {
        for (i in 1 until parts.size) {
            if (parts[i].position == head.copy()) return true
        }

        return false
    }

    fun turnLeft() {
        direction += 1
        if (direction > SnakeDir.Right.value)
            direction = SnakeDir.Up.value
    }

    fun turnRight() {
        direction -= 1
        if (direction < SnakeDir.Up.value)
            direction = SnakeDir.Right.value
    }
}