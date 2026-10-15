package com.example.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.BookingReservation
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
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun BookingSuccessVoucherDialog(
    reservation: BookingReservation,
    onDismiss: () -> Unit,
    onViewMyBookings: () -> Unit
) {
    val context = LocalContext.current

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(PitchCardSurface)
                .border(1.dp, PitchCardBorder, RoundedCornerShape(24.dp))
                .padding(20.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Header with close button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.size(24.dp))

                    // Green Success Badge
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(PrimaryTurfContainer)
                            .border(2.dp, PrimaryTurfLight, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color(0xFF070B0E),
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Zatvori",
                            tint = TextMuted,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Text(
                    text = "Rezervacija Uspešna!",
                    fontFamily = OutfitFont,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 22.sp,
                    color = TextPrimary
                )

                Text(
                    text = "Vaš termin na FK SAVA 45 je evidentiran. Potvrda je spremna za slanje ekipi.",
                    fontSize = 12.sp,
                    color = TextMuted,
                    textAlign = TextAlign.Center,
                    lineHeight = 16.sp
                )

                // Digital Voucher Ticket Box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(PitchNightBase)
                        .border(1.dp, PitchCardBorder, RoundedCornerShape(16.dp))
                        .padding(14.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "KOD TERMINA",
                                fontFamily = OutfitFont,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp,
                                color = TextMuted,
                                letterSpacing = 0.08.sp
                            )
                            Text(
                                text = "#${reservation.id}",
                                fontFamily = OutfitFont,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 13.sp,
                                color = SecondaryFloodlightAmber
                            )
                        }

                        HorizontalDivider(color = PitchCardBorder.copy(alpha = 0.5f))

                        VoucherItemRow(label = "Teren", value = reservation.pitchType.title)
                        VoucherItemRow(label = "Datum", value = reservation.dateString)
                        VoucherItemRow(label = "Vreme", value = reservation.timeString)
                        VoucherItemRow(label = "Kapiten", value = reservation.captainName)

                        HorizontalDivider(color = PitchCardBorder.copy(alpha = 0.5f))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Text(
                                text = "Ukupno (na licu mesta):",
                                fontSize = 12.sp,
                                color = TextMuted
                            )
                            Text(
                                text = "${String.format("%,d", reservation.totalPriceRsd).replace(',', '.')} RSD",
                                fontFamily = OutfitFont,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 17.sp,
                                color = PrimaryTurfContainer
                            )
                        }
                    }
                }

                // CTA: Share to WhatsApp
                Button(
                    onClick = {
                        val message = """
                            ⚽ Ekipa, potvrđen je termin na FK SAVA 45!
                            - Kod: #${reservation.id}
                            - Teren: ${reservation.pitchType.title}
                            - Datum: ${reservation.dateString}
                            - Vreme: ${reservation.timeString}
                            - Lokacija: Dr Ivana Ribara 115, Blok 45
                            Vidimo se u svlačionici!
                        """.trimIndent()
                        val encoded = URLEncoder.encode(message, StandardCharsets.UTF_8.toString())
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/?text=$encoded"))
                        try {
                            context.startActivity(intent)
                        } catch (e: Exception) {}
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryTurfContainer,
                        contentColor = Color(0xFF070B0E)
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("btn_voucher_whatsapp_share")
                ) {
                    Icon(imageVector = Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Podeli sa ekipom na WhatsApp",
                        fontFamily = OutfitFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }

                // CTA: Go to My Bookings
                Button(
                    onClick = {
                        onDismiss()
                        onViewMyBookings()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PitchCardActive,
                        contentColor = TextPrimary
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .border(1.dp, PitchCardBorder, RoundedCornerShape(12.dp))
                        .testTag("btn_voucher_view_bookings")
                ) {
                    Icon(
                        imageVector = Icons.Default.ConfirmationNumber,
                        contentDescription = null,
                        tint = PrimaryTurfLight,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Otvori Moji Termini",
                        fontFamily = OutfitFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun VoucherItemRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 12.sp, color = TextMuted)
        Text(
            text = value,
            fontFamily = OutfitFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp,
            color = TextPrimary
        )
    }
}
