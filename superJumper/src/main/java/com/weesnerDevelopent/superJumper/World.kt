package com.weesnerDevelopent.superJumper

import com.weesnerDevelopent.superJumper.objects.*
import com.weesnerDevelopment.gameEngine.math.*
import kotlin.random.Random

interface WorldListener {
    fun jump()
    fun highJump()
    fun hit()
    fun coin()
}

enum class WorldState {
    Running,
    NextLevel,
    GameOver
}


class World(
    private val listener: WorldListener
) {
    companion object {
        val size = Size(10, 15 * 20)
        val gravity = Vector2D(0, -12)
    }

    lateinit var castle: Castle

    val platforms = arrayListOf<Platform>()
    val springs = arrayListOf<Spring>()
    val squirrels = arrayListOf<Squirrel>()
    val coins = arrayListOf<Coin>()

    val bob = Bob(Vector2D(5, 1))
    val random = Random(System.currentTimeMillis())

    var heightSoFar: Number = 0
    var score: Number = 0
        get() {
            return coinScore + heightScore
        }
    var coinScore: Number = 0
    var heightScore: Number = 0
    var state = WorldState.Running


    init {
        generateLevel()
    }

    fun generateLevel() {
        val maxJumpHeight = bob.jumpVelocity.pow(2) / (2 * -gravity.y)

        var y = Platform.size.height / 2

        while (y < size.height - size.width / 2) {
            val type = if (random.nextFloat() > .8) PlatformType.Moving else PlatformType.Static
            val x =
                random.nextFloat() * (size.width - Platform.size.width) + Platform.size.width / 2

            val platform = Platform(Vector2D(x, y), type)
            platforms.add(platform)

            // creates spring on platform
            if (random.nextFloat() > .9 && type != PlatformType.Moving) {
                val spring = Spring(
                    Vector2D(
                        platform.position.x,
                        platform.position.y + platform.size.height / 2
                    )
                )
                springs.add(spring)
            }

            // creates squirrel
            if (y > size.height / 3 && random.nextFloat() > .8) {
                val squirrel = Squirrel(
                    Vector2D(
                        platform.position.x + random.nextFloat(),
                        platform.position.y + Squirrel.size.height + random.nextFloat() * 2
                    )
                )
                squirrels.add(squirrel)
            }

            // creates coin
            if (random.nextFloat() > .6) {
                val coin = Coin(
                    Vector2D(
                        platform.position.x + random.nextFloat(),
                        platform.position.y + Coin.size.height + random.nextFloat() * 3
                    )
                )
                coins.add(coin)
            }

            y += (maxJumpHeight - .5) - random.nextFloat() * (maxJumpHeight / 3)
        }

        castle = Castle(Vector2D(size.width / 2, y))
    }

    fun update(deltaTime: Number, accelerationX: Number) {
        updateBob(deltaTime, accelerationX)
        updatePlatforms(deltaTime)
        updateSquirrels(deltaTime)
        updateCoins(deltaTime)

        if (bob.state != BobState.Hit) checkCollisions()
        checkGameOver()
    }

    fun checkCollisions() {
        checkPlatformCollisions()
        checkSquirrelCollisions()
        checkItemCollisions()
        checkCastleCollision()
    }

    private fun updateBob(deltaTime: Number, accelerationX: Number) {
        if (bob.state != BobState.Hit && bob.position.y <= 0.5)
            bob.hitPlatform()

        if (bob.state != BobState.Hit)
            bob.velocity.x -= accelerationX / 10 * bob.moveVelocity

        bob.update(deltaTime)

        heightSoFar = max(bob.position.y, heightSoFar)
        heightScore = heightSoFar.toInt() / 15
    }

    private fun updatePlatforms(deltaTime: Number) {
        for (i in 0 until platforms.lastIndex) {
            val platform = platforms[i]
            platform.update(deltaTime)
            if (platform.state == PlatformState.Pulverizing && platform.startTime > platform.pulverizeTime) {
                platforms.remove(platform)
            }
        }
    }

    private fun updateSquirrels(deltaTime: Number) {
        for (squirrel in squirrels) squirrel.update(deltaTime)
    }

    private fun updateCoins(deltaTime: Number) {
        for (coin in coins) coin.update(deltaTime)
    }

    private fun checkPlatformCollisions() {
        if (bob.velocity.y > 0) return

        for (platform in platforms) {
            if (bob.position.y > platform.position.y) {
                if (bob.bounds.isOverlapping(platform.bounds)) {
                    bob.hitPlatform()
                    listener.jump()
                    if (random.nextFloat() > .5) platform.pulverize()

                    break
                }
            }
        }
    }

    private fun checkSquirrelCollisions() {
        for (squirrel in squirrels) {
            if (bob.bounds.isOverlapping(squirrel.bounds)) {
                bob.hitSquirrel()
                listener.hit()
            }
        }
    }

    private fun checkItemCollisions() {
        for (i in 0 until coins.lastIndex) {
            val coin = coins[i]
            if (bob.bounds.isOverlapping(coin.bounds)) {
                coins.remove(coin)
                listener.coin()
                coinScore += Coin.score
            }
        }

        if (bob.velocity.y > 0) return

        for (spring in springs) {
            if (bob.position.y > spring.position.y) {
                if (bob.bounds.isOverlapping(spring.bounds)) {
                    bob.hitSpring()
                    listener.highJump()
                }
            }
        }
    }

    private fun checkCastleCollision() {
        if (bob.bounds.isOverlapping(castle.bounds)) state = WorldState.NextLevel
    }

    private fun checkGameOver() {
        if (heightSoFar - 7.5 > bob.position.y) state = WorldState.GameOver
    }
}