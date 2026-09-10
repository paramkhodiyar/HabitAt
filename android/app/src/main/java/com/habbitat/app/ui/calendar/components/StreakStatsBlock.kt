package com.habitAt.app.ui.calendar.components

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EmojiEvents
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.material.icons.rounded.Percent
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.habitAt.app.ui.calendar.StreakStats
import com.habitAt.app.ui.theme.CardSurface
import com.habitAt.app.ui.theme.GlassBorder
import com.habitAt.app.ui.theme.habitAtTypography
import com.habitAt.app.ui.theme.IndigoLight
import com.habitAt.app.ui.theme.IndigoSecondary
import com.habitAt.app.ui.theme.InkPrimary
import com.habitAt.app.ui.theme.InkSecondary
import com.habitAt.app.ui.theme.SaffronLight
import com.habitAt.app.ui.theme.SaffronPrimary
import com.habitAt.app.ui.theme.TurmericGreenSuccess
import com.habitAt.app.ui.theme.TurmericLight

@Composable
fun StreakStatsBlock(
    stats: StreakStats,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = GlassBorder, shape = RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = CardSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Current Streak
            StatItem(
                icon = Icons.Rounded.LocalFireDepartment,
                iconTint = SaffronPrimary,
                iconBg = SaffronLight,
                valueText = "${stats.currentStreak}",
                labelText = "Current Streak"
            )

            // Best Streak
            StatItem(
                icon = Icons.Rounded.EmojiEvents,
                iconTint = IndigoSecondary,
                iconBg = IndigoLight,
                valueText = "${stats.bestStreak}",
                labelText = "Best Streak"
            )

            // Completion Rate %
            StatItem(
                icon = Icons.Rounded.Percent,
                iconTint = TurmericGreenSuccess,
                iconBg = TurmericLight,
                valueText = "${stats.completionRatePercent}%",
                labelText = "30d Consistency"
            )
        }
    }
}

@Composable
private fun StatItem(
    icon: ImageVector,
    iconTint: Color,
    iconBg: Color,
    valueText: String,
    labelText: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(iconBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = labelText,
                tint = iconTint,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = valueText,
            style = habitAtTypography.displaySmall,
            color = InkPrimary
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = labelText,
            style = habitAtTypography.labelMedium,
            color = InkSecondary
        )
    }
}
