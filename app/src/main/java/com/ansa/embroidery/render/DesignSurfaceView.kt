package com.ansa.embroidery.render

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.ansa.embroidery.data.EmbroideryDesign
import kotlin.math.max
import kotlin.math.min

class DesignSurfaceView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : View(context, attrs) {
    private val renderer = SimpleEmbroideryRenderer()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var design: EmbroideryDesign? = null
    private var mode: RenderMode = RenderMode.TRUE_VIEW

    fun showDesign(design: EmbroideryDesign?, mode: RenderMode) {
        this.design = design
        this.mode = mode
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.DKGRAY)
        val design = design ?: return
        val scaleX = width / design.metadata.widthMm
        val scaleY = height / design.metadata.heightMm
        val scale = min(scaleX, scaleY).coerceAtLeast(1f)
        val bitmap = renderer.render(design, mode, scale)
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
    }
}
