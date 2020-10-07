package com.weesnerDevelopment.gameEngine.util

data class Size(
    val width: Number,
    val height: Number
){
    constructor(both: Number) : this(both, both)
}
