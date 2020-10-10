package com.weesnerDevelopment.mrNom.objects

import com.weesnerDevelopment.gameEngine.math.Vector2D

enum class StainType(val value: Int) {
    Type1(0),
    Type2(1),
    Type3(2);

    companion object {
        fun get(value: Int): StainType = values().first { it.value == value }
    }

}

class Stain(
    val position: Vector2D,
    val type: StainType
)