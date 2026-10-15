package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
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

@Composable
fun CalendarDatePickerDialog(
    selectedDay: Int = 16,
    onDateSelected: (day: Int, monthNumber: Int, monthName: String, dayName: String) -> Unit,
    onDismiss: () -> Unit
) {
    var pickedDay by remember { mutableIntStateOf(selectedDay) }
    val daysInMonth = 31
    val monthName = "Maj 2026"
    val dayNames = listOf("Pon", "Uto", "Sre", "Čet", "Pet", "Sub", "Ned")

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(PitchCardSurface)
                .border(1.dp, PitchCardBorder, RoundedCornerShape(20.dp))
                .padding(18.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(PitchCardActive)
                                .border(1.dp, PitchCardBorder, RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.CalendarMonth,
                                contentDescription = null,
                                tint = PrimaryTurfLight,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Izaberite Datum",
                                fontFamily = OutfitFont,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = TextPrimary
                            )
                            Text(
                                text = monthName,
                                fontFamily = OutfitFont,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp,
                                color = SecondaryFloodlightLight
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Zatvori",
                            tint = TextMuted,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                HorizontalDivider(color = PitchCardBorder.copy(alpha = 0.6f))

                // Day headers (Pon .. Ned)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    dayNames.forEachIndexed { index, name ->
                        val isWeekend = index >= 5
                        Text(
                            text = name,
                            fontFamily = OutfitFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            color = if (isWeekend) SecondaryFloodlightAmber else TextMuted,
                            modifier = Modifier.width(36.dp),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }

                // Calendar Days Grid (Offset 4 for Friday start in May 2026)
                val startDayOffset = 4 // Fri May 1 2026
                val totalCells = 35

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    for (week in 0 until 5) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            for (dayOfWeek in 0 until 7) {
                                val cellIndex = week * 7 + dayOfWeek
                                val dayNumber = cellIndex - startDayOffset + 1

                                if (dayNumber in 1..daysInMonth) {
                                    val isSelected = dayNumber == pickedDay
                                    val isPast = dayNumber < 15
                                    val isWeekend = dayOfWeek >= 5

                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .background(
                                                when {
                                                    isSelected -> PrimaryTurfContainer
                                                    isPast -> Color.Transparent
                                                    else -> PitchNightBase
                                                }
                                            )
                                            .border(
                                                width = if (isSelected) 1.5.dp else if (!isPast) 1.dp else 0.dp,
                                                color = if (isSelected) PrimaryTurfLight else if (!isPast) PitchCardBorder else Color.Transparent,
                                                shape = CircleShape
                                            )
                                            .clickable(enabled = !isPast) {
                                                pickedDay = dayNumber
                                            }
                                            .testTag("cal_day_$dayNumber"),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = dayNumber.toString(),
                                            fontFamily = OutfitFont,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                            fontSize = 13.sp,
                                            color = when {
                                                isSelected -> Color(0xFF070B0E)
                                                isPast -> Color(0xFF3B4A3D)
                                                isWeekend -> SecondaryFloodlightLight
                                                else -> TextPrimary
                                            }
                                        )
                                    }
                                } else {
                                    Spacer(modifier = Modifier.size(36.dp))
                                }
                            }
                        }
                    }
                }

                HorizontalDivider(color = PitchCardBorder.copy(alpha = 0.6f))

                // Action confirmation button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(text = "Odustani", color = TextMuted, fontSize = 13.sp)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    TextButton(
                        onClick = {
                            val dayOfWeekIdx = (pickedDay + startDayOffset - 1) % 7
                            val dayName = dayNames[dayOfWeekIdx]
                            onDateSelected(pickedDay, 5, "Maj", dayName)
                        },
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(PrimaryTurfContainer)
                            .testTag("btn_confirm_calendar_date")
                    ) {
                        Text(
                            text = "Izaberi $pickedDay. Maj",
                            fontFamily = OutfitFont,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF070B0E),
                            fontSize = 13.sp,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                }
            }
        }
    }
}
