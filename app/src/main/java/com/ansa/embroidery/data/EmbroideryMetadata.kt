package com.ansa.embroidery.data

data class EmbroideryMetadata(
    val widthMm: Float,
    val heightMm: Float,
    val stitchCount: Int,
    val colorChanges: Int,
    val stopCount: Int,
)
