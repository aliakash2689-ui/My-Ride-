package com.example

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ApplicationProvider
import com.example.model.AppLanguage
import com.example.model.RideType
import com.example.ui.MyRideScreen
import com.example.ui.RideViewModel
import com.example.ui.theme.MyRideTheme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun `app name matches My Ride`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("My Ride", appName)
  }

  @Test
  fun `booking flow validates empty inputs and shows preview dialog when filled`() {
    val viewModel = RideViewModel()

    composeTestRule.setContent {
      MyRideTheme {
        MyRideScreen(viewModel = viewModel)
      }
    }

    // Empty inputs should flag errors and not open dialog
    composeTestRule.onNodeWithTag("book_ride_button").performScrollTo().performClick()
    assertTrue(viewModel.uiState.value.pickupError)
    assertTrue(viewModel.uiState.value.destinationError)
    assertNull(viewModel.uiState.value.activeBookingPreview)

    // Select Toto, fill pickup and destination, then book
    composeTestRule.onNodeWithTag("ride_option_toto").performScrollTo().performClick()
    composeTestRule.onNodeWithTag("pickup_input").performScrollTo().performTextInput("Itahar Bus Stand")
    composeTestRule.onNodeWithTag("destination_input").performScrollTo().performTextInput("Baliyapara More")
    composeTestRule.onNodeWithTag("book_ride_button").performScrollTo().performClick()

    assertEquals(RideType.TOTO, viewModel.uiState.value.selectedRide)
    assertNotNull(viewModel.uiState.value.activeBookingPreview)

    composeTestRule.onNodeWithTag("booking_dialog_body").assertIsDisplayed()
    composeTestRule.onNodeWithTag("booking_dialog_body").assertTextContains("Toto", substring = true)
    composeTestRule.onNodeWithTag("booking_dialog_ok_button").performClick()

    assertNull(viewModel.uiState.value.activeBookingPreview)
  }

  @Test
  fun `language switcher updates UI copy across Bengali Hindi and English`() {
    val viewModel = RideViewModel()

    composeTestRule.setContent {
      MyRideTheme {
        MyRideScreen(viewModel = viewModel)
      }
    }

    assertEquals(AppLanguage.BENGALI, viewModel.uiState.value.selectedLanguage)

    composeTestRule.onNodeWithTag("language_selector_button").performClick()
    composeTestRule.onNodeWithTag("language_option_en").performClick()
    assertEquals(AppLanguage.ENGLISH, viewModel.uiState.value.selectedLanguage)

    composeTestRule.onNodeWithTag("book_ride_button").performScrollTo().assertTextContains("BOOK YOUR RIDE")

    composeTestRule.onNodeWithTag("language_selector_button").performClick()
    composeTestRule.onNodeWithTag("language_option_hi").performClick()
    assertEquals(AppLanguage.HINDI, viewModel.uiState.value.selectedLanguage)
  }
}
