package com.ansa.embroidery.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SimpleEmbroideryParserTest {
    private val parser = SimpleEmbroideryParser()

    @Test
    fun `parses stream into design metadata`() {
        val bytes = ByteArray(300) { 1 }
        val design = parser.parse(bytes.inputStream(), EmbroideryFormat.DST)

        assertEquals(EmbroideryFormat.DST, design.format)
        assertTrue(design.metadata.stitchCount > 0)
        assertTrue(design.stitches.isNotEmpty())
    }

    @Test
    fun `writes converted design bytes`() {
        val bytes = ByteArray(30) { 2 }
        val design = parser.parse(bytes.inputStream(), EmbroideryFormat.PES)
        val output = parser.write(design, EmbroideryFormat.JEF)

        val outputText = String(output)
        assertTrue(outputText.contains("FORMAT:jef"))
    }
}
