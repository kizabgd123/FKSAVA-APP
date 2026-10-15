package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.EventAvailable
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.Sports
import androidx.compose.material.icons.outlined.ConfirmationNumber
import androidx.compose.material.icons.outlined.EventAvailable
import androidx.compose.material.icons.outlined.LocalCafe
import androidx.compose.material.icons.outlined.Sports
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.OutfitFont
import com.example.ui.theme.PitchCardActive
import com.example.ui.theme.PitchCardBorder
import com.example.ui.theme.PitchNightBase
import com.example.ui.theme.PrimaryTurfContainer
import com.example.ui.theme.TextMuted
import com.example.ui.viewmodel.AppScreen

private data class NavItem(
    val screen: AppScreen,
    val title: String,
    val activeIcon: ImageVector,
    val inactiveIcon: ImageVector,
    val testTag: String
)

@Composable
fun BottomNavBar(
    currentScreen: AppScreen,
    onTabSelected: (AppScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        NavItem(
            screen = AppScreen.REZERVISI,
            title = "Rezerviši",
            activeIcon = Icons.Filled.EventAvailable,
            inactiveIcon = Icons.Outlined.EventAvailable,
            testTag = "nav_tab_rezervisi"
        ),
        NavItem(
            screen = AppScreen.SKOLICA,
            title = "Školica",
            activeIcon = Icons.Filled.Sports,
            inactiveIcon = Icons.Outlined.Sports,
            testTag = "nav_tab_skolica"
        ),
        NavItem(
            screen = AppScreen.KAFIC,
            title = "Kafić",
            activeIcon = Icons.Filled.LocalCafe,
            inactiveIcon = Icons.Outlined.LocalCafe,
            testTag = "nav_tab_kafic"
        ),
        NavItem(
            screen = AppScreen.MOJI_TERMINI,
            title = "Moji Termini",
            activeIcon = Icons.Filled.ConfirmationNumber,
            inactiveIcon = Icons.Outlined.ConfirmationNumber,
            testTag = "nav_tab_moji_termini"
        )
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(PitchNightBase.copy(alpha = 0.98f))
            .border(width = 1.dp, color = PitchCardBorder)
            .navigationBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val isSelected = currentScreen == item.screen

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onTabSelected(item.screen) }
                        .padding(vertical = 6.dp)
                        .testTag(item.testTag),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = if (isSelected) item.activeIcon else item.inactiveIcon,
                        contentDescription = item.title,
                        tint = if (isSelected) PrimaryTurfContainer else TextMuted,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = item.title,
                        fontFamily = OutfitFont,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        fontSize = 11.sp,
                        color = if (isSelected) PrimaryTurfContainer else TextMuted,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
        }
    }
}
