package com.ansa.embroidery.data

import java.io.InputStream
import kotlin.math.max

class SimpleEmbroideryParser : EmbroideryParser {
    override val supportedFormats: Set<EmbroideryFormat> = setOf(
        EmbroideryFormat.DST,
        EmbroideryFormat.PES,
        EmbroideryFormat.JEF,
        EmbroideryFormat.EXP,
        EmbroideryFormat.VP3,
        EmbroideryFormat.XXX,
    )

    override fun parse(stream: InputStream, format: EmbroideryFormat): EmbroideryDesign {
        val bytes = stream.readBytes()
        val stitchCount = max(bytes.size / 3, 1)
        val metadata = EmbroideryMetadata(
            widthMm = 120f,
            heightMm = 120f,
            stitchCount = stitchCount,
            colorChanges = 1,
            stopCount = 0,
        )
        val stitches = List(stitchCount.coerceAtMost(200)) { index ->
            val x = (index % 120).toFloat()
            val y = (index / 120).toFloat()
            StitchCommand(x, y, StitchType.STITCH)
        }
        return EmbroideryDesign(
            name = "Design (${format.extension.uppercase()})",
            format = format,
            metadata = metadata,
            stitches = stitches,
        )
    }

    override fun write(design: EmbroideryDesign, format: EmbroideryFormat): ByteArray {
        val header = "FORMAT:${format.extension};STITCHES:${design.metadata.stitchCount}\n"
        return header.toByteArray()
    }
}
