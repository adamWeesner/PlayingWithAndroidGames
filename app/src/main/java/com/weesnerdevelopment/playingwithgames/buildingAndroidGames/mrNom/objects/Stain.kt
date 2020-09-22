package com.weesnerdevelopment.playingwithgames.buildingAndroidGames.mrNom.objects

import com.weesnerdevelopment.playingwithgames.objects.Vector

enum class StainType(val value: Int) {
    Type1(0),
    Type2(1),
    Type3(2);

    companion object {
        fun get(value: Int): StainType = values().first { it.value == value }
    }

}

class Stain(
    val position: Vector,
    val type: StainType
)