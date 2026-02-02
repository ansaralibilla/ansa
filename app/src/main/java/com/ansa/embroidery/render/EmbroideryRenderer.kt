package com.ansa.embroidery.render

import android.graphics.Bitmap
import com.ansa.embroidery.data.EmbroideryDesign

interface EmbroideryRenderer {
    fun render(design: EmbroideryDesign, mode: RenderMode, scale: Float): Bitmap
}
