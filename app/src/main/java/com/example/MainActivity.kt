package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.data.LanguagePreferenceStore
import com.example.ui.MyRideScreen
import com.example.ui.RideViewModel
import com.example.ui.theme.MyRideTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    val languageStore = LanguagePreferenceStore(applicationContext)
    val viewModel = ViewModelProvider(
      this,
      RideViewModel.Factory(languageStore),
    )[RideViewModel::class.java]

    setContent {
      MyRideTheme {
        MyRideScreen(viewModel = viewModel)
      }
    }
  }
}
