package math

data class Point3D(
    var x: Int,
    var y: Int,
    var z: Int
) {
    fun asPointF3D() = PointF3D(x.toFloat(), y.toFloat(), z.toFloat())

    fun shift(pointF3D: PointF3D) {
        x = x.shl(1)
        pointF3D.x *= 2
        if (pointF3D.x >= 1) {
            x++
            pointF3D.x--
        }

        y = y.shl(1)
        pointF3D.y *= 2
        if (pointF3D.y >= 1) {
            y++
            pointF3D.y--
        }

        z = z.shl(1)
        pointF3D.z *= 2
        if (pointF3D.z >= 1) {
            z++
            pointF3D.z--
        }
    }
}
