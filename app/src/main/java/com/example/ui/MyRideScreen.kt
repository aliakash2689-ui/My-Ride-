package com.example.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.BookRideButton
import com.example.ui.components.BookingPreviewDialog
import com.example.ui.components.FooterCopyright
import com.example.ui.components.HeroWelcomeBanner
import com.example.ui.components.MyRideTopBar
import com.example.ui.components.RideSelectorSection
import com.example.ui.components.RouteInputSection
import com.example.ui.components.ServiceAreaCard

@Composable
fun MyRideScreen(
  viewModel: RideViewModel,
  modifier: Modifier = Modifier,
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  val strings = uiState.strings
  val snackbarHostState = remember { SnackbarHostState() }

  LaunchedEffect(viewModel) {
    viewModel.events.collect { event ->
      when (event) {
        is RideUiEvent.ShowValidationSnackbar -> {
          snackbarHostState.currentSnackbarData?.dismiss()
          snackbarHostState.showSnackbar(event.message)
        }
      }
    }
  }

  LaunchedEffect(uiState.pickup, uiState.destination) {
    if (uiState.pickup.isNotBlank() || uiState.destination.isNotBlank()) {
      snackbarHostState.currentSnackbarData?.dismiss()
    }
  }

  Scaffold(
    modifier = modifier.fillMaxSize(),
    contentWindowInsets = WindowInsets.safeDrawing,
    topBar = {
      MyRideTopBar(
        selectedLanguage = uiState.selectedLanguage,
        strings = strings,
        onSelectLanguage = viewModel::selectLanguage,
      )
    },
    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
  ) { innerPadding ->
    BoxWithConstraints(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .imePadding(),
    ) {
      val isWideLayout = maxWidth >= 680.dp
      val scrollState = rememberScrollState()

      if (isWideLayout) {
        Box(
          modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 24.dp, vertical = 20.dp),
          contentAlignment = Alignment.TopCenter,
        ) {
          Row(
            modifier = Modifier.widthIn(max = 1040.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.Top,
          ) {
            Column(
              modifier = Modifier.weight(1.05f),
              verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
              HeroWelcomeBanner(strings = strings)
              RideSelectorSection(
                selectedRide = uiState.selectedRide,
                strings = strings,
                columnsCount = 3,
                onSelectRide = viewModel::selectRide,
              )
              ServiceAreaCard(strings = strings)
            }

            Column(
              modifier = Modifier.weight(0.95f),
              verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
              RouteInputSection(
                pickup = uiState.pickup,
                destination = uiState.destination,
                pickupError = uiState.pickupError,
                destinationError = uiState.destinationError,
                strings = strings,
                onPickupChange = viewModel::onPickupChange,
                onDestinationChange = viewModel::onDestinationChange,
                onSwapLocations = viewModel::swapLocations,
                onQuickStopClick = viewModel::applyQuickStop,
                onBookRide = viewModel::requestBookRide,
              )
              BookRideButton(
                strings = strings,
                onClick = viewModel::requestBookRide,
              )
              FooterCopyright(strings = strings)
            }
          }
        }
      } else {
        Box(
          modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 18.dp, vertical = 18.dp),
          contentAlignment = Alignment.TopCenter,
        ) {
          Column(
            modifier = Modifier
              .widthIn(max = 600.dp)
              .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(22.dp),
          ) {
            HeroWelcomeBanner(strings = strings)
            RideSelectorSection(
              selectedRide = uiState.selectedRide,
              strings = strings,
              columnsCount = 3,
              onSelectRide = viewModel::selectRide,
            )
            RouteInputSection(
              pickup = uiState.pickup,
              destination = uiState.destination,
              pickupError = uiState.pickupError,
              destinationError = uiState.destinationError,
              strings = strings,
              onPickupChange = viewModel::onPickupChange,
              onDestinationChange = viewModel::onDestinationChange,
              onSwapLocations = viewModel::swapLocations,
              onQuickStopClick = viewModel::applyQuickStop,
              onBookRide = viewModel::requestBookRide,
            )
            BookRideButton(
              strings = strings,
              onClick = viewModel::requestBookRide,
            )
            ServiceAreaCard(strings = strings)
            FooterCopyright(strings = strings)
          }
        }
      }
    }
  }

  uiState.activeBookingPreview?.let { preview ->
    BookingPreviewDialog(
      preview = preview,
      strings = strings,
      onDismiss = viewModel::dismissBookingPreview,
    )
  }
}
