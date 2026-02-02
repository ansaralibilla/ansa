package com.ansa.embroidery

import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.ansa.embroidery.data.EmbroideryFormat
import com.ansa.embroidery.data.EmbroideryRepository
import com.ansa.embroidery.data.SimpleEmbroideryParser
import com.ansa.embroidery.render.DesignSurfaceView
import com.ansa.embroidery.render.RenderMode
import com.ansa.embroidery.ui.DesignViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: DesignViewModel by viewModels()
    private val repository = EmbroideryRepository(SimpleEmbroideryParser())
    private var currentMode = RenderMode.TRUE_VIEW

    private val openDocument = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        uri?.let { openDesign(it) }
    }

    private val createDocument = registerForActivityResult(ActivityResultContracts.CreateDocument("application/octet-stream")) { uri ->
        uri?.let { exportDesign(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val details = findViewById<TextView>(R.id.details)
        val designSurface = findViewById<DesignSurfaceView>(R.id.designSurface)
        val toggleViewButton = findViewById<Button>(R.id.toggleViewButton)
        val openButton = findViewById<Button>(R.id.openButton)
        val exportButton = findViewById<Button>(R.id.exportButton)
        val formatSpinner = findViewById<Spinner>(R.id.formatSpinner)

        val formats = EmbroideryFormat.entries.filter { it != EmbroideryFormat.UNKNOWN }
        formatSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            formats.map { it.extension.uppercase() },
        )

        openButton.setOnClickListener {
            openDocument.launch(arrayOf("*/*"))
        }

        toggleViewButton.setOnClickListener {
            currentMode = if (currentMode == RenderMode.TRUE_VIEW) {
                toggleViewButton.text = "Stitch View"
                RenderMode.STITCH_VIEW
            } else {
                toggleViewButton.text = "True View"
                RenderMode.TRUE_VIEW
            }
            val design = viewModel.currentDesign
            if (design != null) {
                designSurface.showDesign(design, currentMode)
            }
        }

        exportButton.setOnClickListener {
            val selectedFormat = formats[formatSpinner.selectedItemPosition]
            createDocument.launch("design.${selectedFormat.extension}")
        }

        viewModel.viewState.observe(this) { state ->
            details.text = state.detailsText
            designSurface.showDesign(state.design, currentMode)
        }
    }

    private fun openDesign(uri: Uri) {
        val format = EmbroideryFormat.fromUri(uri)
        contentResolver.openInputStream(uri)?.use { stream ->
            val design = repository.openDesign(stream, format)
            viewModel.onDesignLoaded(design)
        }
    }

    private fun exportDesign(uri: Uri) {
        val design = viewModel.currentDesign ?: return
        val format = EmbroideryFormat.fromUri(uri)
        val bytes = repository.convertDesign(design, format)
        contentResolver.openOutputStream(uri)?.use { stream ->
            stream.write(bytes)
        }
    }
}
