package com.habbitat.app.ui.calendar

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.habbitat.app.data.local.entity.CompletionRecord
import com.habbitat.app.data.local.entity.Habit
import com.habbitat.app.ui.theme.CardSurface
import com.habbitat.app.ui.theme.GlassBorder
import com.habbitat.app.ui.theme.HabbitAtTypography
import com.habbitat.app.ui.theme.IndigoLight
import com.habbitat.app.ui.theme.IndigoSecondary
import com.habbitat.app.ui.theme.InkMuted
import com.habbitat.app.ui.theme.InkPrimary
import com.habbitat.app.ui.theme.InkSecondary
import com.habbitat.app.ui.theme.SaffronLight
import com.habbitat.app.ui.theme.SaffronPrimary
import com.habbitat.app.ui.theme.TerracottaHairline
import com.habbitat.app.ui.theme.TerracottaLight
import com.habbitat.app.ui.theme.TerracottaTertiary
import com.habbitat.app.ui.theme.TurmericGreenSuccess
import com.habbitat.app.ui.theme.TurmericLight
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayBreakdownModal(
    dateString: String, // yyyy-MM-dd
    allHabits: List<Habit>,
    recordsForDate: List<CompletionRecord>,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val formattedDateTitle = remember(dateString) {
        try {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val d = sdf.parse(dateString)
            if (d != null) {
                SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault()).format(d)
            } else dateString
        } catch (e: Exception) {
            dateString
        }
    }

    val recordsByHabitId = remember(recordsForDate) {
        recordsForDate.associateBy { it.habitId }
    }

    val completedCount = remember(recordsForDate) {
        recordsForDate.count { it.verified }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = CardSurface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp)
                .padding(bottom = 28.dp)
        ) {
            // Header: Title & Close Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = formattedDateTitle,
                        style = HabbitAtTypography.headlineMedium,
                        color = InkPrimary
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Daily Discipline & Proof Breakdown",
                        style = HabbitAtTypography.bodyMedium,
                        color = InkSecondary
                    )
                }

                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "Close",
                        tint = InkSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Summary Pills
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(TurmericLight)
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "$completedCount Verified Completed",
                        style = HabbitAtTypography.labelSmall,
                        color = TurmericGreenSuccess,
                        fontWeight = FontWeight.Bold
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(IndigoLight)
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "${allHabits.size} Total Habits",
                        style = HabbitAtTypography.labelSmall,
                        color = IndigoSecondary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Habits Breakdown List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                items(items = allHabits, key = { habit -> habit.id }) { habit ->
                    val record = recordsByHabitId[habit.id]
                    val isCompleted = record != null && record.verified
                    val hasPhoto = isCompleted && !record?.imageLocalUri.isNullOrBlank()

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(width = 1.dp, color = GlassBorder, shape = RoundedCornerShape(14.dp)),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = CardSurface)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = habit.name,
                                            style = HabbitAtTypography.headlineMedium,
                                            fontSize = 16.sp,
                                            color = InkPrimary
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(6.dp))
                                                .background(IndigoLight)
                                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                        ) {
                                            Text(
                                                text = habit.frequency,
                                                style = HabbitAtTypography.labelSmall,
                                                color = IndigoSecondary,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(4.dp))
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Rounded.Schedule,
                                            contentDescription = null,
                                            tint = InkMuted,
                                            modifier = Modifier.size(13.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "${habit.targetDurationMinutes} min target",
                                            style = HabbitAtTypography.bodyMedium,
                                            fontSize = 13.sp,
                                            color = InkSecondary
                                        )
                                    }
                                }

                                // Status Badge
                                if (isCompleted) {
                                    Box(
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .background(TurmericLight)
                                            .padding(horizontal = 10.dp, vertical = 4.dp)
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = Icons.Rounded.CheckCircle,
                                                contentDescription = "Completed",
                                                tint = TurmericGreenSuccess,
                                                modifier = Modifier.size(14.dp)
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = "Verified",
                                                style = HabbitAtTypography.labelSmall,
                                                color = TurmericGreenSuccess,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .background(TerracottaLight)
                                            .border(0.5.dp, TerracottaHairline, CircleShape)
                                            .padding(horizontal = 10.dp, vertical = 4.dp)
                                    ) {
                                        Text(
                                            text = "Incomplete",
                                            style = HabbitAtTypography.labelSmall,
                                            color = TerracottaTertiary
                                        )
                                    }
                                }
                            }

                            // If photo proof exists for this day, render thumbnail preview
                            if (hasPhoto) {
                                Spacer(modifier = Modifier.height(10.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(TurmericLight)
                                        .padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    AsyncImage(
                                        model = File(record!!.imageLocalUri!!),
                                        contentDescription = "Proof Thumbnail",
                                        modifier = Modifier
                                            .size(48.dp)
                                            .clip(RoundedCornerShape(8.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(
                                            text = "Photographic Proof Captured",
                                            style = HabbitAtTypography.labelMedium,
                                            color = TurmericGreenSuccess,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "AI Confidence: ${(record.confidence * 100).toInt()}% • Streak: ${record.streakAtCompletion}",
                                            style = HabbitAtTypography.bodyMedium,
                                            fontSize = 12.sp,
                                            color = InkSecondary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = SaffronLight),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Close", color = InkPrimary)
            }
        }
    }
}
