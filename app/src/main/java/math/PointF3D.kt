package math

import kotlin.math.floor

data class PointF3D(
    var x: Float,
    var y: Float,
    var z: Float
) {
    fun toAbs() {
        if (x < 0) x = -x
        if (y < 0) y = -y
        if (z < 0) z = -z
    }

    fun toPoint3D() = Point3D(floor(x).toInt(), floor(y).toInt(), floor(z).toInt())

    fun minus(other: PointF3D) =
        PointF3D(x - other.x, y - other.y, z - other.z)
}
