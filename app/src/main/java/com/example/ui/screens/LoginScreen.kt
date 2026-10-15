package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Sms
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
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
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.BookingViewModel

@Composable
fun LoginScreen(
    viewModel: BookingViewModel,
    onNavigateToSplash: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PitchNightBase)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header: Top Status & Quick Splash Re-trigger
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "20:45",
                        fontFamily = OutfitFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(PrimaryTurfContainer)
                    )
                }

                // Replay slow splash animation
                IconButton(
                    onClick = onNavigateToSplash,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(PitchCardSurface)
                        .border(1.dp, PitchCardBorder, CircleShape)
                        .testTag("btn_replay_splash")
                ) {
                    Icon(
                        imageVector = Icons.Default.Replay,
                        contentDescription = "Ponovi uvodnu animaciju",
                        tint = TextMuted,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // Center Branding & Form
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Crest in stylish box
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(PitchCardSurface)
                        .border(1.dp, PitchCardBorder, RoundedCornerShape(20.dp))
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_fk_sava_crest),
                        contentDescription = "FK SAVA 45 Logo",
                        modifier = Modifier.size(64.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Location badge
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(PitchCardActive)
                        .border(1.dp, PitchCardBorder, RoundedCornerShape(16.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(SecondaryFloodlightAmber)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "NOVI BEOGRAD · BLOK 45",
                        fontFamily = OutfitFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        color = SecondaryFloodlightLight,
                        letterSpacing = 0.06.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "FK SAVA 45",
                    fontFamily = OutfitFont,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 24.sp,
                    color = TextPrimary
                )

                Text(
                    text = "Prijava kapitena",
                    fontFamily = OutfitFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = PrimaryTurfLight,
                    modifier = Modifier.padding(top = 2.dp)
                )

                Text(
                    text = "Rezervišite termine u Bloku 45",
                    fontSize = 13.sp,
                    color = TextMuted,
                    modifier = Modifier.padding(top = 2.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Mode Switcher: Email vs Telefon
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(PitchCardSurface)
                        .border(1.dp, PitchCardBorder, RoundedCornerShape(12.dp))
                        .padding(4.dp)
                ) {
                    val isEmail = uiState.loginMode == "email"

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isEmail) PitchCardActive else Color.Transparent)
                            .border(
                                width = if (isEmail) 1.dp else 0.dp,
                                color = if (isEmail) PitchCardBorder else Color.Transparent,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { viewModel.setLoginMode("email") }
                            .padding(vertical = 10.dp)
                            .testTag("tab_login_email"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Email & Lozinka",
                            fontFamily = OutfitFont,
                            fontWeight = if (isEmail) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 13.sp,
                            color = if (isEmail) TextPrimary else TextMuted
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (!isEmail) PitchCardActive else Color.Transparent)
                            .border(
                                width = if (!isEmail) 1.dp else 0.dp,
                                color = if (!isEmail) PitchCardBorder else Color.Transparent,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { viewModel.setLoginMode("phone") }
                            .padding(vertical = 10.dp)
                            .testTag("tab_login_phone"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Broj telefona",
                            fontFamily = OutfitFont,
                            fontWeight = if (!isEmail) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 13.sp,
                            color = if (!isEmail) TextPrimary else TextMuted
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Form based on active tab
                if (uiState.loginMode == "email") {
                    // EMAIL INPUT
                    Text(
                        text = "EMAIL ADRESA",
                        fontFamily = OutfitFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        color = TextMuted,
                        letterSpacing = 0.05.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = uiState.loginEmail,
                        onValueChange = { viewModel.updateLoginEmail(it) },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Mail, contentDescription = null, tint = TextMuted, modifier = Modifier.size(18.dp))
                        },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = PitchNightBase,
                            unfocusedContainerColor = PitchNightBase,
                            focusedBorderColor = PrimaryTurfContainer,
                            unfocusedBorderColor = PitchCardBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_login_email")
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // PASSWORD INPUT
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "LOZINKA",
                            fontFamily = OutfitFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            color = TextMuted,
                            letterSpacing = 0.05.sp
                        )
                        Text(
                            text = "Zaboravljena lozinka?",
                            fontSize = 12.sp,
                            color = SecondaryFloodlightLight,
                            modifier = Modifier.clickable {
                                viewModel.performLogin("Lozinka je resetovana. Dobrodošli!")
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = uiState.loginPassword,
                        onValueChange = { viewModel.updateLoginPassword(it) },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = TextMuted, modifier = Modifier.size(18.dp))
                        },
                        trailingIcon = {
                            IconButton(onClick = { viewModel.togglePasswordVisibility() }) {
                                Icon(
                                    imageVector = if (uiState.isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = "Prikaži lozinku",
                                    tint = TextMuted,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        },
                        visualTransformation = if (uiState.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = PitchNightBase,
                            unfocusedContainerColor = PitchNightBase,
                            focusedBorderColor = PrimaryTurfContainer,
                            unfocusedBorderColor = PitchCardBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_login_password")
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Primary Button: Prijavi se (Bright Emerald)
                    Button(
                        onClick = { viewModel.performLogin() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryTurfContainer,
                            contentColor = Color(0xFF070B0E)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("btn_submit_login")
                    ) {
                        Icon(imageVector = Icons.Default.Login, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Prijavi se",
                            fontFamily = OutfitFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                } else {
                    // PHONE INPUT
                    Text(
                        text = "BROJ MOBILNOG TELEFONA",
                        fontFamily = OutfitFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        color = TextMuted,
                        letterSpacing = 0.05.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = uiState.loginPhone,
                        onValueChange = { viewModel.updateLoginPhone(it) },
                        leadingIcon = {
                            Text(
                                text = "🇷🇸 +381",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextMuted,
                                modifier = Modifier.padding(start = 12.dp, end = 4.dp)
                            )
                        },
                        placeholder = { Text("64 123 4567", color = TextMuted) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = PitchNightBase,
                            unfocusedContainerColor = PitchNightBase,
                            focusedBorderColor = PrimaryTurfContainer,
                            unfocusedBorderColor = PitchCardBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_login_phone")
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { viewModel.performLogin("SMS kod je uspešno poslat!") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryTurfContainer,
                            contentColor = Color(0xFF070B0E)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("btn_submit_sms")
                    ) {
                        Icon(imageVector = Icons.Default.Sms, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Pošalji SMS kod",
                            fontFamily = OutfitFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }

                // Divider ILI
                Spacer(modifier = Modifier.height(18.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(modifier = Modifier.weight(1f), color = PitchCardBorder)
                    Text(
                        text = "ILI",
                        fontFamily = OutfitFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        color = TextMuted,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                    HorizontalDivider(modifier = Modifier.weight(1f), color = PitchCardBorder)
                }
                Spacer(modifier = Modifier.height(18.dp))

                // WhatsApp Booking
                Button(
                    onClick = {
                        val waUrl = viewModel.getWhatsAppBookingUrl()
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(waUrl))
                        try {
                            context.startActivity(intent)
                        } catch (e: Exception) {}
                        viewModel.continueAsGuest()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PitchCardSurface,
                        contentColor = TextPrimary
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .border(1.dp, PrimaryTurfContainer.copy(alpha = 0.35f), RoundedCornerShape(12.dp))
                        .testTag("btn_login_whatsapp")
                ) {
                    Icon(
                        imageVector = Icons.Default.Chat,
                        contentDescription = null,
                        tint = PrimaryTurfContainer,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Rezerviši putem WhatsApp-a",
                        fontFamily = OutfitFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Continue as guest
                Button(
                    onClick = { viewModel.continueAsGuest() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = TextMuted
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .border(1.dp, PitchCardBorder, RoundedCornerShape(12.dp))
                        .testTag("btn_login_guest")
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = null,
                        tint = TextMuted,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Nastavi kao Gost (Pregled slobodnih termina)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextMuted
                    )
                }
            }

            // Footer Info
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HorizontalDivider(color = PitchCardBorder.copy(alpha = 0.6f))
                Spacer(modifier = Modifier.height(10.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Nemaš registrovani ekipu? ", fontSize = 12.sp, color = TextMuted)
                    Text(
                        text = "Registruj se",
                        fontFamily = OutfitFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = PrimaryTurfLight,
                        modifier = Modifier.clickable {
                            viewModel.performLogin("Profil kreiran! Dobrodošli u FK Sava 45.")
                        }
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "📍 Dr Ivana Ribara 115, Blok 45  •  📞 063 111 0407",
                    fontSize = 11.sp,
                    color = TextMuted.copy(alpha = 0.8f)
                )
            }
        }
    }
}
