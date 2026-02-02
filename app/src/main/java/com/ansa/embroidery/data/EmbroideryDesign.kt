package com.ansa.embroidery.data

data class EmbroideryDesign(
    val name: String,
    val format: EmbroideryFormat,
    val metadata: EmbroideryMetadata,
    val stitches: List<StitchCommand>,
)

data class StitchCommand(
    val x: Float,
    val y: Float,
    val type: StitchType,
)

enum class StitchType {
    STITCH,
    JUMP,
    TRIM,
    COLOR_CHANGE,
    STOP,
    END,
}
