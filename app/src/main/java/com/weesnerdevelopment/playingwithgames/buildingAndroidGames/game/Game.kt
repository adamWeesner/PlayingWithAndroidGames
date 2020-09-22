package com.weesnerdevelopment.playingwithgames.buildingAndroidGames.game

import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.audio.Audio
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.file.FileIO
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.graphics.Graphics
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.input.Input
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
