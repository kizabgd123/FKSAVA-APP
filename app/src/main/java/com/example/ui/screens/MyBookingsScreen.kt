package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BookingReservation
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
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun MyBookingsScreen(
    viewModel: BookingViewModel,
    modifier: Modifier = Modifier
) {
    val reservations by viewModel.reservations.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PitchNightBase)
    ) {
        SharedTopBar(
            onMenuClick = { viewModel.navigateTo(AppScreen.REZERVISI) }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .padding(bottom = 74.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Moji Termini",
                        fontFamily = OutfitFont,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 24.sp,
                        color = TextPrimary
                    )
                    Text(
                        text = "Pregled zakazanih i aktivnih mečeva",
                        fontSize = 12.sp,
                        color = TextMuted
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(PrimaryTurfContainer.copy(alpha = 0.12f))
                        .border(1.dp, PrimaryTurfContainer.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "${reservations.size} Termina",
                        fontFamily = OutfitFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        color = PrimaryTurfLight
                    )
                }
            }

            if (reservations.isEmpty()) {
                // Empty state
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(PitchCardSurface)
                                .border(1.dp, PitchCardBorder, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.SportsSoccer,
                                contentDescription = null,
                                tint = TextMuted,
                                modifier = Modifier.size(32.dp)
                            )
                        }

                        Text(
                            text = "Nemate aktivnih rezervacija",
                            fontFamily = OutfitFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = TextPrimary
                        )

                        Text(
                            text = "Izaberite podlogu i termin za vašu ekipu u nekoliko klikova.",
                            fontSize = 13.sp,
                            color = TextMuted
                        )

                        Button(
                            onClick = { viewModel.navigateTo(AppScreen.REZERVISI) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PrimaryTurfContainer,
                                contentColor = Color(0xFF070B0E)
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Text(
                                text = "Rezerviši Termin Sada",
                                fontFamily = OutfitFont,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(reservations, key = { it.id }) { item ->
                        ReservationCard(
                            reservation = item,
                            onShareTeam = {
                                val message = """
                                    ⚽ Ekipa, zakazan je termin na FK SAVA 45!
                                    - Teren: ${item.pitchType.title}
                                    - Datum: ${item.dateString}
                                    - Vreme: ${item.timeString}
                                    - Kapiten: ${item.captainName}
                                    - Lokacija: Dr Ivana Ribara 115, Blok 45
                                    Vidimo se 15 minuta ranije u svlačionici!
                                """.trimIndent()
                                val encoded = URLEncoder.encode(message, StandardCharsets.UTF_8.toString())
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/?text=$encoded"))
                                try {
                                    context.startActivity(intent)
                                } catch (e: Exception) {}
                            },
                            onCancel = { viewModel.cancelBooking(item.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ReservationCard(
    reservation: BookingReservation,
    onShareTeam: () -> Unit,
    onCancel: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(PitchCardSurface)
            .border(1.dp, PitchCardBorder, RoundedCornerShape(16.dp))
            .padding(14.dp)
            .testTag("reservation_card_${reservation.id}")
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            // Header status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = PrimaryTurfLight,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = reservation.status,
                        fontFamily = OutfitFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        color = PrimaryTurfLight,
                        letterSpacing = 0.06.sp
                    )
                }

                Text(
                    text = "${String.format("%,d", reservation.totalPriceRsd).replace(',', '.')} RSD",
                    fontFamily = OutfitFont,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 17.sp,
                    color = PrimaryTurfContainer
                )
            }

            Text(
                text = "${reservation.pitchType.title} (${reservation.pitchType.subtitle.takeWhile { it != '·' }})",
                fontFamily = OutfitFont,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = TextPrimary
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = null,
                    tint = SecondaryFloodlightAmber,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "${reservation.dateString}  •  ${reservation.timeString}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = SecondaryFloodlightLight
                )
            }

            Text(
                text = "Kapiten: ${reservation.captainName} (${reservation.phoneNumber})",
                fontSize = 12.sp,
                color = TextMuted
            )

            if (reservation.cafeTableReserved) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.LocalCafe, contentDescription = null, tint = PrimaryTurfLight, modifier = Modifier.size(13.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Sto u kafiću rezervisan za ekipu", fontSize = 11.sp, color = PrimaryTurfLight)
                }
            }

            if (reservation.note.isNotBlank()) {
                Text(
                    text = "Napomena: ${reservation.note}",
                    fontSize = 11.sp,
                    color = TextMuted
                )
            }

            HorizontalDivider(color = PitchCardBorder.copy(alpha = 0.6f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Share to WhatsApp team
                Button(
                    onClick = onShareTeam,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PitchCardActive,
                        contentColor = TextPrimary
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                        .border(1.dp, PrimaryTurfContainer.copy(alpha = 0.3f), RoundedCornerShape(10.dp))
                ) {
                    Icon(imageVector = Icons.Default.Share, contentDescription = null, tint = PrimaryTurfLight, modifier = Modifier.size(15.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Podeli sa ekipom",
                        fontFamily = OutfitFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }

                // Cancel button
                Button(
                    onClick = onCancel,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PitchNightBase,
                        contentColor = StatusBookedRed
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .height(40.dp)
                        .border(1.dp, PitchCardBorder, RoundedCornerShape(10.dp))
                ) {
                    Icon(imageVector = Icons.Default.Cancel, contentDescription = null, modifier = Modifier.size(15.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Otkaži", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}
