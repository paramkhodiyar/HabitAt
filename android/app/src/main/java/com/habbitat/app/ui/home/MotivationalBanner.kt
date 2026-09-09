package com.habbitat.app.ui.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.habbitat.app.ui.theme.CardSurface
import com.habbitat.app.ui.theme.GlassBorder
import com.habbitat.app.ui.theme.HabbitAtTypography
import com.habbitat.app.ui.theme.IndigoSecondary
import com.habbitat.app.ui.theme.InkPrimary
import com.habbitat.app.ui.theme.InkSecondary
import com.habbitat.app.ui.theme.SaffronPrimary
import java.util.Calendar

@Composable
fun MotivationalBanner(
    totalHabitsCount: Int,
    completedTodayCount: Int,
    modifier: Modifier = Modifier
) {
    val quotes = remember {
        listOf(
            "We are what we repeatedly do. Excellence, then, is not an act, but a habit." to "Aristotle",
            "Discipline is choosing between what you want now and what you want most." to "Abraham Lincoln",
            "Small daily improvements over time lead to stunning results." to "Robin Sharma",
            "Proof creates belief. Show up today and record your victory." to "HabitAt AI Core",
            "Consistency is the true test of character and self-mastery." to "HabitAt AI Core"
        )
    }

    val dayOfYear = remember { Calendar.getInstance().get(Calendar.DAY_OF_YEAR) }
    val (quoteText, quoteAuthor) = remember(dayOfYear) {
        quotes[dayOfYear % quotes.size]
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = GlassBorder, shape = RoundedCornerShape(18.dp)),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = CardSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = Icons.Rounded.AutoAwesome,
                contentDescription = "Daily AI Motivation",
                tint = SaffronPrimary,
                modifier = Modifier
                    .size(24.dp)
                    .padding(top = 2.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "DAILY MOTIVATIONAL AI FOCUS",
                        style = HabbitAtTypography.labelSmall,
                        letterSpacing = 1.2.sp,
                        color = IndigoSecondary,
                        fontWeight = FontWeight.Bold
                    )

                    if (totalHabitsCount > 0) {
                        Text(
                            text = "$completedTodayCount/$totalHabitsCount Done Today",
                            style = HabbitAtTypography.labelSmall,
                            color = InkSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "\"$quoteText\"",
                    style = HabbitAtTypography.bodyMedium,
                    fontStyle = FontStyle.Italic,
                    color = InkPrimary
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "— $quoteAuthor",
                    style = HabbitAtTypography.labelMedium,
                    color = InkSecondary
                )
            }
        }
    }
}
