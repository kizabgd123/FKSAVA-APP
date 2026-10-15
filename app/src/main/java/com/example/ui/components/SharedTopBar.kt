package com.example.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary

@Composable
fun SharedTopBar(
    onMenuClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(PitchNightBase.copy(alpha = 0.95f))
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Logo / Icon
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(PitchCardActive)
                .border(1.dp, PitchCardBorder, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_fk_sava_crest),
                contentDescription = "FK SAVA 45 Grb",
                modifier = Modifier.size(30.dp)
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        // Title and Location
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "FK SAVA 45",
                    fontFamily = OutfitFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = PrimaryTurfLight,
                    letterSpacing = (-0.01).sp
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(PrimaryTurfContainer)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "Novi Beograd · Blok 45",
                    fontSize = 11.sp,
                    color = TextMuted,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // Direct Call Pill
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(PitchCardSurface)
                .border(1.dp, PitchCardBorder, RoundedCornerShape(20.dp))
                .clickable {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:0631110407"))
                    context.startActivity(intent)
                }
                .padding(horizontal = 10.dp, vertical = 6.dp)
                .testTag("top_bar_call_btn"),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Call,
                contentDescription = "Pozovite klub",
                tint = SecondaryFloodlightAmber,
                modifier = Modifier.size(15.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "063 111 0407",
                fontFamily = OutfitFont,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = SecondaryFloodlightAmber
            )
        }

        Spacer(modifier = Modifier.width(4.dp))

        // Menu icon button
        IconButton(
            onClick = onMenuClick,
            modifier = Modifier
                .size(36.dp)
                .testTag("top_bar_menu_btn")
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Meni",
                tint = TextMuted,
                modifier = Modifier.size(22.dp)
            )
        }
    }
}
