package com.weesnerDevelopment.gameEngine.game

import com.weesnerDevelopment.gameEngine.audio.Audio
import com.weesnerDevelopment.gameEngine.graphics.Graphics
import com.weesnerDevelopment.gameEngine.input.Input
import com.weesnerDevelopment.gameEngine.file.FileIO
import java.util.*

interface Game {
    val startScreen: Screen

    var graphics: Graphics
    var audio: Audio
    var input: Input
    var fileIO: FileIO
    var screen: Screen

    val backStack: Stack<Screen>

    fun back()
}
