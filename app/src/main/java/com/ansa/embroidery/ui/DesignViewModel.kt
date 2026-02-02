package com.ansa.embroidery.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ansa.embroidery.data.EmbroideryDesign

class DesignViewModel : ViewModel() {
    private val _viewState = MutableLiveData(
        DesignDetailsViewState(
            detailsText = "Open a DST/PES/JEF/EXP file to view metadata and preview stitches.",
        ),
    )
    val viewState: LiveData<DesignDetailsViewState> = _viewState

    fun onDesignLoaded(design: EmbroideryDesign) {
        val metadata = design.metadata
        val details = buildString {
            appendLine("Format: ${design.format}")
            appendLine("Size: ${metadata.widthMm}mm x ${metadata.heightMm}mm")
            appendLine("Stitches: ${metadata.stitchCount}")
            appendLine("Color changes: ${metadata.colorChanges}")
            appendLine("Stops: ${metadata.stopCount}")
        }
        _viewState.value = DesignDetailsViewState(detailsText = details.trim())
    }
}
