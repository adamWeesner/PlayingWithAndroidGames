package com.weesnerDevelopment.gameEngine.game

import com.weesnerDevelopment.gameEngine.graphics.Graphics
import com.weesnerDevelopment.gameEngine.graphics.Pixmap
import com.weesnerDevelopment.gameEngine.input.Input
import com.weesnerDevelopment.gameEngine.math.*

abstract class UiComponent(
    open val position: Vector2D,
    open val size: Size?
) {
    abstract fun draw(graphics: Graphics)

    open fun onClick(event: Input.TouchEvent, click: () -> Unit) {
        val size = this.size!!
        val pos = event.position
        if (
            pos.x > position.x && pos.x < position.x + size.width - 1 &&
            pos.y > position.y && pos.y < position.y + size.height - 1
        )
            click()
    }
}

data class Image(
    private val image: Pixmap,
    override val position: Vector2D = Vector2D(0, 0),
    override val size: Size? = null,
    var offset: Vector2D? = null
) : UiComponent(position, size ?: image.size) {
    constructor(image: Pixmap, position: Vector2D) : this(
        image,
        position,
        image.size,
        Vector2D(0, 0)
    )

    override fun draw(graphics: Graphics) {
        if (size == null && offset == null)
            graphics.drawPixmap(image, position)
        else
            graphics.drawPixmap(image, position, offset!!, size!!)
    }

    override fun onClick(event: Input.TouchEvent, click: () -> Unit) {
        super.onClick(event, click)
    }
}