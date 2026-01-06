package com.unicofrance.uniexo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.ui.Modifier
import com.unicofrance.uniexo.UniExoApplication
import com.unicofrance.uniexo.ui.googleMap.GoogleMapScreen
import com.unicofrance.uniexo.ui.googleMap.GoogleMapViewModel

class MainActivity : ComponentActivity() {
    private val app by lazy { application as UniExoApplication }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val insets = WindowInsets.systemBars
            GoogleMapScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = insets.asPaddingValues().calculateTopPadding()),
                viewModel = GoogleMapViewModel(containerRepository = app.containerRepository)
            )
        }
    }
}