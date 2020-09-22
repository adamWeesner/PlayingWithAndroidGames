package com.weesnerdevelopment.playingwithgames.buildingAndroidGames.mrNom

import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.game.AndroidGame
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.game.Screen
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.mrNom.screens.LoadingScreen


class MrNomGame : AndroidGame() {
    override val startScreen: Screen = LoadingScreen(this)
}