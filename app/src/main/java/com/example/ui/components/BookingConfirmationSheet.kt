package com.example.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhoneIphone
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.OutfitFont
import com.example.ui.theme.PitchCardActive
import com.example.ui.theme.PitchCardBorder
import com.example.ui.theme.PitchCardSurface
import com.example.ui.theme.PitchNightBase
import com.example.ui.theme.PrimaryTurfContainer
import com.example.ui.theme.PrimaryTurfLight
import com.example.ui.theme.SecondaryFloodlightAmber
import com.example.ui.theme.SecondaryFloodlightLight
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.viewmodel.BookingUiState
import com.example.ui.viewmodel.BookingViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun BookingConfirmationSheet(
    uiState: BookingUiState,
    viewModel: BookingViewModel,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = PitchCardSurface,
        scrimColor = Color.Black.copy(alpha = 0.75f),
        dragHandle = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 44.dp, height = 5.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(PitchCardBorder.copy(alpha = 0.9f))
                )
            }
        },
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
                .navigationBarsPadding()
                .verticalScroll(scrollState)
        ) {
            // Header Row: Badge, Title & Close
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .clip(CircleShape)
                                .background(PrimaryTurfContainer)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "DIREKTNA REZERVACIJA",
                            fontFamily = OutfitFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            color = PrimaryTurfLight,
                            letterSpacing = 0.08.sp
                        )
                    }
                    Text(
                        text = "Potvrda Rezervacije",
                        fontFamily = OutfitFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = TextPrimary,
                        letterSpacing = (-0.01).sp,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                    Text(
                        text = "${uiState.selectedPitch.title} · ${uiState.selectedPitch.subtitle}",
                        fontSize = 12.sp,
                        color = TextMuted,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }

                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(PitchCardActive)
                        .border(1.dp, PitchCardBorder, CircleShape)
                        .testTag("btn_close_confirmation")
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Zatvori",
                        tint = TextMuted,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // Summary Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(PitchNightBase.copy(alpha = 0.9f))
                    .border(1.dp, PitchCardBorder, RoundedCornerShape(16.dp))
                    .padding(14.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(PitchCardActive)
                                    .border(1.dp, PitchCardBorder, RoundedCornerShape(10.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.SportsSoccer,
                                    contentDescription = null,
                                    tint = PrimaryTurfLight,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "IZABRANI TERMIN",
                                    fontFamily = OutfitFont,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp,
                                    color = TextMuted,
                                    letterSpacing = 0.05.sp
                                )
                                Text(
                                    text = uiState.selectedDate?.fullDateString ?: "Sutra, 16. Maj",
                                    fontFamily = OutfitFont,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp,
                                    color = TextPrimary
                                )
                            }
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "${uiState.selectedSlot?.time ?: "20:00"} - ${(uiState.selectedSlot?.time ?: "20:00").take(2).toIntOrNull()?.plus(1)}:00",
                                fontFamily = OutfitFont,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 17.sp,
                                color = PrimaryTurfContainer
                            )
                            Text(
                                text = "(${uiState.selectedDuration.minutes} min)",
                                fontSize = 11.sp,
                                color = TextMuted
                            )
                        }
                    }

                    // Night slot alert
                    if (uiState.selectedSlot?.isNightSlot == true) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(SecondaryFloodlightAmber.copy(alpha = 0.12f))
                                .border(1.dp, SecondaryFloodlightAmber.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "🌙", fontSize = 14.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Noćni termin pod LED reflektorima",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = SecondaryFloodlightLight
                            )
                        }
                    }

                    // Location line
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = PrimaryTurfLight,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Dr Ivana Ribara 115, Blok 45, Novi Beograd",
                            fontSize = 12.sp,
                            color = TextMuted
                        )
                    }

                    // Perks
                    Spacer(modifier = Modifier.height(10.dp))
                    HorizontalDivider(color = PitchCardBorder.copy(alpha = 0.6f))
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "UKLJUČENO U TERMIN BEZ DOPLATE:",
                        fontFamily = OutfitFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        color = TextMuted,
                        letterSpacing = 0.05.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        PerkPill(text = "⚽ Lopte & Markeri (Besplatno)")
                        PerkPill(text = "🚿 Topli tuševi & svlačionica")
                        if (uiState.cafeTableSelected) {
                            PerkPill(text = "☕ Sto u kafiću rezervisan")
                        }
                    }
                }
            }

            // Input Fields Section
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "PODACI ZA REZERVACIJU",
                    fontFamily = OutfitFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    color = TextMuted,
                    letterSpacing = 0.06.sp
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = PrimaryTurfLight,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = "Siguran unos",
                        fontSize = 11.sp,
                        color = PrimaryTurfLight,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Captain Name Input
            Text(
                text = "Ime i prezime kapitena / ekipe *",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = uiState.captainName,
                onValueChange = { viewModel.updateCaptainName(it) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = TextMuted,
                        modifier = Modifier.size(18.dp)
                    )
                },
                placeholder = { Text("Unesite ime ili ekipu", color = TextMuted) },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = PitchNightBase,
                    unfocusedContainerColor = PitchNightBase,
                    focusedBorderColor = PrimaryTurfContainer,
                    unfocusedBorderColor = PitchCardBorder,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_captain_name")
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Phone Input
            Text(
                text = "Broj telefona (za WhatsApp potvrdu) *",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = uiState.phoneNumber,
                onValueChange = { viewModel.updatePhoneNumber(it) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.PhoneIphone,
                        contentDescription = null,
                        tint = TextMuted,
                        modifier = Modifier.size(18.dp)
                    )
                },
                trailingIcon = {
                    Text(
                        text = "WhatsApp aktivan",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = PrimaryTurfContainer,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                },
                placeholder = { Text("+381 64 ...", color = TextMuted) },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = PitchNightBase,
                    unfocusedContainerColor = PitchNightBase,
                    focusedBorderColor = PrimaryTurfContainer,
                    unfocusedBorderColor = PitchCardBorder,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_phone_number")
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Notes Input
            Text(
                text = "Napomena / Poseban zahtev (opciono)",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = uiState.bookingNote,
                onValueChange = { viewModel.updateBookingNote(it) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.ChatBubble,
                        contentDescription = null,
                        tint = TextMuted,
                        modifier = Modifier.size(18.dp)
                    )
                },
                placeholder = { Text("npr. Treba nam 2 kompleta markera...", color = TextMuted) },
                maxLines = 3,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = PitchNightBase,
                    unfocusedContainerColor = PitchNightBase,
                    focusedBorderColor = PrimaryTurfContainer,
                    unfocusedBorderColor = PitchCardBorder,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_booking_note")
            )

            // Price Breakdown Card
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(PitchNightBase)
                    .border(1.dp, PitchCardBorder, RoundedCornerShape(16.dp))
                    .padding(14.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "PREGLED TROŠKOVA",
                        fontFamily = OutfitFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        color = TextMuted,
                        letterSpacing = 0.06.sp
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Cena termina (${uiState.selectedDuration.minutes} min)",
                            fontSize = 13.sp,
                            color = TextMuted
                        )
                        Text(
                            text = "${uiState.formattedPriceRsd} RSD",
                            fontFamily = OutfitFont,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = TextPrimary
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Oprema i rekviziti", fontSize = 13.sp, color = TextMuted)
                        Text(
                            text = "0 RSD (Uključeno)",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = PrimaryTurfLight
                        )
                    }

                    if (uiState.cafeTableSelected) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Rezervacija stola u kafiću", fontSize = 13.sp, color = TextMuted)
                            Text(
                                text = "Gratis",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = PrimaryTurfLight
                            )
                        }
                    }

                    HorizontalDivider(color = PitchCardBorder)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = "Ukupno za plaćanje:",
                            fontFamily = OutfitFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = TextPrimary
                        )
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = uiState.formattedPriceRsd,
                                fontFamily = OutfitFont,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 24.sp,
                                color = PrimaryTurfContainer
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "RSD",
                                fontFamily = OutfitFont,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = TextMuted
                            )
                        }
                    }

                    // Payment on site notice
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(PitchCardSurface)
                            .border(1.dp, PitchCardBorder, RoundedCornerShape(8.dp))
                            .padding(8.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Default.Payments,
                            contentDescription = null,
                            tint = SecondaryFloodlightAmber,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "Plaćanje na licu mesta",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextPrimary
                            )
                            Text(
                                text = "U kafiću sportskog centra pre početka vašeg termina (gotovina ili kartice).",
                                fontSize = 11.sp,
                                color = TextMuted
                            )
                        }
                    }

                    // Cancellation
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = PrimaryTurfLight,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Besplatno otkazivanje do 4h pre termina. Potvrda stiže na WhatsApp.",
                            fontSize = 11.sp,
                            color = TextMuted
                        )
                    }
                }
            }

            // Trust indicators
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "⚡ Potvrda u roku od 5 min", fontSize = 11.sp, color = PrimaryTurfLight, fontWeight = FontWeight.Medium)
                Text(text = "  •  ", fontSize = 11.sp, color = TextMuted)
                Text(text = "⭐ 5.0 Google Ocena (120+ recenzija)", fontSize = 11.sp, color = SecondaryFloodlightAmber, fontWeight = FontWeight.Medium)
            }

            // Action Buttons
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val reservation = viewModel.confirmBooking()
                    val waUrl = viewModel.getWhatsAppBookingUrl()
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(waUrl))
                    try {
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        // WhatsApp might not be installed in emulator
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryTurfContainer,
                    contentColor = Color(0xFF070B0E)
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("btn_confirm_send_whatsapp")
            ) {
                Text(
                    text = "Potvrdi i Pošalji na WhatsApp 📲",
                    fontFamily = OutfitFont,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:0631110407"))
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = PitchNightBase,
                    contentColor = TextPrimary
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .border(1.dp, PitchCardBorder, RoundedCornerShape(12.dp))
                    .testTag("btn_call_manager_confirm")
            ) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = null,
                    tint = SecondaryFloodlightAmber,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Pozovi direktno menadžera (063 111 0407)",
                    fontFamily = OutfitFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun PerkPill(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(PitchCardSurface)
            .border(1.dp, PitchCardBorder, RoundedCornerShape(20.dp))
            .padding(horizontal = 9.dp, vertical = 4.dp)
    ) {
        Text(text = text, fontSize = 11.sp, color = TextPrimary, fontWeight = FontWeight.Medium)
    }
}
