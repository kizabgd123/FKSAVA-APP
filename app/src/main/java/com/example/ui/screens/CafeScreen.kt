package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PhoneInTalk
import androidx.compose.material.icons.filled.Roofing
import androidx.compose.material.icons.filled.Sports
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.TableRestaurant
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.Wifi
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R
import com.example.data.model.CafeMenuItem
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
import com.example.ui.theme.TertiaryMint
import com.example.ui.theme.TertiaryMintLight
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.BookingViewModel

@Composable
fun CafeScreen(
    viewModel: BookingViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PitchNightBase)
    ) {
        SharedTopBar(
            onMenuClick = { viewModel.navigateTo(AppScreen.MOJI_TERMINI) }
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .padding(bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // HERO INTRO
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(PrimaryTurfContainer.copy(alpha = 0.12f))
                            .border(1.dp, PrimaryTurfContainer.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
                            .padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(PrimaryTurfContainer)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "NOVO 2026",
                            fontFamily = OutfitFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            color = PrimaryTurfLight,
                            letterSpacing = 0.08.sp
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Visibility,
                            contentDescription = null,
                            tint = SecondaryFloodlightAmber,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Pogled na oba terena",
                            fontSize = 12.sp,
                            color = SecondaryFloodlightLight,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Text(
                    text = "Zastakljeni Kafić & Gornji Balkon",
                    fontFamily = OutfitFont,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 24.sp,
                    color = TextPrimary,
                    letterSpacing = (-0.01).sp
                )

                Text(
                    text = "Panoramsko utočište za roditelje tokom treninga i pravo treće poluvreme sa ekipom posle noćnog termina.",
                    fontSize = 13.sp,
                    color = TextMuted,
                    lineHeight = 18.sp
                )
            }

            // ZONE SWITCHER
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(PitchCardSurface)
                    .border(1.dp, PitchCardBorder, RoundedCornerShape(14.dp))
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                val isKafic = uiState.cafeZone == "kafic"

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (isKafic) PitchCardActive else Color.Transparent)
                        .border(
                            width = if (isKafic) 1.5.dp else 0.dp,
                            color = if (isKafic) PrimaryTurfContainer else Color.Transparent,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clickable { viewModel.setCafeZone("kafic") }
                        .padding(vertical = 10.dp)
                        .testTag("tab_cafe_kafic"),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Storefront,
                            contentDescription = null,
                            tint = if (isKafic) PrimaryTurfContainer else TextMuted,
                            modifier = Modifier.size(17.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Zastakljeni Kafić",
                            fontFamily = OutfitFont,
                            fontWeight = if (isKafic) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 13.sp,
                            color = if (isKafic) PrimaryTurfLight else TextMuted
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (!isKafic) PitchCardActive else Color.Transparent)
                        .border(
                            width = if (!isKafic) 1.5.dp else 0.dp,
                            color = if (!isKafic) SecondaryFloodlightAmber else Color.Transparent,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clickable { viewModel.setCafeZone("terasa") }
                        .padding(vertical = 10.dp)
                        .testTag("tab_cafe_terasa"),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Roofing,
                            contentDescription = null,
                            tint = if (!isKafic) SecondaryFloodlightAmber else TextMuted,
                            modifier = Modifier.size(17.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Gornji Balkon",
                            fontFamily = OutfitFont,
                            fontWeight = if (!isKafic) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 13.sp,
                            color = if (!isKafic) SecondaryFloodlightLight else TextMuted
                        )
                    }
                }
            }

            // SHOWCASE CARD WITH CAFE BANNER
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .border(1.dp, PitchCardBorder, RoundedCornerShape(18.dp))
                    .background(PitchCardSurface)
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                    ) {
                        // Image banner
                        Image(
                            painter = painterResource(id = R.drawable.cafe_banner),
                            contentDescription = "Zastakljeni Kafić FK Sava 45",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        // Gradient overlay
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            PitchCardSurface.copy(alpha = 0.5f),
                                            PitchCardSurface
                                        )
                                    )
                                )
                        )

                        // Live status pill
                        Row(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(12.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(PitchNightBase.copy(alpha = 0.85f))
                                .border(1.dp, PitchCardBorder, RoundedCornerShape(20.dp))
                                .padding(horizontal = 9.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(PrimaryTurfContainer))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Otvoreno 08:00 - 23:55",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = PrimaryTurfLight
                            )
                        }

                        // Bottom banner label
                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (uiState.cafeZone == "kafic") "Zastakljeni Lounge u ravni trave" else "Gornji Panoramski Balkon (Terasa)",
                                fontFamily = OutfitFont,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = TextPrimary
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(SecondaryFloodlightAmber)
                                    .padding(horizontal = 7.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = "4K TV Prenosi",
                                    fontFamily = OutfitFont,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp,
                                    color = Color(0xFF070B0E)
                                )
                            }
                        }
                    }

                    // 3 Metrics Status Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CafeMetricCard(
                            icon = Icons.Default.AcUnit,
                            title = "Klima & Grejanje",
                            subtitle = "Uvek 22°C",
                            tint = PrimaryTurfLight,
                            modifier = Modifier.weight(1f)
                        )
                        CafeMetricCard(
                            icon = Icons.Default.Wifi,
                            title = "Brz Wi-Fi",
                            subtitle = "300 Mbps Free",
                            tint = SecondaryFloodlightAmber,
                            modifier = Modifier.weight(1f)
                        )
                        CafeMetricCard(
                            icon = Icons.Default.Tv,
                            title = "Liga Šampiona",
                            subtitle = "Arena & SK",
                            tint = TertiaryMintLight,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // AMBIJENT & ZONE (BENTO CARDS)
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Ambijent & Zone",
                        fontFamily = OutfitFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = TextPrimary
                    )
                    Text(
                        text = "Izaberi gde sediš",
                        fontSize = 12.sp,
                        color = TextMuted
                    )
                }

                // Card 1: Kafić prizemlje
                ZoneBentoCard(
                    icon = Icons.Default.LocalCafe,
                    title = "Zastakljeni Sportski Kafić",
                    badge = "Prizemlje",
                    badgeColor = PrimaryTurfLight,
                    description = "Stakleni zid celom dužinom aut-linije. Savršeno za praćenje treninga dece uz espresso, craft pivo i topao ambijent.",
                    tags = listOf("60 sedećih mesta", "4x 65\" 4K TV")
                )

                // Card 2: Gornji Panoramski Balkon
                ZoneBentoCard(
                    icon = Icons.Default.Roofing,
                    title = "Gornji Panoramski Balkon",
                    badge = "Sprat + Terasa",
                    badgeColor = SecondaryFloodlightAmber,
                    description = "Krovna natkrivena terasa sa pogledom iz ptičije perspektive na oba terena. Lounge fotelje, svež vazduh sa reke Save i zalazak sunca.",
                    tags = listOf("Pogled odozgo", "Otvoreni bar")
                )

                // Card 3: Privatne Proslave & Rođendani
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    PitchCardSurface,
                                    SecondaryFloodlightAmber.copy(alpha = 0.08f)
                                )
                            )
                        )
                        .border(1.dp, SecondaryFloodlightAmber.copy(alpha = 0.35f), RoundedCornerShape(14.dp))
                        .padding(14.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(SecondaryFloodlightAmber.copy(alpha = 0.15f))
                                .border(1.dp, SecondaryFloodlightAmber.copy(alpha = 0.4f), RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Celebration,
                                contentDescription = null,
                                tint = SecondaryFloodlightAmber,
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Fudbalski Rođendani & Proslave",
                                    fontFamily = OutfitFont,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = TextPrimary
                                )
                                Text(
                                    text = "Zakup",
                                    fontFamily = OutfitFont,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp,
                                    color = SecondaryFloodlightAmber
                                )
                            }
                            Text(
                                text = "Mogućnost rezervacije cele terase ili dela kafića. Teren za decu, fudbalski animatori, ozvučenje i roštilj meni po dogovoru.",
                                fontSize = 12.sp,
                                color = TextMuted,
                                lineHeight = 16.sp,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(top = 6.dp)
                                    .clickable {
                                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:0631110407"))
                                        context.startActivity(intent)
                                    }
                            ) {
                                Text(
                                    text = "Raspitaj se za termine zakupa",
                                    fontFamily = OutfitFont,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 12.sp,
                                    color = SecondaryFloodlightLight
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = null,
                                    tint = SecondaryFloodlightLight,
                                    modifier = Modifier.size(13.dp)
                                )
                            }
                        }
                    }
                }
            }

            // MENI & OSVEŽENJE
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Meni & Osveženje",
                            fontFamily = OutfitFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = TextPrimary
                        )
                        Text(
                            text = "Cenovnik pića za igrače i navijače",
                            fontSize = 12.sp,
                            color = TextMuted
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(PitchCardActive)
                            .border(1.dp, PitchCardBorder, RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "RSD Cene",
                            fontFamily = OutfitFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            color = PrimaryTurfLight
                        )
                    }
                }

                // Filter pills
                val filterScroll = rememberScrollState()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(filterScroll),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    CafeFilterChip(label = "Sve", isSelected = uiState.cafeMenuFilter == "all", onClick = { viewModel.setCafeMenuFilter("all") })
                    CafeFilterChip(label = "Kafa & Toplo", isSelected = uiState.cafeMenuFilter == "kafa", onClick = { viewModel.setCafeMenuFilter("kafa") })
                    CafeFilterChip(label = "Pivo & 3. Poluvreme", isSelected = uiState.cafeMenuFilter == "pivo", onClick = { viewModel.setCafeMenuFilter("pivo") })
                    CafeFilterChip(label = "Proteini & Voda", isSelected = uiState.cafeMenuFilter == "fit", onClick = { viewModel.setCafeMenuFilter("fit") })
                }

                // Grid of items
                val filteredItems = viewModel.repository.cafeMenuItems.filter {
                    uiState.cafeMenuFilter == "all" || it.category == uiState.cafeMenuFilter
                }

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    for (i in filteredItems.indices step 2) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            CafeItemCard(item = filteredItems[i], modifier = Modifier.weight(1f))
                            if (i + 1 < filteredItems.size) {
                                CafeItemCard(item = filteredItems[i + 1], modifier = Modifier.weight(1f))
                            } else {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }

            // REZERVIŠI STO ZA EKIPU
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                PitchCardActive,
                                PitchCardSurface
                            )
                        )
                    )
                    .border(1.dp, PitchCardBorder, RoundedCornerShape(18.dp))
                    .padding(16.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(PrimaryTurfContainer.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.TableRestaurant,
                                contentDescription = null,
                                tint = PrimaryTurfLight,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Rezerviši Sto za Ekipu",
                                fontFamily = OutfitFont,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = TextPrimary
                            )
                            Text(
                                text = "Osiguraj mesto na terasi ili pored TV ekrana",
                                fontSize = 11.sp,
                                color = TextMuted
                            )
                        }
                    }

                    Text(
                        text = "Igrate noćni termin od 21h ili 22h? Pošaljite nam poruku na WhatsApp i sačekaće vas spojen sto i ohlađeno piće.",
                        fontSize = 12.sp,
                        color = TextMuted,
                        lineHeight = 17.sp
                    )

                    Button(
                        onClick = {
                            val waUrl = viewModel.getWhatsAppCafeUrl()
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(waUrl))
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
                            .testTag("btn_cafe_whatsapp_table")
                    ) {
                        Icon(imageVector = Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Rezerviši Sto na WhatsApp",
                            fontFamily = OutfitFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }

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
                            .height(44.dp)
                            .border(1.dp, PitchCardBorder, RoundedCornerShape(12.dp))
                            .testTag("btn_cafe_call_bar")
                    ) {
                        Icon(
                            imageVector = Icons.Default.PhoneInTalk,
                            contentDescription = null,
                            tint = SecondaryFloodlightAmber,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Pozovi Šank: 063 111 0407",
                            fontFamily = OutfitFont,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp
                        )
                    }
                }
            }

            // LOCATION FOOTER
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(PitchCardSurface)
                    .border(1.dp, PitchCardBorder, RoundedCornerShape(12.dp))
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = TextMuted,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Dr Ivana Ribara bb, Blok 45",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        )
                        Text(
                            text = "Besplatan parking ispred kluba",
                            fontSize = 10.sp,
                            color = TextMuted
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(PitchNightBase)
                        .border(1.dp, PitchCardBorder, RoundedCornerShape(6.dp))
                        .padding(horizontal = 7.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "Sava Kej 200m",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryTurfLight
                    )
                }
            }
        }
    }
}

@Composable
private fun CafeMetricCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    tint: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(PitchNightBase.copy(alpha = 0.8f))
            .border(1.dp, PitchCardBorder, RoundedCornerShape(10.dp))
            .padding(vertical = 8.dp, horizontal = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = icon, contentDescription = null, tint = tint, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.height(3.dp))
            Text(text = title, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            Text(text = subtitle, fontSize = 10.sp, color = TextMuted)
        }
    }
}

@Composable
private fun ZoneBentoCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    badge: String,
    badgeColor: Color,
    description: String,
    tags: List<String>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(PitchCardSurface)
            .border(1.dp, PitchCardBorder, RoundedCornerShape(14.dp))
            .padding(14.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(PitchCardActive)
                    .border(1.dp, PitchCardBorder, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = badgeColor, modifier = Modifier.size(22.dp))
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        fontFamily = OutfitFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = TextPrimary
                    )
                    Text(
                        text = badge,
                        fontFamily = OutfitFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        color = badgeColor,
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(badgeColor.copy(alpha = 0.12f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Text(
                    text = description,
                    fontSize = 12.sp,
                    color = TextMuted,
                    lineHeight = 16.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(top = 6.dp)
                ) {
                    tags.forEach { tag ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = badgeColor,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = tag, fontSize = 11.sp, color = TextMuted)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CafeFilterChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(if (isSelected) PrimaryTurfContainer else PitchCardSurface)
            .border(
                width = 1.dp,
                color = if (isSelected) PrimaryTurfContainer else PitchCardBorder,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = label,
            fontFamily = OutfitFont,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            fontSize = 11.sp,
            color = if (isSelected) Color(0xFF070B0E) else TextMuted
        )
    }
}

@Composable
private fun CafeItemCard(
    item: CafeMenuItem,
    modifier: Modifier = Modifier
) {
    val iconVector = when (item.icon) {
        "sports_bar" -> Icons.Default.LocalBar
        "fitness_center" -> Icons.Default.Sports
        "nutrition" -> Icons.Default.LocalDrink
        "liquor" -> Icons.Default.LocalBar
        "water_drop" -> Icons.Default.WaterDrop
        else -> Icons.Default.Coffee
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(PitchCardSurface)
            .border(1.dp, PitchCardBorder, RoundedCornerShape(12.dp))
            .padding(10.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.height(115.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    imageVector = iconVector,
                    contentDescription = null,
                    tint = if (item.category == "pivo") SecondaryFloodlightAmber else PrimaryTurfLight,
                    modifier = Modifier.size(20.dp)
                )
                item.badge?.let { badge ->
                    Text(
                        text = badge,
                        fontFamily = OutfitFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        color = if (item.category == "pivo") SecondaryFloodlightAmber else PrimaryTurfLight
                    )
                }
            }

            Column {
                Text(
                    text = item.name,
                    fontFamily = OutfitFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = TextPrimary,
                    maxLines = 1
                )
                Text(
                    text = item.description,
                    fontSize = 10.sp,
                    color = TextMuted,
                    maxLines = 1
                )
            }

            HorizontalDivider(color = PitchCardBorder.copy(alpha = 0.5f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "${item.priceRsd}",
                    fontFamily = OutfitFont,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp,
                    color = if (item.category == "pivo") SecondaryFloodlightLight else PrimaryTurfLight
                )
                Text(
                    text = "RSD",
                    fontSize = 10.sp,
                    color = TextMuted
                )
            }
        }
    }
}
