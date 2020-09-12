package math


fun Float.map(
    startOFromRange: Int,
    endOfFromRange: Int,
    startOfToRange: Int,
    endOfToRange: Int
) = this.map(
    startOFromRange.toFloat(),
    endOfFromRange.toFloat(),
    startOfToRange.toFloat(),
    endOfToRange.toFloat()
)

/**
 * Convert the number from the [startOFromRange]-[endOfFromRange] to [startOfToRange]-[endOfToRange].
 * @see https://rosettacode.org/wiki/Map_range#Kotlin
 */
fun Float.map(
    startOFromRange: Float,
    endOfFromRange: Float,
    startOfToRange: Float,
    endOfToRange: Float
) =
    startOfToRange + (this - startOFromRange) * (endOfToRange - startOfToRange) / (endOfFromRange - startOFromRange)