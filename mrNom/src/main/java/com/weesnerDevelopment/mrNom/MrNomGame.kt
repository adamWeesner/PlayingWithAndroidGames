package com.weesnerDevelopment.mrNom

import com.weesnerDevelopment.androidGameEngine.game.AndroidGame
import com.weesnerDevelopment.gameEngine.game.Screen
import com.weesnerDevelopment.mrNom.screens.LoadingScreen


class MrNomGame : AndroidGame() {
    override val startScreen: Screen = LoadingScreen(this)
}