package math

/**
 * Convert the number from the [startOFromRange]-[endOfFromRange] to [startOfToRange]-[endOfToRange].
 * @see https://rosettacode.org/wiki/Map_range#Kotlin
 */
fun Number.map(
    startOFromRange: Number,
    endOfFromRange: Number,
    startOfToRange: Number,
    endOfToRange: Number
) =
    startOfToRange + (this - startOFromRange) * (endOfToRange - startOfToRange) / (endOfFromRange - startOFromRange)

/**
 * Allows for comparisons of [Number] values.
 */
operator fun Number.compareTo(other: Number): Int {
    return when (this) {
        is Float -> {
            when (other) {
                is Float -> compareTo(other)
                is Double -> compareTo(other)
                is Int -> compareTo(other)
                else -> throw IllegalArgumentException("${other::class} is not currently supported as a comparable")
            }
        }
        is Double -> {
            when (other) {
                is Float -> compareTo(other)
                is Double -> compareTo(other)
                is Int -> compareTo(other)
                else -> throw IllegalArgumentException("${other::class} is not currently supported as a comparable")
            }
        }
        is Int -> {
            when (other) {
                is Float -> compareTo(other)
                is Double -> compareTo(other)
                is Int -> compareTo(other)
                else -> throw IllegalArgumentException("${other::class} is not currently supported as a comparable")
            }
        }
        else -> throw IllegalArgumentException("${this::class} is not currently supported as a comparable")
    }
}

/**
 * Allows for addition of [Number] values.
 */
operator fun Number.plus(other: Number): Number = when (this) {
    is Float -> {
        when (other) {
            is Float -> plus(other)
            is Double -> plus(other)
            is Int -> plus(other)
            else -> throw IllegalArgumentException("${other::class} is not currently supported for addition")
        }
    }
    is Double -> {
        when (other) {
            is Float -> plus(other)
            is Double -> plus(other)
            is Int -> plus(other)
            else -> throw IllegalArgumentException("${other::class} is not currently supported for addition")
        }
    }
    is Int -> {
        when (other) {
            is Float -> plus(other)
            is Double -> plus(other)
            is Int -> plus(other)
            else -> throw IllegalArgumentException("${other::class} is not currently supported for addition")
        }
    }
    else -> throw IllegalArgumentException("${this::class} is not currently supported for addition")
}

/**
 * Allows for subtraction of [Number] values.
 */
operator fun Number.minus(other: Number): Number = when (this) {
    is Float -> {
        when (other) {
            is Float -> minus(other)
            is Double -> minus(other)
            is Int -> minus(other)
            else -> throw IllegalArgumentException("${other::class} is not currently supported for subtraction")
        }
    }
    is Double -> {
        when (other) {
            is Float -> minus(other)
            is Double -> minus(other)
            is Int -> minus(other)
            else -> throw IllegalArgumentException("${other::class} is not currently supported for subtraction")
        }
    }
    is Int -> {
        when (other) {
            is Float -> minus(other)
            is Double -> minus(other)
            is Int -> minus(other)
            else -> throw IllegalArgumentException("${other::class} is not currently supported for subtraction")
        }
    }
    else -> throw IllegalArgumentException("${this::class} is not currently supported for subtraction")
}

/**
 * Allows for subtraction of [Number] values.
 */
operator fun Number.unaryMinus(): Number = this * -1

/**
 * Allows for division of [Number] values.
 */
operator fun Number.div(other: Number): Number = when (this) {
    is Float -> {
        when (other) {
            is Float -> div(other)
            is Double -> div(other)
            is Int -> div(other)
            else -> throw IllegalArgumentException("${other::class} is not currently supported for division")
        }
    }
    is Double -> {
        when (other) {
            is Float -> div(other)
            is Double -> div(other)
            is Int -> div(other)
            else -> throw IllegalArgumentException("${other::class} is not currently supported for division")
        }
    }
    is Int -> {
        when (other) {
            is Float -> div(other)
            is Double -> div(other)
            is Int -> div(other)
            else -> throw IllegalArgumentException("${other::class} is not currently supported for division")
        }
    }
    else -> throw IllegalArgumentException("${this::class} is not currently supported for division")
}

/**
 * Allows for multiplication of [Number] values.
 */
operator fun Number.times(other: Number): Number = when (this) {
    is Float -> {
        when (other) {
            is Float -> times(other)
            is Double -> times(other)
            is Int -> times(other)
            else -> throw IllegalArgumentException("${other::class} is not currently supported for multiplication")
        }
    }
    is Double -> {
        when (other) {
            is Float -> times(other)
            is Double -> times(other)
            is Int -> times(other)
            else -> throw IllegalArgumentException("${other::class} is not currently supported for multiplication")
        }
    }
    is Int -> {
        when (other) {
            is Float -> times(other)
            is Double -> times(other)
            is Int -> times(other)
            else -> throw IllegalArgumentException("${other::class} is not currently supported for multiplication")
        }
    }
    else -> throw IllegalArgumentException("${this::class} is not currently supported for multiplication")
}

/**
 * Allows for incrementation of [Number] values.
 */
operator fun Number.inc(): Number = when (this) {
    is Float -> inc()
    is Double -> inc()
    is Int -> inc()
    else -> throw IllegalArgumentException("${this::class} is not currently supported for incrementation")
}

/**
 * Allows for decrementing of [Number] values.
 */
operator fun Number.dec(): Number = when (this) {
    is Float -> dec()
    is Double -> dec()
    is Int -> dec()
    else -> throw IllegalArgumentException("${this::class} is not currently supported for decrementation")
}

/**
 * Boolean whether the given [Number] is between the start and end range.
 */
fun Number.between(start: Number = 0, end: Number) =
    this >= end || this <= start

/**
 * Binary and.
 */
fun Number.and(other: Number): Int = when (this) {
    is Int -> {
        when (other) {
            is Int -> and(other)
            else -> throw IllegalArgumentException("${other::class} is not currently supported for multiplication")
        }
    }
    else -> throw IllegalArgumentException("${this::class} is not currently supported for multiplication")
}

/**
 * Binary shift left.
 */
fun Number.shl(other: Number): Int = when (this) {
    is Int -> {
        when (other) {
            is Int -> shl(other)
            else -> throw IllegalArgumentException("${other::class} is not currently supported for multiplication")
        }
    }
    else -> throw IllegalArgumentException("${this::class} is not currently supported for multiplication")
}

/**
 * Binary shift right.
 */
fun Number.shr(other: Number): Int = when (this) {
    is Int -> {
        when (other) {
            is Int -> shr(other)
            else -> throw IllegalArgumentException("${other::class} is not currently supported for multiplication")
        }
    }
    else -> throw IllegalArgumentException("${this::class} is not currently supported for multiplication")
}

/**
 * Gets the absolute value of the [Number].
 */
val Number.absoluteValue: Number get() = if (this < 0) -this else this

/**
 * Get the cosine for the [value].
 */
fun cos(value: Number): Number = when (value) {
    is Float -> kotlin.math.cos(value)
    is Double -> kotlin.math.cos(value)
    else -> throw IllegalArgumentException("Cannot get cos for type ${value::class}")
}

/**
 * Floor the [value].
 */
fun floor(value: Number): Number = when (value) {
    is Float -> kotlin.math.floor(value)
    is Double -> kotlin.math.floor(value)
    else -> throw IllegalArgumentException("Cannot get cos for type ${value::class}")
}