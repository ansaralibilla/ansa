package com.ansa.embroidery.render

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.ansa.embroidery.data.EmbroideryDesign
import com.ansa.embroidery.data.StitchType
import kotlin.math.max

class SimpleEmbroideryRenderer : EmbroideryRenderer {
    override fun render(design: EmbroideryDesign, mode: RenderMode, scale: Float): Bitmap {
        val width = max((design.metadata.widthMm * scale).toInt(), 1)
        val height = max((design.metadata.heightMm * scale).toInt(), 1)
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.BLACK)

        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            strokeWidth = if (mode == RenderMode.TRUE_VIEW) 3f else 1.5f
            color = if (mode == RenderMode.TRUE_VIEW) Color.CYAN else Color.YELLOW
            style = Paint.Style.STROKE
        }

        val stitches = design.stitches
        if (stitches.size < 2) {
            return bitmap
        }

        for (index in 1 until stitches.size) {
            val prev = stitches[index - 1]
            val current = stitches[index]
            if (current.type == StitchType.JUMP || current.type == StitchType.TRIM) {
                continue
            }
            canvas.drawLine(
                prev.x * scale,
                prev.y * scale,
                current.x * scale,
                current.y * scale,
                paint,
            )
        }

        return bitmap
    }
}
