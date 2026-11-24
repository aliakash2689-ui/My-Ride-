package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.LanguagePreferenceStore
import com.example.model.AppLanguage
import com.example.model.BookingPreview
import com.example.model.LocalizedStrings
import com.example.model.RideCopy
import com.example.model.RideType
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RideUiState(
  val selectedLanguage: AppLanguage = AppLanguage.BENGALI,
  val selectedRide: RideType = RideType.BIKE,
  val pickup: String = "",
  val destination: String = "",
  val pickupError: Boolean = false,
  val destinationError: Boolean = false,
  val activeBookingPreview: BookingPreview? = null,
) {
  val strings: LocalizedStrings
    get() = RideCopy.forLanguage(selectedLanguage)
}

sealed interface RideUiEvent {
  data class ShowValidationSnackbar(val message: String) : RideUiEvent
}

class RideViewModel(
  private val languageStore: LanguagePreferenceStore? = null,
) : ViewModel() {

  private val _uiState = MutableStateFlow(RideUiState())
  val uiState: StateFlow<RideUiState> = _uiState.asStateFlow()

  private val _events = MutableSharedFlow<RideUiEvent>()
  val events: SharedFlow<RideUiEvent> = _events.asSharedFlow()

  init {
    languageStore?.let { store ->
      viewModelScope.launch {
        store.selectedLanguage.collect { savedLanguage ->
          _uiState.update { it.copy(selectedLanguage = savedLanguage) }
        }
      }
    }
  }

  fun selectLanguage(language: AppLanguage) {
    _uiState.update { it.copy(selectedLanguage = language) }
    languageStore?.let { store ->
      viewModelScope.launch {
        store.saveLanguage(language)
      }
    }
  }

  fun selectRide(ride: RideType) {
    _uiState.update { it.copy(selectedRide = ride) }
  }

  fun onPickupChange(value: String) {
    _uiState.update {
      it.copy(
        pickup = value,
        pickupError = if (value.isNotBlank()) false else it.pickupError,
      )
    }
  }

  fun onDestinationChange(value: String) {
    _uiState.update {
      it.copy(
        destination = value,
        destinationError = if (value.isNotBlank()) false else it.destinationError,
      )
    }
  }

  fun swapLocations() {
    _uiState.update { state ->
      state.copy(
        pickup = state.destination,
        destination = state.pickup,
        pickupError = false,
        destinationError = false,
      )
    }
  }

  fun applyQuickStop(stopName: String) {
    _uiState.update { state ->
      when {
        state.pickup.isBlank() -> state.copy(pickup = stopName, pickupError = false)
        state.destination.isBlank() && state.pickup.trim() != stopName ->
          state.copy(destination = stopName, destinationError = false)
        else -> state.copy(destination = stopName, destinationError = false)
      }
    }
  }

  fun requestBookRide() {
    val current = _uiState.value
    val trimmedPickup = current.pickup.trim()
    val trimmedDestination = current.destination.trim()

    if (trimmedPickup.isEmpty() || trimmedDestination.isEmpty()) {
      _uiState.update {
        it.copy(
          pickupError = trimmedPickup.isEmpty(),
          destinationError = trimmedDestination.isEmpty(),
        )
      }
      viewModelScope.launch {
        _events.emit(
          RideUiEvent.ShowValidationSnackbar(current.strings.validationEmptyLocations)
        )
      }
      return
    }

    _uiState.update {
      it.copy(
        pickupError = false,
        destinationError = false,
        activeBookingPreview = BookingPreview(
          rideType = current.selectedRide,
          pickup = trimmedPickup,
          destination = trimmedDestination,
        ),
      )
    }
  }

  fun dismissBookingPreview() {
    _uiState.update { it.copy(activeBookingPreview = null) }
  }

  class Factory(private val languageStore: LanguagePreferenceStore) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
      return RideViewModel(languageStore) as T
    }
  }
}
