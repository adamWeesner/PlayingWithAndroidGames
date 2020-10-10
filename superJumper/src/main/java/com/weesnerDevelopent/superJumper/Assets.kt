package com.weesnerDevelopent.superJumper

import com.weesnerDevelopment.gameEngine.audio.Music
import com.weesnerDevelopment.gameEngine.audio.Sound
import com.weesnerDevelopment.gameEngine.math.Size
import com.weesnerDevelopment.gameEngine.math.Vector2D
import com.weesnerDevelopment.openGlGameEngine.*

object Assets {
    lateinit var background: Texture
    lateinit var items: Texture

    lateinit var backgroundRegion: TextureRegion
    lateinit var mainMenu: TextureRegion
    lateinit var pauseMenu: TextureRegion
    lateinit var ready: TextureRegion
    lateinit var gameOver: TextureRegion
    lateinit var highScoresRegion: TextureRegion
    lateinit var logo: TextureRegion
    lateinit var soundOn: TextureRegion
    lateinit var soundOff: TextureRegion
    lateinit var arrow: TextureRegion
    lateinit var pause: TextureRegion
    lateinit var spring: TextureRegion
    lateinit var castle: TextureRegion
    lateinit var bobHit: TextureRegion
    lateinit var platform: TextureRegion

    lateinit var coinAnim: Animation
    lateinit var bobJump: Animation
    lateinit var bobFall: Animation
    lateinit var squirrelFly: Animation
    lateinit var breakingPlatform: Animation

    lateinit var font: Font

    lateinit var music: Music

    lateinit var jumpSound: Sound
    lateinit var highJumpSound: Sound
    lateinit var hitSound: Sound
    lateinit var coinSound: Sound
    lateinit var clickSound: Sound

    val frameDuration = .2
    val baseSize = Size(32, 32)
    val windowSize = Size(320, 480)

    fun load(game: GlGame) {
        background = Texture(game, "background-sj.png")
        backgroundRegion = TextureRegion(background, Vector2D(0,0), windowSize)


        items = Texture(game, "items-sj.png")
        mainMenu = TextureRegion(items, Vector2D(0, 224), Size(300, 110))
        pauseMenu = TextureRegion(items, Vector2D(224, 128), Size(192, 96))
        ready = TextureRegion(items, Vector2D(320, 224), Size(192, 32))
        gameOver = TextureRegion(items, Vector2D(352, 256), Size(160, 96))
        highScoresRegion = TextureRegion(items, Vector2D(0, 257), Size(300, 110 / 3))

        logo = TextureRegion(items, Vector2D(0, 352), Size(274, 142))

        soundOff = TextureRegion(items, Vector2D(0,0), baseSize *  2)
        soundOn = TextureRegion(items, Vector2D(64, 0), baseSize *  2)
        arrow = TextureRegion(items, Vector2D(0, 64), baseSize *  2)
        pause = TextureRegion(items, Vector2D(64, 64), baseSize *  2)

        spring = TextureRegion(items, Vector2D(128, 0), baseSize)
        castle = TextureRegion(items, Vector2D(128, 64), baseSize *  2)
        platform = TextureRegion(items, Vector2D(64, 160), Size(64, 16))

        bobHit = TextureRegion(items, Vector2D(128, 128), baseSize)

        coinAnim = Animation(
            frameDuration,
            TextureRegion(items, Vector2D(128, 32), baseSize),
            TextureRegion(items, Vector2D(160, 32), baseSize),
            TextureRegion(items, Vector2D(192, 32), baseSize),
            TextureRegion(items, Vector2D(160, 32), baseSize)
        )
        bobJump = Animation(
            frameDuration,
            TextureRegion(items, Vector2D(0, 128), baseSize),
            TextureRegion(items, Vector2D(32, 128), baseSize)
        )
        bobFall = Animation(
            frameDuration,
            TextureRegion(items, Vector2D(64, 128), baseSize),
            TextureRegion(items, Vector2D(96, 128), baseSize)
        )
        squirrelFly = Animation(
            frameDuration,
            TextureRegion(items, Vector2D(0, 160), baseSize),
            TextureRegion(items, Vector2D(32, 160), baseSize)
        )
        breakingPlatform = Animation(
            frameDuration,
            TextureRegion(items, Vector2D(64, 160), Size(64, 16)),
            TextureRegion(items, Vector2D(64, 176), Size(64, 16)),
            TextureRegion(items, Vector2D(64, 192), Size(64, 16)),
            TextureRegion(items, Vector2D(64, 208), Size(64, 16))
        )

        font = Font(items, Vector2D(224, 0), Size(16,20), 16)

        music = game.audio.newMusic("music-sj.mp3").apply {
            setLooping(true)
            setVolume(.5)
            if (Settings.soundEnabled) play()
        }

        jumpSound = game.audio.newSound("jump-sj.ogg")
        highJumpSound = game.audio.newSound("highjump-sj.ogg")
        hitSound = game.audio.newSound("hit-sj.ogg")
        coinSound = game.audio.newSound("coin-sj.ogg")
        clickSound = game.audio.newSound("click-sj.ogg")
    }

    fun reload() {
        background.reload()
        items.reload()
        if (Settings.soundEnabled) music.play()
    }

    fun playSound(sound: Sound) {
        if (Settings.soundEnabled) sound.play(1)
    }
}
