package com.ansa.embroidery

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.ansa.embroidery.ui.DesignViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: DesignViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val details = findViewById<TextView>(R.id.details)
        viewModel.viewState.observe(this) { state ->
            details.text = state.detailsText
        }
    }
}
