package com.example.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.BatteryFull
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    viewModel: BookingViewModel,
    onNavigateToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scaleAnim = remember { Animatable(0.75f) }
    val alphaAnim = remember { Animatable(0f) }
    val progressAnim = remember { Animatable(0f) }
    val auraScaleAnim = remember { Animatable(0.85f) }

    LaunchedEffect(Unit) {
        // Logo Entrance
        scaleAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1800, easing = FastOutSlowInEasing)
        )
    }

    LaunchedEffect(Unit) {
        alphaAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1200, easing = LinearEasing)
        )
    }

    LaunchedEffect(Unit) {
        auraScaleAnim.animateTo(
            targetValue = 1.15f,
            animationSpec = infiniteRepeatable(
                animation = tween(2200, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    LaunchedEffect(Unit) {
        progressAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 3200, easing = LinearEasing)
        )
        delay(300)
        onNavigateToLogin()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PitchNightBase)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        // Subtle pitch line grid background
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Status & Quick Stage Switcher
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
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

                // Stage pill indicator
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(PitchCardSurface)
                        .border(1.dp, PitchCardBorder, RoundedCornerShape(20.dp))
                        .padding(horizontal = 6.dp, vertical = 3.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Uvod",
                        fontFamily = OutfitFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        color = PrimaryTurfLight,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(PitchCardActive)
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Prijava",
                        fontFamily = OutfitFont,
                        fontSize = 11.sp,
                        color = TextMuted,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { onNavigateToLogin() }
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(imageVector = Icons.Default.SignalCellularAlt, contentDescription = null, tint = TextMuted, modifier = Modifier.size(14.dp))
                    Icon(imageVector = Icons.Default.Wifi, contentDescription = null, tint = TextMuted, modifier = Modifier.size(14.dp))
                    Icon(imageVector = Icons.Default.BatteryFull, contentDescription = null, tint = TextMuted, modifier = Modifier.size(14.dp))
                }
            }

            // Center Club Emblem & Titles
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(alphaAnim.value)
            ) {
                // Top Club Badge Pill
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(PitchCardSurface)
                        .border(1.dp, PitchCardBorder, RoundedCornerShape(20.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(SecondaryFloodlightAmber)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "NOVI BEOGRAD · BLOK 45",
                        fontFamily = OutfitFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        color = SecondaryFloodlightLight,
                        letterSpacing = 0.08.sp
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                // Emblem with Golden Backlight Aura
                Box(
                    modifier = Modifier
                        .size(240.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Pulsing golden aura glow
                    Box(
                        modifier = Modifier
                            .size(220.dp)
                            .scale(auraScaleAnim.value)
                            .blur(40.dp)
                            .background(
                                Brush.radialGradient(
                                    colors = listOf(
                                        SecondaryFloodlightAmber.copy(alpha = 0.35f),
                                        PrimaryTurfContainer.copy(alpha = 0.15f),
                                        Color.Transparent
                                    )
                                )
                            )
                    )

                    Image(
                        painter = painterResource(id = R.drawable.ic_fk_sava_crest),
                        contentDescription = "FK SAVA 45 Zvanični Grb",
                        modifier = Modifier
                            .size(200.dp)
                            .scale(scaleAnim.value)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Club Name
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "ФК САВА ",
                        fontFamily = OutfitFont,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 32.sp,
                        color = TextPrimary,
                        letterSpacing = (-0.02).sp
                    )
                    Text(
                        text = "45",
                        fontFamily = OutfitFont,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 32.sp,
                        color = SecondaryFloodlightLight,
                        letterSpacing = (-0.02).sp
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "REDOVNI I BALON TERMINI",
                    fontFamily = OutfitFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = PrimaryTurfContainer,
                    letterSpacing = 0.05.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Noćni reflektori, veštačka trava najnovije\ngeneracije i klupski kafić",
                    fontSize = 13.sp,
                    color = TextMuted,
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp
                )
            }

            // Bottom Progress & Continue Button
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Progress status
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .padding(bottom = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(PrimaryTurfContainer)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Učitavanje terena...",
                            fontSize = 11.sp,
                            color = TextMuted
                        )
                    }
                    Text(
                        text = "Blok 45",
                        fontFamily = OutfitFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        color = SecondaryFloodlightLight
                    )
                }

                LinearProgressIndicator(
                    progress = { progressAnim.value },
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(5.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = PrimaryTurfContainer,
                    trackColor = PitchCardBorder
                )

                Spacer(modifier = Modifier.height(18.dp))

                Button(
                    onClick = onNavigateToLogin,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PitchCardSurface,
                        contentColor = TextPrimary
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.88f)
                        .height(50.dp)
                        .border(1.dp, PitchCardBorder, RoundedCornerShape(12.dp))
                        .testTag("btn_proceed_to_login")
                ) {
                    Text(
                        text = "Pređi na prijavu",
                        fontFamily = OutfitFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
