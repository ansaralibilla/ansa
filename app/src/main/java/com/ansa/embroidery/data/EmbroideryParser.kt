package com.ansa.embroidery.data

import java.io.InputStream

interface EmbroideryParser {
    val supportedFormats: Set<EmbroideryFormat>

    fun parse(stream: InputStream, format: EmbroideryFormat): EmbroideryDesign

    fun write(design: EmbroideryDesign, format: EmbroideryFormat): ByteArray
}
