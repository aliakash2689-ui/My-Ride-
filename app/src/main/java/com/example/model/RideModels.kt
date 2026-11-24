package com.example.model

enum class AppLanguage(val code: String, val nativeLabel: String) {
  BENGALI("bn", "বাংলা"),
  HINDI("hi", "हिन्दी"),
  ENGLISH("en", "English");

  companion object {
    fun fromCode(code: String?): AppLanguage =
      entries.firstOrNull { it.code == code } ?: BENGALI
  }
}

enum class RideType(
  val id: String,
  val displayName: String,
  val seatCapacity: Int,
) {
  BIKE("bike", "Bike", 1),
  TOTO("toto", "Toto", 4),
  AUTO("auto", "Auto", 3),
  CAR("car", "Car", 4),
  SUV("suv", "SUV", 6),
}

data class BookingPreview(
  val rideType: RideType,
  val pickup: String,
  val destination: String,
)

data class LocalizedStrings(
  val appBrandTitle: String,
  val welcomeHeadline: String,
  val tagline: String,
  val safeJourneySubtitle: String,
  val chooseRideTitle: String,
  val seatsUnit: String,
  val whereAreYouGoingTitle: String,
  val pickupHint: String,
  val destinationHint: String,
  val quickStopsLabel: String,
  val swapLocationsDesc: String,
  val clearFieldDesc: String,
  val bookRideButton: String,
  val validationEmptyLocations: String,
  val dialogTitle: String,
  val dialogBodyFormatter: (rideName: String, pickup: String, destination: String) -> String,
  val dialogDismissButton: String,
  val serviceAreaHeading: String,
  val serviceAreaTowns: String,
  val serviceAreaNearby: String,
  val serviceAreaRegion: String,
  val footerCopyright: String,
  val languageMenuDesc: String,
)

object RideCopy {
  val localLandmarks = listOf(
    "Itahar Bus Stand",
    "Baliyapara More",
    "Itahar Hospital",
    "Durgapur More",
    "Churamon",
    "Raiganj Road",
  )

  fun forLanguage(language: AppLanguage): LocalizedStrings = when (language) {
    AppLanguage.BENGALI -> LocalizedStrings(
      appBrandTitle = "MY RIDE",
      welcomeHeadline = "MY RIDE-এ স্বাগতম",
      tagline = "Your Ride, Our Priority",
      safeJourneySubtitle = "আপনার যাত্রা হোক নিরাপদ ও আরামদায়ক।",
      chooseRideTitle = "আপনার রাইড বেছে নিন",
      seatsUnit = "জন",
      whereAreYouGoingTitle = "আপনি কোথায় যাচ্ছেন?",
      pickupHint = "পিকআপ লোকেশন (Pickup)",
      destinationHint = "গন্তব্য লোকেশন (Destination)",
      quickStopsLabel = "জনপ্রিয় স্টপ",
      swapLocationsDesc = "পিকআপ ও গন্তব্য বদল করুন",
      clearFieldDesc = "মুছে ফেলুন",
      bookRideButton = "বুক করুন • BOOK YOUR RIDE",
      validationEmptyLocations = "Pickup এবং Destination লিখুন",
      dialogTitle = "MY RIDE",
      dialogBodyFormatter = { rideName, pickup, destination ->
        "আপনার $rideName বুকিংয়ের অনুরোধ তৈরি করার জন্য প্রস্তুত।\n\n" +
          "Pickup: $pickup\n" +
          "Destination: $destination\n\n" +
          "এটি ডেমো। এখনো আসল বুকিং চালু হয়নি।"
      },
      dialogDismissButton = "ঠিক আছে",
      serviceAreaHeading = "আমাদের পরিষেবা এলাকা",
      serviceAreaTowns = "Itahar • Baliyapara",
      serviceAreaNearby = "এবং আশেপাশের এলাকা",
      serviceAreaRegion = "পশ্চিমবঙ্গ, ভারত",
      footerCopyright = "MY RIDE © 2026\nYour Ride, Our Priority",
      languageMenuDesc = "ভাষা পরিবর্তন করুন",
    )

    AppLanguage.HINDI -> LocalizedStrings(
      appBrandTitle = "MY RIDE",
      welcomeHeadline = "MY RIDE में आपका स्वागत है",
      tagline = "Your Ride, Our Priority",
      safeJourneySubtitle = "आपकी यात्रा सुरक्षित और आरामदायक हो।",
      chooseRideTitle = "अपनी राइड चुनें",
      seatsUnit = "सीट",
      whereAreYouGoingTitle = "आप कहाँ जा रहे हैं?",
      pickupHint = "पिकअप स्थान (Pickup)",
      destinationHint = "गंतव्य स्थान (Destination)",
      quickStopsLabel = "लोकप्रिय स्टॉप",
      swapLocationsDesc = "पिकअप और गंतव्य बदलें",
      clearFieldDesc = "साफ़ करें",
      bookRideButton = "राइड बुक करें • BOOK YOUR RIDE",
      validationEmptyLocations = "Pickup और Destination दर्ज करें",
      dialogTitle = "MY RIDE",
      dialogBodyFormatter = { rideName, pickup, destination ->
        "आपका $rideName बुकिंग अनुरोध तैयार है।\n\n" +
          "Pickup: $pickup\n" +
          "Destination: $destination\n\n" +
          "यह एक डेमो है। अभी वास्तविक बुकिंग शुरू नहीं हुई है।"
      },
      dialogDismissButton = "ठीक है",
      serviceAreaHeading = "हमारा सेवा क्षेत्र",
      serviceAreaTowns = "Itahar • Baliyapara",
      serviceAreaNearby = "और आस-पास के क्षेत्र",
      serviceAreaRegion = "पश्चिम बंगाल, भारत",
      footerCopyright = "MY RIDE © 2026\nYour Ride, Our Priority",
      languageMenuDesc = "भाषा बदलें",
    )

    AppLanguage.ENGLISH -> LocalizedStrings(
      appBrandTitle = "MY RIDE",
      welcomeHeadline = "Welcome to MY RIDE",
      tagline = "Your Ride, Our Priority",
      safeJourneySubtitle = "Wishing you a safe and comfortable journey.",
      chooseRideTitle = "Choose Your Ride",
      seatsUnit = "seats",
      whereAreYouGoingTitle = "Where are you going?",
      pickupHint = "Pickup Location",
      destinationHint = "Destination Location",
      quickStopsLabel = "Popular Stops",
      swapLocationsDesc = "Swap pickup and destination",
      clearFieldDesc = "Clear location",
      bookRideButton = "BOOK YOUR RIDE",
      validationEmptyLocations = "Please enter Pickup and Destination",
      dialogTitle = "MY RIDE",
      dialogBodyFormatter = { rideName, pickup, destination ->
        "Your $rideName booking request is ready to be created.\n\n" +
          "Pickup: $pickup\n" +
          "Destination: $destination\n\n" +
          "This is a demo. Live booking is not active yet."
      },
      dialogDismissButton = "OK",
      serviceAreaHeading = "OUR SERVICE AREA",
      serviceAreaTowns = "Itahar • Baliyapara",
      serviceAreaNearby = "And Nearby Areas",
      serviceAreaRegion = "West Bengal, India",
      footerCopyright = "MY RIDE © 2026\nYour Ride, Our Priority",
      languageMenuDesc = "Change language",
    )
  }
}
