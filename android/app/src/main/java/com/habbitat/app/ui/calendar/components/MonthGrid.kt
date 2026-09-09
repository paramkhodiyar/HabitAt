package com.habbitat.app.ui.calendar.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.habbitat.app.ui.calendar.DayTileData
import com.habbitat.app.ui.calendar.MonthGridData
import com.habbitat.app.ui.theme.HabbitAtTypography
import com.habbitat.app.ui.theme.InkMuted
import com.habbitat.app.ui.theme.InkPrimary

@Composable
fun MonthGrid(
    monthData: MonthGridData,
    onTileClick: (DayTileData) -> Unit,
    modifier: Modifier = Modifier
) {
    val dayHeaders = listOf("M", "T", "W", "T", "F", "S", "S")

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        // Month Title in Display Serif
        Text(
            text = monthData.monthYearLabel,
            style = HabbitAtTypography.headlineMedium,
            color = InkPrimary
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 7-column Day Header Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            dayHeaders.forEach { header ->
                Text(
                    text = header,
                    style = HabbitAtTypography.labelMedium,
                    color = InkMuted,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 7-column Grid of Day Tiles
        val chunkedDays = monthData.days.chunked(7)
        chunkedDays.forEachIndexed { rowIndex, rowDays ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 3.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                rowDays.forEach { dayTileData ->
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn() + scaleIn(),
                        modifier = Modifier.weight(1f)
                    ) {
                        DayTile(
                            tileData = dayTileData,
                            onClick = { onTileClick(dayTileData) }
                        )
                    }
                }
                // Fill missing columns in final row if needed
                if (rowDays.size < 7) {
                    for (i in 0 until (7 - rowDays.size)) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}
