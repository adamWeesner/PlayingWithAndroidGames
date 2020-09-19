package com.weesnerdevelopment.playingwithgames.game

object GameVariables {
    val pathColorCount = GameVariable(2)
    val looper = GameVariable(GameLoopType.CONSTANT_GAME_SPEED_VARIABLE_FPS)
    val itemsPerClick = GameVariable(2)
    val fps = GameVariable(60)
}

object StateVariables {
    val resetGame = GameVariable(false)
    val currentFragment = GameVariable(1)
}
