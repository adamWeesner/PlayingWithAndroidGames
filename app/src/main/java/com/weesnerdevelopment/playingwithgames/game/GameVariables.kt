package com.weesnerdevelopment.playingwithgames.game

object GameVariables {
    val resetGame = GameVariable(false)
    val pathColorCount = GameVariable(2)
    var itemsPerClick = GameVariable(2)
    var looper = GameVariable(GameLoopType.CONSTANT_GAME_SPEED_VARIABLE_FPS)
}
