package com.weesnerdevelopment.playingwithgames.game

enum class GameLoopType(val value: String) {
    AS_FAST_AS_POSSIBLE("Unlimited"),
    CONSTANT_GAME_SPEED("Game Speed"),
    VARIABLE_FPS("Vary FPS"),
    MAX_FPS("Max FPS"),
    CONSTANT_GAME_SPEED_VARIABLE_FPS("Game FPS")
}
