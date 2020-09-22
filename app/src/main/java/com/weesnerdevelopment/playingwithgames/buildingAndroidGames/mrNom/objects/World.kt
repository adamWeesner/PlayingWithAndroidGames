package com.weesnerdevelopment.playingwithgames.buildingAndroidGames.mrNom.objects

import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.mrNom.Settings
import com.weesnerdevelopment.playingwithgames.math.compareTo
import com.weesnerdevelopment.playingwithgames.math.minus
import com.weesnerdevelopment.playingwithgames.math.plus
import com.weesnerdevelopment.playingwithgames.math.times
import com.weesnerdevelopment.playingwithgames.objects.Vector
import kotlin.random.Random

class World {
    private val size = Settings.gridSize
    private val scoreIncrement = 10
    private val tickInterval: Number = .5
    private val tickDecrement: Number = .5
    private val random = Random(System.currentTimeMillis())
    private val fields = Array(size.width.toInt()) {
        Array(size.height.toInt()) {
            false
        }
    }
    private var tickTime: Number = 0
    private var tick = tickInterval

    lateinit var stain: Stain

    var gameOver = false
    var score = 0
    val snake = Snake()

    init {
        placeStain()
    }

    private fun placeStain() {
        for (x in 0 until size.width.toInt()) {
            for (y in 0 until size.height.toInt()) {
                fields[x][y] = false
            }
        }

        for (i in snake.parts.indices) {
            val part = snake.parts[i].position
            fields[part.x.toInt()][part.y.toInt()] = true
        }

        val stainPos = Vector(
            random.nextInt(size.width.toInt()),
            random.nextInt(size.height.toInt())
        )

        while (true) {
            if (!fields[stainPos.x.toInt()][stainPos.y.toInt()]) break

            stainPos.x += 1
            if (stainPos.x >= size.width) {
                stainPos.x = 0
                stainPos.y += 1
                if (stainPos.y >= size.height)
                    stainPos.y = 0
            }
        }

        stain = Stain(stainPos, StainType.get(random.nextInt(StainType.values().size)))
    }

    fun update(deltaTime: Number) {
        if (gameOver) return

        tickTime += deltaTime

        while (tickTime > tick) {
            tickTime -= tick
            snake.advance()

            if (snake.checkBitten()) {
                gameOver = true
                return
            }

            if (snake.head == stain.position) {
                score += scoreIncrement
                snake.eat()
                if (snake.parts.size == size.width * size.height) {
                    gameOver = true
                    return
                } else {
                    placeStain()
                }

                if (score % 100 == 0 && tick - tickDecrement > 0)
                    tick -= tickDecrement
            }
        }
    }
}