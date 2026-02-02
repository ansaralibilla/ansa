package com.ansa.embroidery.ui

import com.ansa.embroidery.data.EmbroideryDesign
import com.ansa.embroidery.data.EmbroideryFormat
import com.ansa.embroidery.data.EmbroideryMetadata
import com.ansa.embroidery.data.StitchCommand
import com.ansa.embroidery.data.StitchType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class DesignViewModelTest {
    @Test
    fun `updates view state when design is loaded`() {
        val viewModel = DesignViewModel()
        val design = EmbroideryDesign(
            name = "Sample",
            format = EmbroideryFormat.EXP,
            metadata = EmbroideryMetadata(
                widthMm = 100f,
                heightMm = 80f,
                stitchCount = 1200,
                colorChanges = 3,
                stopCount = 1,
            ),
            stitches = listOf(StitchCommand(0f, 0f, StitchType.STITCH)),
        )

        viewModel.onDesignLoaded(design)

        val state = viewModel.viewState.value
        assertNotNull(state)
        assertEquals(design, state?.design)
        assertEquals("Format: EXP", state?.detailsText?.lineSequence()?.first())
    }
}
