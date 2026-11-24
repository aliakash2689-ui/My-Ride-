package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.AirportShuttle
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.DirectionsCar
import androidx.compose.material.icons.rounded.ElectricRickshaw
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material.icons.rounded.LocalTaxi
import androidx.compose.material.icons.rounded.LocationCity
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.MyLocation
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.SwapVert
import androidx.compose.material.icons.rounded.TwoWheeler
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.AppLanguage
import com.example.model.BookingPreview
import com.example.model.LocalizedStrings
import com.example.model.RideCopy
import com.example.model.RideType
import com.example.ui.theme.BrandAmber
import com.example.ui.theme.BrandOrange
import com.example.ui.theme.BrandOrangeDeep
import com.example.ui.theme.DestinationRed
import com.example.ui.theme.HeroScrimDark
import com.example.ui.theme.PickupGreen

fun RideType.icon(): ImageVector = when (this) {
  RideType.BIKE -> Icons.Rounded.TwoWheeler
  RideType.TOTO -> Icons.Rounded.ElectricRickshaw
  RideType.AUTO -> Icons.Rounded.LocalTaxi
  RideType.CAR -> Icons.Rounded.DirectionsCar
  RideType.SUV -> Icons.Rounded.AirportShuttle
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyRideTopBar(
  selectedLanguage: AppLanguage,
  strings: LocalizedStrings,
  onSelectLanguage: (AppLanguage) -> Unit,
) {
  var languageMenuExpanded by remember { mutableStateOf(false) }

  TopAppBar(
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = MaterialTheme.colorScheme.primary,
      titleContentColor = MaterialTheme.colorScheme.onPrimary,
      actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
    ),
    title = {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
      ) {
        Surface(
          shape = CircleShape,
          color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.18f),
          modifier = Modifier.size(36.dp),
        ) {
          Box(contentAlignment = Alignment.Center) {
            Icon(
              imageVector = Icons.Rounded.ElectricRickshaw,
              contentDescription = null,
              tint = MaterialTheme.colorScheme.onPrimary,
              modifier = Modifier.size(20.dp),
            )
          }
        }
        Column {
          Text(
            text = strings.appBrandTitle,
            style = MaterialTheme.typography.titleLarge.copy(
              letterSpacing = 2.sp,
              fontWeight = FontWeight.Bold,
            ),
            maxLines = 1,
          )
          Text(
            text = strings.serviceAreaTowns,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f),
            maxLines = 1,
          )
        }
      }
    },
    actions = {
      Box {
        Surface(
          shape = RoundedCornerShape(50),
          color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.16f),
          modifier = Modifier
            .padding(end = 12.dp)
            .minimumInteractiveComponentSize()
            .clip(RoundedCornerShape(50))
            .clickable { languageMenuExpanded = true }
            .testTag("language_selector_button"),
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
          ) {
            Icon(
              imageVector = Icons.Rounded.Language,
              contentDescription = strings.languageMenuDesc,
              tint = MaterialTheme.colorScheme.onPrimary,
              modifier = Modifier.size(18.dp),
            )
            Text(
              text = selectedLanguage.nativeLabel,
              style = MaterialTheme.typography.labelMedium,
              color = MaterialTheme.colorScheme.onPrimary,
            )
          }
        }

        DropdownMenu(
          expanded = languageMenuExpanded,
          onDismissRequest = { languageMenuExpanded = false },
        ) {
          AppLanguage.entries.forEach { language ->
            DropdownMenuItem(
              text = {
                Text(
                  text = language.nativeLabel,
                  style = MaterialTheme.typography.bodyLarge,
                  fontWeight = if (language == selectedLanguage) FontWeight.Bold else FontWeight.Normal,
                )
              },
              trailingIcon = {
                if (language == selectedLanguage) {
                  Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                  )
                }
              },
              modifier = Modifier.testTag("language_option_${language.code}"),
              onClick = {
                languageMenuExpanded = false
                onSelectLanguage(language)
              },
            )
          }
        }
      }
    },
  )
}

@Composable
fun HeroWelcomeBanner(
  strings: LocalizedStrings,
  modifier: Modifier = Modifier,
) {
  Card(
    modifier = modifier.fillMaxWidth(),
    shape = MaterialTheme.shapes.large,
    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(210.dp),
    ) {
      Image(
        painter = painterResource(id = R.drawable.img_ride_hero),
        contentDescription = strings.welcomeHeadline,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize(),
      )

      Box(
        modifier = Modifier
          .fillMaxSize()
          .background(
            Brush.verticalGradient(
              colors = listOf(
                BrandOrangeDeep.copy(alpha = 0.65f),
                HeroScrimDark.copy(alpha = 0.88f),
              )
            )
          ),
      )

      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(20.dp),
        verticalArrangement = Arrangement.SpaceBetween,
      ) {
        Surface(
          shape = RoundedCornerShape(50),
          color = BrandAmber.copy(alpha = 0.24f),
          border = BorderStroke(1.dp, BrandAmber.copy(alpha = 0.6f)),
        ) {
          Text(
            text = strings.tagline,
            style = MaterialTheme.typography.labelMedium,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
          )
        }

        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
          Text(
            text = strings.welcomeHeadline,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
          )
          Text(
            text = strings.safeJourneySubtitle,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White.copy(alpha = 0.92f),
          )
        }
      }
    }
  }
}

@Composable
fun RideSelectorSection(
  selectedRide: RideType,
  strings: LocalizedStrings,
  columnsCount: Int,
  onSelectRide: (RideType) -> Unit,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(12.dp),
  ) {
    Text(
      text = strings.chooseRideTitle,
      style = MaterialTheme.typography.titleLarge,
      color = MaterialTheme.colorScheme.onBackground,
    )

    val rows = remember(columnsCount) { RideType.entries.chunked(columnsCount) }
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
      rows.forEach { rowItems ->
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
          rowItems.forEach { ride ->
            RideOptionCard(
              ride = ride,
              isSelected = ride == selectedRide,
              seatsUnit = strings.seatsUnit,
              onClick = { onSelectRide(ride) },
              modifier = Modifier.weight(1f),
            )
          }
          repeat(columnsCount - rowItems.size) {
            Spacer(modifier = Modifier.weight(1f))
          }
        }
      }
    }
  }
}

@Composable
private fun RideOptionCard(
  ride: RideType,
  isSelected: Boolean,
  seatsUnit: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val containerColor by animateColorAsState(
    targetValue = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
    label = "ride_card_bg",
  )
  val contentColor by animateColorAsState(
    targetValue = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
    label = "ride_card_fg",
  )
  val iconTint by animateColorAsState(
    targetValue = if (isSelected) MaterialTheme.colorScheme.onPrimary else BrandOrange,
    label = "ride_card_icon",
  )
  val borderColor by animateColorAsState(
    targetValue = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
    label = "ride_card_border",
  )
  val scale by animateFloatAsState(
    targetValue = if (isSelected) 1.02f else 1f,
    animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
    label = "ride_card_scale",
  )
  val elevation by animateDpAsState(
    targetValue = if (isSelected) 6.dp else 1.dp,
    label = "ride_card_elevation",
  )

  Surface(
    modifier = modifier
      .scale(scale)
      .height(104.dp)
      .minimumInteractiveComponentSize()
      .clip(MaterialTheme.shapes.medium)
      .clickable(onClick = onClick)
      .testTag("ride_option_${ride.id}"),
    shape = MaterialTheme.shapes.medium,
    color = containerColor,
    contentColor = contentColor,
    tonalElevation = if (isSelected) 0.dp else 2.dp,
    shadowElevation = elevation,
    border = BorderStroke(if (isSelected) 2.dp else 1.dp, borderColor),
  ) {
    Box(modifier = Modifier.fillMaxSize().padding(10.dp)) {
      if (isSelected) {
        Icon(
          imageVector = Icons.Rounded.CheckCircle,
          contentDescription = null,
          tint = contentColor.copy(alpha = 0.9f),
          modifier = Modifier
            .size(16.dp)
            .align(Alignment.TopEnd),
        )
      }

      Column(
        modifier = Modifier.align(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp),
      ) {
        Icon(
          imageVector = ride.icon(),
          contentDescription = ride.displayName,
          tint = iconTint,
          modifier = Modifier.size(32.dp),
        )
        Text(
          text = ride.displayName,
          style = MaterialTheme.typography.titleSmall,
          fontWeight = FontWeight.Bold,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis,
        )
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(2.dp),
        ) {
          Icon(
            imageVector = Icons.Rounded.Person,
            contentDescription = null,
            tint = contentColor.copy(alpha = 0.72f),
            modifier = Modifier.size(12.dp),
          )
          Text(
            text = "${ride.seatCapacity} $seatsUnit",
            style = MaterialTheme.typography.labelSmall,
            color = contentColor.copy(alpha = 0.78f),
            maxLines = 1,
          )
        }
      }
    }
  }
}

@Composable
fun RouteInputSection(
  pickup: String,
  destination: String,
  pickupError: Boolean,
  destinationError: Boolean,
  strings: LocalizedStrings,
  onPickupChange: (String) -> Unit,
  onDestinationChange: (String) -> Unit,
  onSwapLocations: () -> Unit,
  onQuickStopClick: (String) -> Unit,
  onBookRide: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(12.dp),
  ) {
    Text(
      text = strings.whereAreYouGoingTitle,
      style = MaterialTheme.typography.titleLarge,
      color = MaterialTheme.colorScheme.onBackground,
    )

    Card(
      shape = MaterialTheme.shapes.large,
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
      border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f)),
    ) {
      Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
          verticalAlignment = Alignment.CenterVertically,
        ) {
          // Dotted vertical route connector
          val lineColor = MaterialTheme.colorScheme.outlineVariant
          Column(
            modifier = Modifier
              .width(24.dp)
              .fillMaxHeight()
              .padding(vertical = 22.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
          ) {
            Box(
              modifier = Modifier
                .size(10.dp)
                .background(PickupGreen, CircleShape)
            )
            Canvas(
              modifier = Modifier
                .weight(1f)
                .width(2.dp)
                .padding(vertical = 4.dp)
            ) {
              drawLine(
                color = lineColor,
                start = Offset(size.width / 2f, 0f),
                end = Offset(size.width / 2f, size.height),
                strokeWidth = 3f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 8f), 0f),
              )
            }
            Box(
              modifier = Modifier
                .size(10.dp)
                .background(DestinationRed, RoundedCornerShape(3.dp))
            )
          }

          Spacer(modifier = Modifier.width(8.dp))

          Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp),
          ) {
            OutlinedTextField(
              value = pickup,
              onValueChange = onPickupChange,
              modifier = Modifier
                .fillMaxWidth()
                .testTag("pickup_input"),
              singleLine = true,
              isError = pickupError,
              placeholder = { Text(strings.pickupHint) },
              leadingIcon = {
                Icon(
                  imageVector = Icons.Rounded.MyLocation,
                  contentDescription = null,
                  tint = PickupGreen,
                )
              },
              trailingIcon = {
                if (pickup.isNotEmpty()) {
                  IconButton(
                    onClick = { onPickupChange("") },
                    modifier = Modifier.testTag("clear_pickup_button"),
                  ) {
                    Icon(
                      imageVector = Icons.Rounded.Close,
                      contentDescription = strings.clearFieldDesc,
                    )
                  }
                }
              },
              keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
              shape = MaterialTheme.shapes.small,
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
              ),
            )

            OutlinedTextField(
              value = destination,
              onValueChange = onDestinationChange,
              modifier = Modifier
                .fillMaxWidth()
                .testTag("destination_input"),
              singleLine = true,
              isError = destinationError,
              placeholder = { Text(strings.destinationHint) },
              leadingIcon = {
                Icon(
                  imageVector = Icons.Rounded.LocationOn,
                  contentDescription = null,
                  tint = DestinationRed,
                )
              },
              trailingIcon = {
                if (destination.isNotEmpty()) {
                  IconButton(
                    onClick = { onDestinationChange("") },
                    modifier = Modifier.testTag("clear_destination_button"),
                  ) {
                    Icon(
                      imageVector = Icons.Rounded.Close,
                      contentDescription = strings.clearFieldDesc,
                    )
                  }
                }
              },
              keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
              keyboardActions = KeyboardActions(onDone = { onBookRide() }),
              shape = MaterialTheme.shapes.small,
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
              ),
            )
          }

          Spacer(modifier = Modifier.width(6.dp))

          FilledTonalIconButton(
            onClick = onSwapLocations,
            colors = IconButtonDefaults.filledTonalIconButtonColors(
              containerColor = MaterialTheme.colorScheme.primaryContainer,
              contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            ),
            modifier = Modifier
              .minimumInteractiveComponentSize()
              .testTag("swap_locations_button"),
          ) {
            Icon(
              imageVector = Icons.Rounded.SwapVert,
              contentDescription = strings.swapLocationsDesc,
            )
          }
        }

        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Text(
            text = strings.quickStopsLabel,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
          )
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
          ) {
            RideCopy.localLandmarks.forEach { stop ->
              AssistChip(
                onClick = { onQuickStopClick(stop) },
                label = {
                  Text(
                    text = stop,
                    style = MaterialTheme.typography.labelMedium,
                  )
                },
                leadingIcon = {
                  Icon(
                    imageVector = Icons.Rounded.LocationOn,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.primary,
                  )
                },
                colors = AssistChipDefaults.assistChipColors(
                  containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                modifier = Modifier.testTag("quick_stop_${stop.lowercase().replace(' ', '_')}"),
              )
            }
          }
        }
      }
    }
  }
}

@Composable
fun BookRideButton(
  strings: LocalizedStrings,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Button(
    onClick = onClick,
    modifier = modifier
      .fillMaxWidth()
      .height(56.dp)
      .testTag("book_ride_button"),
    shape = RoundedCornerShape(16.dp),
    colors = ButtonDefaults.buttonColors(
      containerColor = MaterialTheme.colorScheme.primary,
      contentColor = MaterialTheme.colorScheme.onPrimary,
    ),
    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
  ) {
    Text(
      text = strings.bookRideButton,
      style = MaterialTheme.typography.labelLarge,
    )
    Spacer(modifier = Modifier.width(8.dp))
    Icon(
      imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
      contentDescription = null,
      modifier = Modifier.size(20.dp),
    )
  }
}

@Composable
fun ServiceAreaCard(
  strings: LocalizedStrings,
  modifier: Modifier = Modifier,
) {
  Card(
    modifier = modifier.fillMaxWidth(),
    shape = MaterialTheme.shapes.large,
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f)),
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
      Surface(
        shape = CircleShape,
        color = MaterialTheme.colorScheme.primaryContainer,
        modifier = Modifier.size(52.dp),
      ) {
        Box(contentAlignment = Alignment.Center) {
          Icon(
            imageVector = Icons.Rounded.LocationCity,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(28.dp),
          )
        }
      }

      Spacer(modifier = Modifier.height(4.dp))

      Text(
        text = strings.serviceAreaHeading,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface,
        textAlign = TextAlign.Center,
      )
      Text(
        text = strings.serviceAreaTowns,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary,
        textAlign = TextAlign.Center,
      )
      Text(
        text = strings.serviceAreaNearby,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center,
      )
      Surface(
        shape = RoundedCornerShape(50),
        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.65f),
        modifier = Modifier.padding(top = 4.dp),
      ) {
        Text(
          text = strings.serviceAreaRegion,
          style = MaterialTheme.typography.labelMedium,
          color = MaterialTheme.colorScheme.onSecondaryContainer,
          modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
        )
      }
    }
  }
}

@Composable
fun FooterCopyright(
  strings: LocalizedStrings,
  modifier: Modifier = Modifier,
) {
  Text(
    text = strings.footerCopyright,
    style = MaterialTheme.typography.bodySmall.copy(lineHeight = 20.sp),
    color = MaterialTheme.colorScheme.onSurfaceVariant,
    textAlign = TextAlign.Center,
    modifier = modifier
      .fillMaxWidth()
      .padding(vertical = 8.dp),
  )
}

@Composable
fun BookingPreviewDialog(
  preview: BookingPreview,
  strings: LocalizedStrings,
  onDismiss: () -> Unit,
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    icon = {
      Surface(
        shape = CircleShape,
        color = MaterialTheme.colorScheme.primaryContainer,
        modifier = Modifier.size(56.dp),
      ) {
        Box(contentAlignment = Alignment.Center) {
          Icon(
            imageVector = preview.rideType.icon(),
            contentDescription = preview.rideType.displayName,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(30.dp),
          )
        }
      }
    },
    title = {
      Text(
        text = strings.dialogTitle,
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center,
      )
    },
    text = {
      Text(
        text = strings.dialogBodyFormatter(
          preview.rideType.displayName,
          preview.pickup,
          preview.destination,
        ),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.testTag("booking_dialog_body"),
      )
    },
    confirmButton = {
      TextButton(
        onClick = onDismiss,
        modifier = Modifier
          .minimumInteractiveComponentSize()
          .testTag("booking_dialog_ok_button"),
      ) {
        Text(
          text = strings.dialogDismissButton,
          style = MaterialTheme.typography.labelLarge,
        )
      }
    },
  )
}
