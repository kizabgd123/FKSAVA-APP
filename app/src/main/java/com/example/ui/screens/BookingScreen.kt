package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Grass
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocalParking
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Roofing
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Shower
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material.icons.filled.Stadium
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WbIncandescent
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import com.example.ui.components.BookingSuccessVoucherDialog
import com.example.ui.components.CalendarDatePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BookingDate
import com.example.data.model.MatchDuration
import com.example.data.model.PitchType
import com.example.data.model.TimeSlot
import com.example.ui.components.BookingConfirmationSheet
import com.example.ui.components.SharedTopBar
import com.example.ui.theme.OutfitFont
import com.example.ui.theme.PitchCardActive
import com.example.ui.theme.PitchCardBorder
import com.example.ui.theme.PitchCardSurface
import com.example.ui.theme.PitchNightBase
import com.example.ui.theme.PrimaryTurfContainer
import com.example.ui.theme.PrimaryTurfLight
import com.example.ui.theme.SecondaryFloodlightAmber
import com.example.ui.theme.SecondaryFloodlightLight
import com.example.ui.theme.StatusBookedRed
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.BookingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(
    viewModel: BookingViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    val confirmationSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (uiState.isConfirmationSheetOpen) {
        BookingConfirmationSheet(
            uiState = uiState,
            viewModel = viewModel,
            sheetState = confirmationSheetState,
            onDismiss = { viewModel.closeConfirmationSheet() }
        )
    }

    if (uiState.showDatePickerDialog) {
        CalendarDatePickerDialog(
            selectedDay = uiState.selectedDate?.dayNumber?.toIntOrNull() ?: 16,
            onDateSelected = { day, monthNum, monthName, dayName ->
                viewModel.selectCustomDate(day, monthNum, monthName, dayName)
            },
            onDismiss = { viewModel.closeDatePicker() }
        )
    }

    if (uiState.showSuccessVoucher && uiState.lastConfirmedReservation != null) {
        BookingSuccessVoucherDialog(
            reservation = uiState.lastConfirmedReservation!!,
            onDismiss = { viewModel.dismissSuccessVoucher() },
            onViewMyBookings = { viewModel.navigateTo(AppScreen.MOJI_TERMINI) }
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PitchNightBase)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            SharedTopBar(
                onMenuClick = { viewModel.navigateTo(AppScreen.MOJI_TERMINI) }
            )

            // Scrollable Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .padding(bottom = 90.dp), // Clearance for sticky tray
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // HERO BANNER: Location, Status & Rating
                HeroStatusBanner()

                // 1. IZABERITE TEREN
                PitchSelectorSection(
                    selectedPitch = uiState.selectedPitch,
                    onPitchSelected = { viewModel.selectPitch(it) }
                )

                // PITCH DETAILS PREVIEW CARD
                PitchDetailsCard(selectedPitch = uiState.selectedPitch)

                // 2. IZABERITE DAN
                DateSelectorSection(
                    dates = viewModel.repository.availableDates,
                    selectedDate = uiState.selectedDate,
                    onDateSelected = { viewModel.selectDate(it) },
                    onOpenCalendar = { viewModel.openDatePicker() }
                )

                // 3. IZABERITE VREME
                TimeSlotsSection(
                    slots = viewModel.repository.timeSlots,
                    selectedSlot = uiState.selectedSlot,
                    basePrice = uiState.selectedPitch.baseRateRsd,
                    onSlotSelected = { viewModel.selectSlot(it) }
                )

                // 4. TRAJANJE UTAKMICE & BESPLATNE POGODNOSTI
                DurationAndExtrasSection(
                    selectedDuration = uiState.selectedDuration,
                    cafeTableSelected = uiState.cafeTableSelected,
                    onDurationSelected = { viewModel.selectDuration(it) },
                    onCafeTableToggle = { viewModel.toggleCafeTable(it) }
                )

                // OSTALE USLUGE FK SAVA 45
                OtherServicesSection(
                    onSchoolClick = { viewModel.navigateTo(AppScreen.SKOLICA) },
                    onBirthdayClick = { viewModel.navigateTo(AppScreen.KAFIC) }
                )

                // TESTIMONIAL PREVIEW
                TestimonialCard()
            }
        }

        // STICKY BOTTOM SUMMARY TRAY
        StickyBookingTray(
            summaryMeta = uiState.bookingSummaryMeta,
            totalPrice = uiState.formattedPriceRsd,
            onConfirmClick = { viewModel.openConfirmationSheet() },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun HeroStatusBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(PitchCardSurface)
            .border(1.dp, PitchCardBorder, RoundedCornerShape(16.dp))
            .padding(14.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(PitchNightBase)
                        .border(1.dp, PitchCardBorder, RoundedCornerShape(20.dp))
                        .padding(horizontal = 9.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = PrimaryTurfLight,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Dr Ivana Ribara 115, Blok 45",
                        fontSize = 11.sp,
                        color = TextMuted
                    )
                }

                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(PrimaryTurfContainer.copy(alpha = 0.12f))
                        .border(1.dp, PrimaryTurfContainer.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
                        .padding(horizontal = 9.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(PrimaryTurfContainer)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Otvoreno do 23:55h",
                        fontFamily = OutfitFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        color = PrimaryTurfLight
                    )
                }
            }

            HorizontalDivider(color = PitchCardBorder.copy(alpha = 0.6f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Rezervacija Terena",
                        fontFamily = OutfitFont,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 22.sp,
                        color = TextPrimary,
                        letterSpacing = (-0.01).sp
                    )
                    Text(
                        text = "Izaberite podlogu, vreme i potvrdite termin za 60 sekundi.",
                        fontSize = 12.sp,
                        color = TextMuted,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(PitchNightBase)
                        .border(1.dp, PitchCardBorder, RoundedCornerShape(10.dp))
                        .padding(horizontal = 8.dp, vertical = 5.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = SecondaryFloodlightAmber,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "5.0",
                                fontFamily = OutfitFont,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = SecondaryFloodlightLight
                            )
                        }
                        Text(
                            text = "48+ ocena",
                            fontSize = 10.sp,
                            color = TextMuted
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PitchSelectorSection(
    selectedPitch: PitchType,
    onPitchSelected: (PitchType) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "1. IZABERITE TEREN",
                fontFamily = OutfitFont,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                color = TextMuted,
                letterSpacing = 0.08.sp
            )
            Text(
                text = "Nova Trava 2026",
                fontFamily = OutfitFont,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                color = PrimaryTurfLight
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(PitchCardSurface)
                .border(1.dp, PitchCardBorder, RoundedCornerShape(14.dp))
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            PitchTabButton(
                title = "Mali Teren",
                subtitle = "5-na-5 · 4.000 RSD",
                icon = Icons.Default.SportsSoccer,
                isSelected = selectedPitch == PitchType.MALI,
                onClick = { onPitchSelected(PitchType.MALI) },
                modifier = Modifier.weight(1f)
            )

            PitchTabButton(
                title = "Veliki Teren",
                subtitle = "Mečevi · 6.000 RSD",
                icon = Icons.Default.Stadium,
                isSelected = selectedPitch == PitchType.VELIKI,
                onClick = { onPitchSelected(PitchType.VELIKI) },
                modifier = Modifier.weight(1f)
            )

            PitchTabButton(
                title = "Balon Teren",
                subtitle = "Zatvoreno · 5.000 RSD",
                icon = Icons.Default.Roofing,
                isSelected = selectedPitch == PitchType.BALON,
                onClick = { onPitchSelected(PitchType.BALON) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun PitchTabButton(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(if (isSelected) PitchCardActive else Color.Transparent)
            .border(
                width = if (isSelected) 1.5.dp else 0.dp,
                color = if (isSelected) PrimaryTurfContainer else Color.Transparent,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp, horizontal = 8.dp)
            .testTag("pitch_tab_${title.lowercase().replace(" ", "_")}"),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (isSelected) PrimaryTurfContainer else TextMuted,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = title,
                    fontFamily = OutfitFont,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    fontSize = 14.sp,
                    color = if (isSelected) PrimaryTurfLight else TextMuted
                )
            }
            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = TextMuted,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

@Composable
private fun PitchDetailsCard(selectedPitch: PitchType) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(PitchCardSurface)
            .border(1.dp, PitchCardBorder, RoundedCornerShape(16.dp))
            .padding(14.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(PrimaryTurfContainer.copy(alpha = 0.12f))
                    .border(1.dp, PrimaryTurfContainer.copy(alpha = 0.3f), RoundedCornerShape(6.dp))
                    .padding(horizontal = 8.dp, vertical = 3.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Bolt,
                    contentDescription = null,
                    tint = PrimaryTurfLight,
                    modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "POTVRDA ZA 5 MINUTA",
                    fontFamily = OutfitFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp,
                    color = PrimaryTurfLight,
                    letterSpacing = 0.08.sp
                )
            }

            Text(
                text = when (selectedPitch) {
                    PitchType.MALI -> "Standardni / Mali Fudbalski Teren"
                    PitchType.VELIKI -> "Veliki Fudbalski Teren za Utakmice"
                    PitchType.BALON -> "Zatvoreni Balon Teren sa Grejanjem"
                },
                fontFamily = OutfitFont,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = TextPrimary
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Dimenzije: ${selectedPitch.dimensions}  •  Kapacitet: ${selectedPitch.recommendedPlayers}",
                    fontSize = 11.sp,
                    color = SecondaryFloodlightLight,
                    fontWeight = FontWeight.Medium
                )
            }

            Text(
                text = selectedPitch.description,
                fontSize = 12.sp,
                color = TextMuted,
                lineHeight = 17.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            // 4 Facility Badges
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                FacilityBadge(icon = Icons.Default.Grass, label = "Trava 2026", tint = PrimaryTurfLight, modifier = Modifier.weight(1f))
                FacilityBadge(icon = Icons.Default.Lightbulb, label = "LED Reflektori", tint = SecondaryFloodlightAmber, modifier = Modifier.weight(1f))
                FacilityBadge(icon = Icons.Default.Shower, label = "Topli Tuševi", tint = PrimaryTurfLight, modifier = Modifier.weight(1f))
                FacilityBadge(icon = Icons.Default.LocalParking, label = "Besplatan Parking", tint = PrimaryTurfLight, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun FacilityBadge(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    tint: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(PitchNightBase)
            .border(1.dp, PitchCardBorder, RoundedCornerShape(8.dp))
            .padding(vertical = 8.dp, horizontal = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(17.dp)
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = label,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun DateSelectorSection(
    dates: List<BookingDate>,
    selectedDate: BookingDate?,
    onDateSelected: (BookingDate) -> Unit,
    onOpenCalendar: () -> Unit
) {
    val dateScrollState = rememberScrollState()

    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "2. IZABERITE DAN",
                fontFamily = OutfitFont,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                color = TextMuted,
                letterSpacing = 0.08.sp
            )

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(PitchCardActive)
                    .border(1.dp, PitchCardBorder, RoundedCornerShape(12.dp))
                    .clickable { onOpenCalendar() }
                    .padding(horizontal = 8.dp, vertical = 3.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = null,
                    tint = SecondaryFloodlightAmber,
                    modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Ceo Kalendar",
                    fontFamily = OutfitFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    color = SecondaryFloodlightLight
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(dateScrollState),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            dates.forEach { date ->
                val isSelected = selectedDate?.dayNumber == date.dayNumber

                Box(
                    modifier = Modifier
                        .width(76.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(if (isSelected) PitchCardActive else PitchCardSurface)
                        .border(
                            width = if (isSelected) 2.dp else 1.dp,
                            color = if (isSelected) PrimaryTurfContainer else PitchCardBorder,
                            shape = RoundedCornerShape(14.dp)
                        )
                        .clickable { onDateSelected(date) }
                        .padding(vertical = 10.dp)
                        .testTag("date_pill_${date.dayNumber}"),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = date.label,
                            fontFamily = OutfitFont,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 11.sp,
                            color = if (isSelected) PrimaryTurfContainer else if (date.isWeekend) SecondaryFloodlightLight else TextMuted
                        )
                        Text(
                            text = date.dayNumber,
                            fontFamily = OutfitFont,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp,
                            color = TextPrimary
                        )
                        Text(
                            text = date.dayName,
                            fontSize = 10.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) PrimaryTurfContainer else if (date.isWeekend) SecondaryFloodlightAmber else TextMuted
                        )
                    }
                }
            }

            // Calendar picker launcher tile
            Box(
                modifier = Modifier
                    .width(76.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(PitchNightBase)
                    .border(1.dp, SecondaryFloodlightAmber.copy(alpha = 0.4f), RoundedCornerShape(14.dp))
                    .clickable { onOpenCalendar() }
                    .padding(vertical = 10.dp)
                    .testTag("date_pill_open_calendar"),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = "Drugi datum",
                        tint = SecondaryFloodlightAmber,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Drugi",
                        fontFamily = OutfitFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = SecondaryFloodlightLight
                    )
                    Text(
                        text = "Datum",
                        fontSize = 10.sp,
                        color = TextMuted
                    )
                }
            }
        }
    }
}

@Composable
private fun TimeSlotsSection(
    slots: List<TimeSlot>,
    selectedSlot: TimeSlot?,
    basePrice: Int,
    onSlotSelected: (TimeSlot) -> Unit
) {
    val daySlots = slots.filter { !it.isNightSlot }
    val nightSlots = slots.filter { it.isNightSlot }

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "3. IZABERITE VREME",
                fontFamily = OutfitFont,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                color = TextMuted,
                letterSpacing = 0.08.sp
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(PrimaryTurfContainer))
                Spacer(modifier = Modifier.width(3.dp))
                Text(text = "Izabrano", fontSize = 10.sp, color = TextMuted)
                Spacer(modifier = Modifier.width(8.dp))
                Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(StatusBookedRed))
                Spacer(modifier = Modifier.width(3.dp))
                Text(text = "Zauzeto", fontSize = 10.sp, color = TextMuted)
            }
        }

        // Dnevni Termini
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Dnevni Termini", fontSize = 12.sp, color = TextMuted)
            Text(text = "Prirodno svetlo", fontSize = 11.sp, color = TextMuted)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            daySlots.forEach { slot ->
                SlotButton(
                    slot = slot,
                    isSelected = selectedSlot?.time == slot.time,
                    priceText = "${String.format("%,d", basePathPrice(basePrice, slot))}.000 RSD",
                    onClick = { onSlotSelected(slot) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Noćni Termini
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.WbIncandescent,
                    contentDescription = null,
                    tint = SecondaryFloodlightAmber,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "Noćni Termini pod LED Reflektorima",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = SecondaryFloodlightLight
                )
            }
            Text(
                text = "Najtraženije",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = SecondaryFloodlightAmber
            )
        }

        // Night slots grid (2 rows of 3)
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                nightSlots.take(3).forEach { slot ->
                    SlotButton(
                        slot = slot,
                        isSelected = selectedSlot?.time == slot.time,
                        priceText = "${String.format("%,d", basePathPrice(basePrice, slot))}.000 RSD",
                        onClick = { onSlotSelected(slot) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                nightSlots.drop(3).forEach { slot ->
                    SlotButton(
                        slot = slot,
                        isSelected = selectedSlot?.time == slot.time,
                        priceText = "${String.format("%,d", basePathPrice(basePrice, slot))}.000 RSD",
                        onClick = { onSlotSelected(slot) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

private fun basePathPrice(base: Int, slot: TimeSlot): Int = base / 1000

@Composable
private fun SlotButton(
    slot: TimeSlot,
    isSelected: Boolean,
    priceText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isOccupied = slot.isOccupied
    val isClosed = slot.isClosed

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(
                when {
                    isSelected -> PrimaryTurfContainer
                    isOccupied || isClosed -> Color(0xFF0A0F0D)
                    else -> PitchCardSurface
                }
            )
            .border(
                width = if (isSelected) 1.5.dp else 1.dp,
                color = when {
                    isSelected -> PrimaryTurfLight
                    isOccupied || isClosed -> Color(0xFF141E18)
                    else -> PitchCardBorder
                },
                shape = RoundedCornerShape(10.dp)
            )
            .clickable(enabled = !isOccupied && !isClosed, onClick = onClick)
            .padding(vertical = 10.dp, horizontal = 4.dp)
            .testTag("slot_button_${slot.time.replace(":", "_")}"),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = slot.time,
                fontFamily = OutfitFont,
                fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Bold,
                fontSize = 15.sp,
                textDecoration = if (isOccupied) TextDecoration.LineThrough else TextDecoration.None,
                color = when {
                    isSelected -> Color(0xFF070B0E)
                    isOccupied -> Color(0xFF4B5E54)
                    isClosed -> Color(0xFF4B5E54)
                    else -> TextPrimary
                }
            )
            Text(
                text = when {
                    isOccupied -> "Zauzeto"
                    isClosed -> "Zatvoreno"
                    else -> priceText
                },
                fontFamily = OutfitFont,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                fontSize = 11.sp,
                color = when {
                    isSelected -> Color(0xFF070B0E).copy(alpha = 0.85f)
                    isOccupied -> StatusBookedRed
                    isClosed -> Color(0xFF4B5E54)
                    else -> TextMuted
                }
            )
        }
    }
}

@Composable
private fun DurationAndExtrasSection(
    selectedDuration: MatchDuration,
    cafeTableSelected: Boolean,
    onDurationSelected: (MatchDuration) -> Unit,
    onCafeTableToggle: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(PitchCardSurface)
            .border(1.dp, PitchCardBorder, RoundedCornerShape(16.dp))
            .padding(14.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                text = "4. TRAJANJE UTAKMICE",
                fontFamily = OutfitFont,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                color = TextMuted,
                letterSpacing = 0.08.sp
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MatchDuration.values().forEach { dur ->
                    val isSelected = selectedDuration == dur

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) PitchCardActive else PitchNightBase)
                            .border(
                                width = if (isSelected) 1.5.dp else 1.dp,
                                color = if (isSelected) PrimaryTurfContainer else PitchCardBorder,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable { onDurationSelected(dur) }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = dur.label,
                                fontFamily = OutfitFont,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 14.sp,
                                color = if (isSelected) PrimaryTurfLight else TextMuted
                            )
                            Text(
                                text = dur.subLabel,
                                fontSize = 10.sp,
                                color = if (isSelected) PrimaryTurfContainer else TextMuted
                            )
                        }
                    }
                }
            }

            HorizontalDivider(color = PitchCardBorder.copy(alpha = 0.6f))

            Text(
                text = "BESPLATNE POGODNOSTI & DODACI",
                fontFamily = OutfitFont,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                color = TextMuted,
                letterSpacing = 0.08.sp
            )

            // Perk 1: Balls & bibs (checked)
            PerkRow(
                title = "⚽ Lopte & Markeri za obe ekipe",
                statusLabel = "BESPLATNO",
                statusColor = PrimaryTurfLight,
                isChecked = true,
                onCheckedChange = null
            )

            // Perk 2: Cafe table (interactive)
            PerkRow(
                title = "☕ Rezervacija stola u kafiću posle termina",
                statusLabel = "Preporučeno",
                statusColor = SecondaryFloodlightAmber,
                isChecked = cafeTableSelected,
                onCheckedChange = onCafeTableToggle
            )

            // Perk 3: Locker & showers (checked)
            PerkRow(
                title = "🚿 Korišćenje svlačionice i tuševa",
                statusLabel = "UKLJUČENO",
                statusColor = PrimaryTurfLight,
                isChecked = true,
                onCheckedChange = null
            )
        }
    }
}

@Composable
private fun PerkRow(
    title: String,
    statusLabel: String,
    statusColor: Color,
    isChecked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(PitchNightBase)
            .border(1.dp, PitchCardBorder, RoundedCornerShape(8.dp))
            .clickable(enabled = onCheckedChange != null) {
                onCheckedChange?.invoke(!isChecked)
            }
            .padding(horizontal = 10.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = onCheckedChange,
                enabled = onCheckedChange != null,
                colors = CheckboxDefaults.colors(
                    checkedColor = PrimaryTurfContainer,
                    uncheckedColor = PitchCardBorder,
                    checkmarkColor = Color(0xFF070B0E)
                ),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary
            )
        }

        Text(
            text = statusLabel,
            fontFamily = OutfitFont,
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp,
            color = statusColor,
            letterSpacing = 0.05.sp
        )
    }
}

@Composable
private fun OtherServicesSection(
    onSchoolClick: () -> Unit,
    onBirthdayClick: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "OSTALE USLUGE FK SAVA 45",
            fontFamily = OutfitFont,
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp,
            color = TextMuted,
            letterSpacing = 0.08.sp
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Card 1: Škola fudbala
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(14.dp))
                    .background(PitchCardSurface)
                    .border(1.dp, PitchCardBorder, RoundedCornerShape(14.dp))
                    .clickable(onClick = onSchoolClick)
                    .padding(12.dp)
                    .testTag("card_skola_fudbala")
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(PrimaryTurfContainer.copy(alpha = 0.12f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.School, contentDescription = null, tint = PrimaryTurfLight, modifier = Modifier.size(18.dp))
                        }
                        Text(
                            text = "100% BESPLATNO",
                            fontFamily = OutfitFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 9.sp,
                            color = PrimaryTurfLight,
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(PrimaryTurfContainer.copy(alpha = 0.15f))
                                .padding(horizontal = 5.dp, vertical = 2.dp)
                        )
                    }

                    Text(
                        text = "Škola Fudbala (5–15 god)",
                        fontFamily = OutfitFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = TextPrimary
                    )

                    Text(
                        text = "UEFA licencirani treneri. Prvi probni trening gratis.",
                        fontSize = 11.sp,
                        color = TextMuted,
                        lineHeight = 15.sp
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Text(
                            text = "Upiši Dete",
                            fontFamily = OutfitFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = PrimaryTurfLight
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = PrimaryTurfLight, modifier = Modifier.size(13.dp))
                    }
                }
            }

            // Card 2: Dečiji Rođendani
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(14.dp))
                    .background(PitchCardSurface)
                    .border(1.dp, PitchCardBorder, RoundedCornerShape(14.dp))
                    .clickable(onClick = onBirthdayClick)
                    .padding(12.dp)
                    .testTag("card_deciji_rodjendani")
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(SecondaryFloodlightAmber.copy(alpha = 0.12f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.Cake, contentDescription = null, tint = SecondaryFloodlightAmber, modifier = Modifier.size(18.dp))
                        }
                        Text(
                            text = "ALL-INCLUSIVE",
                            fontFamily = OutfitFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 9.sp,
                            color = SecondaryFloodlightLight,
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(SecondaryFloodlightAmber.copy(alpha = 0.15f))
                                .padding(horizontal = 5.dp, vertical = 2.dp)
                        )
                    }

                    Text(
                        text = "Dečiji Rođendani & Balkon",
                        fontFamily = OutfitFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = TextPrimary
                    )

                    Text(
                        text = "Animatori, kafić sa staklom i prostrani gornji balkon.",
                        fontSize = 11.sp,
                        color = TextMuted,
                        lineHeight = 15.sp
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Text(
                            text = "Saznaj Više",
                            fontFamily = OutfitFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = SecondaryFloodlightLight
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = SecondaryFloodlightLight, modifier = Modifier.size(13.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun TestimonialCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(PitchCardSurface)
            .border(1.dp, PitchCardBorder, RoundedCornerShape(16.dp))
            .padding(14.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(PitchCardActive)
                            .border(1.dp, PitchCardBorder, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "MP",
                            fontFamily = OutfitFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = PrimaryTurfLight
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Marko Petrović",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        )
                        Text(
                            text = "Stalni Mesečni Termin (Sreda)",
                            fontSize = 11.sp,
                            color = TextMuted
                        )
                    }
                }

                Row {
                    repeat(5) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = SecondaryFloodlightAmber,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }

            Text(
                text = "\"Otkako je teren renoviran sa novom veštačkom travom, lopta ide savršeno a noćni reflektori pružaju osećaj kao na pravom stadionu. Druženje u kafiću posle je vrhunsko!\"",
                fontSize = 12.sp,
                color = TextMuted,
                lineHeight = 17.sp,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
            )
        }
    }
}

@Composable
private fun StickyBookingTray(
    summaryMeta: String,
    totalPrice: String,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(PitchNightBase.copy(alpha = 0.98f))
            .border(1.dp, PitchCardBorder)
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = summaryMeta,
                    fontSize = 11.sp,
                    color = TextMuted,
                    maxLines = 1
                )
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = totalPrice,
                        fontFamily = OutfitFont,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 24.sp,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "RSD",
                        fontFamily = OutfitFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = PrimaryTurfContainer
                    )
                }
            }

            Button(
                onClick = onConfirmClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryTurfContainer,
                    contentColor = Color(0xFF070B0E)
                ),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                modifier = Modifier
                    .height(44.dp)
                    .testTag("btn_sticky_whatsapp_confirm")
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "WhatsApp ⚡",
                    fontFamily = OutfitFont,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp
                )
            }
        }
    }
}
