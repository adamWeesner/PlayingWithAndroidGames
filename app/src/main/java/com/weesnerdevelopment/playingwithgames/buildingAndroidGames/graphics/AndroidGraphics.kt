package com.weesnerdevelopment.playingwithgames.buildingAndroidGames.graphics

import android.content.res.AssetManager
import android.graphics.*
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.util.Size
import com.weesnerdevelopment.playingwithgames.math.minus
import com.weesnerdevelopment.playingwithgames.math.plus
import com.weesnerdevelopment.playingwithgames.objects.Vector
import java.io.IOException
import java.io.InputStream

class AndroidGraphics(
    private val assets: AssetManager,
    frameBuffer: Bitmap
) : Graphics {
    private val canvas: Canvas = Canvas(frameBuffer)
    private val paint: Paint = Paint()

    private val srcRect = Rect()
    private val destRect = Rect()

    override val size: Size = Size(frameBuffer.width, frameBuffer.height)

    override fun newPixmap(fileName: String, format: Graphics.PixmapFormat): Pixmap {
        val config: Bitmap.Config = when (format) {
            Graphics.PixmapFormat.RGB565 -> Bitmap.Config.RGB_565
            else -> Bitmap.Config.ARGB_8888
        }

        BitmapFactory.Options().apply {
            inPreferredConfig = config
        }

        var input: InputStream? = null
        val bitmap: Bitmap?

        try {
            input = assets.open(fileName)
            bitmap = BitmapFactory.decodeStream(input)
            if (bitmap == null)
                throw RuntimeException("Could not load bitmap from asset $fileName")
        } catch (e: IOException) {
            throw RuntimeException("Could not load bitmap from asset $fileName")
        } finally {
            if (input != null)
                try {
                    input.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
        }

        val newFormat =
            if (bitmap?.config == Bitmap.Config.RGB_565) Graphics.PixmapFormat.RGB565
            else Graphics.PixmapFormat.ARGB8888

        return AndroidPixmap(bitmap!!, newFormat)
    }

    override fun clear(color: Int) {
        canvas.drawRGB((color and 0Xff0000) shr 16, (color and 0xff00) shr 8, (color and 0xff))
    }

    override fun drawPixel(position: Vector, color: Int) {
        paint.color = color
        canvas.drawPoint(position.x.toFloat(), position.y.toFloat(), paint)
    }

    override fun drawLine(startPosition: Vector, endPosition: Vector, color: Int) {
        paint.color = color
        canvas.drawLine(
            startPosition.x.toFloat(),
            startPosition.y.toFloat(),
            endPosition.x.toFloat(),
            endPosition.y.toFloat(),
            paint
        )
    }

    override fun drawRect(position: Vector, size: Size, color: Int) {
        paint.apply {
            this.color = color
            style = Paint.Style.FILL
        }
        canvas.drawRect(
            position.x.toFloat(),
            position.y.toFloat(),
            (position.x + size.width - 1).toFloat(),
            (position.y + size.width - 1).toFloat(),
            paint
        )
    }

    override fun drawPixmap(pixmap: Pixmap, position: Vector, srcPosition: Vector, srcSize: Size) {
        srcRect.apply {
            left = srcPosition.x.toInt()
            top = srcPosition.y.toInt()
            right = (srcPosition.x + srcSize.width - 1).toInt()
            bottom = (srcPosition.y + srcSize.height - 1).toInt()
        }
        destRect.apply {
            left = position.x.toInt()
            top = position.y.toInt()
            right = (position.x + srcSize.width - 1).toInt()
            bottom = (position.y + srcSize.height - 1).toInt()
        }

        canvas.drawBitmap(
            (pixmap as AndroidPixmap).bitmap,
            srcRect,
            destRect,
            null
        )
    }

    override fun drawPixmap(pixmap: Pixmap, position: Vector) {
        canvas.drawBitmap(
            (pixmap as AndroidPixmap).bitmap,
            position.x.toFloat(),
            position.y.toFloat(),
            null
        )
    }
}
