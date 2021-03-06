package com.weesnerDevelopment.gameEngine.graphics

import com.weesnerDevelopment.gameEngine.math.Size
import com.weesnerDevelopment.gameEngine.math.Vector2D

interface Graphics {
    enum class PixmapFormat {
        ARGB8888,
        RGB565,
    }

    val size: Size

    /**
     * load an image given the [fileName] in either JPEG or PNG format. We specify a desired [format]
     * for the resulting [Pixmap], which is a hint for the loading mechanism.
     */
    fun newPixmap(fileName: String, format: PixmapFormat): Pixmap

    /**
     * Take a slice out of the given [pixmap] and return a new [Pixmap].
     */
    fun slicePixmap(pixmap: Pixmap, position: Vector2D, size: Size, format: PixmapFormat): Pixmap

    /**
     * Clears the framebuffer with the given [color].
     */
    fun clear(color: Int)

    /**
     * Set the pixel ata [position] in the framebuffer to the given [color]. Coordinates outside of
     * the screen will be ignored.
     */
    fun drawPixel(position: Vector2D, color: Int)

    /**
     * Draws a line from [startPosition] to [endPosition] with the given [color]. Coordinates
     * outside of the framebuffers raster will be ignored.
     */
    fun drawLine(startPosition: Vector2D, endPosition: Vector2D, color: Int)

    /**
     * Draws a rectangle to the framebuffer at the given [position] (starting at the top left corner)
     * with the given [size] and fill [color].
     */
    fun drawRect(position: Vector2D, size: Size, color: Int)

    /**
     * Draws rectangular portions of a [pixmap] to the framebuffer at the [position] (top left corner)
     * [srcPosition] is the specified coordinating to be used from the [pixmap], given its own
     * coordinate system. The [srcSize] is the size of the portion to take from the [pixmap].
     */
    fun drawPixmap(pixmap: Pixmap, position: Vector2D, srcPosition: Vector2D, srcSize: Size)

    /**
     * Draws rectangular portions of a pixmap to the framebuffer at the position (top left corner).
     */
    fun drawPixmap(pixmap: Pixmap, position: Vector2D)
}
