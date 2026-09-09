package com.habbitat.app.ui.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.habbitat.app.ui.theme.CardSurface
import com.habbitat.app.ui.theme.GlassBorder
import com.habbitat.app.ui.theme.HabbitAtTypography
import com.habbitat.app.ui.theme.InkPrimary
import com.habbitat.app.ui.theme.InkSecondary

@Composable
fun MotivationalBanner(
    totalHabitsCount: Int,
    completedTodayCount: Int,
    userName: String = "Param",
    modifier: Modifier = Modifier
) {
    val subtitleText = remember(totalHabitsCount, completedTodayCount) {
        if (totalHabitsCount == 0) {
            "Build your photographic proof of discipline today."
        } else if (completedTodayCount >= totalHabitsCount) {
            "All $totalHabitsCount habits verified today. Flawless execution."
        } else {
            "$completedTodayCount of $totalHabitsCount habits completed. Submit remaining proof before EOD."
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = GlassBorder, shape = RoundedCornerShape(14.dp)),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = CardSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Text(
                text = "Welcome $userName",
                style = HabbitAtTypography.headlineMedium,
                fontSize = 15.sp,
                color = InkPrimary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitleText,
                style = HabbitAtTypography.bodyMedium,
                fontSize = 13.sp,
                color = InkSecondary
            )
        }
    }
}
