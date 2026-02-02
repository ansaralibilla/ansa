package com.ansa.embroidery.data

import java.io.InputStream

class EmbroideryRepository(
    private val parser: EmbroideryParser,
) {
    fun openDesign(stream: InputStream, format: EmbroideryFormat): EmbroideryDesign {
        return parser.parse(stream, format)
    }

    fun convertDesign(design: EmbroideryDesign, targetFormat: EmbroideryFormat): ByteArray {
        return parser.write(design, targetFormat)
    }
}
