package com.habbitat.app.ui.home

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material.icons.rounded.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.habbitat.app.ui.theme.SaffronGlow
import com.habbitat.app.ui.theme.SaffronLight
import com.habbitat.app.ui.theme.SaffronPrimary
import com.habbitat.app.ui.theme.TerracottaHairline
import com.habbitat.app.ui.theme.TurmericGreenSuccess
import com.habbitat.app.ui.theme.TurmericLight
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitDetailModal(
    habit: Habit,
    completionRecords: List<CompletionRecord>,
    isCompletedToday: Boolean,
    onDismiss: () -> Unit,
    onOpenProofCapture: () -> Unit,
    onQuickMarkCompleted: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var selectedRecordForView by remember { mutableStateOf<CompletionRecord?>(null) }

    val todayDateStr = remember {
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }

    val recordsByDate = remember(completionRecords) {
        completionRecords.associateBy { it.date }
    }

    // Generate monthly calendar days for current month
    val calendarDays = remember {
        val days = mutableListOf<CalendarDayInfo>()
        val cal = Calendar.getInstance()
        val currentYear = cal.get(Calendar.YEAR)
        val currentMonth = cal.get(Calendar.MONTH)
        val todayDayOfMonth = cal.get(Calendar.DAY_OF_MONTH)

        cal.set(Calendar.DAY_OF_MONTH, 1)
        val firstDayOfWeek = (cal.get(Calendar.DAY_OF_WEEK) + 5) % 7
        for (i in 0 until firstDayOfWeek) {
            days.add(
                CalendarDayInfo(
                    dayNumber = -1,
                    dateString = "",
                    isToday = false,
                    isPast = false,
                    record = null
                )
            )
        }

        val maxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        for (day in 1..maxDays) {
            cal.set(Calendar.DAY_OF_MONTH, day)
            val dateStr = sdf.format(cal.time)
            val isToday = (day == todayDayOfMonth)
            val isPast = day < todayDayOfMonth
            days.add(
                CalendarDayInfo(
                    dayNumber = day,
                    dateString = dateStr,
                    isToday = isToday,
                    isPast = isPast,
                    record = recordsByDate[dateStr]
                )
            )
        }
        days
    }

    val monthName = remember {
        SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(Date())
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
                .padding(bottom = 24.dp)
        ) {
            // Header: Title & Close Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = habit.name,
                        style = HabbitAtTypography.displayMedium,
                        color = InkPrimary
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Habit Details & History Ledger",
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

            Spacer(modifier = Modifier.height(16.dp))

            // Metadata Row: Frequency, Duration, Interval
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MetaBadge(
                    icon = Icons.Rounded.Schedule,
                    label = "${habit.targetDurationMinutes} min target",
                    bgColor = SaffronGlow,
                    contentColor = SaffronPrimary,
                    modifier = Modifier.weight(1f)
                )

                MetaBadge(
                    icon = Icons.Rounded.Notifications,
                    label = "${habit.reminderIntervalMinutes}m interval",
                    bgColor = IndigoLight,
                    contentColor = IndigoSecondary,
                    modifier = Modifier.weight(1f)
                )

                MetaBadge(
                    icon = Icons.Rounded.Event,
                    label = habit.frequency,
                    bgColor = TurmericLight,
                    contentColor = TurmericGreenSuccess,
                    modifier = Modifier.weight(1f)
                )
            }

            // Description / Proof Requirement
            if (habit.proofDescription.isNotBlank()) {
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = IndigoLight)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Description,
                            contentDescription = null,
                            tint = IndigoSecondary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = habit.proofDescription,
                            style = HabbitAtTypography.bodyMedium,
                            color = IndigoSecondary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Calendar Section Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = monthName,
                    style = HabbitAtTypography.titleMedium,
                    color = InkPrimary
                )

                if (isCompletedToday) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(TurmericLight)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Rounded.CheckCircle,
                                contentDescription = null,
                                tint = TurmericGreenSuccess,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Completed Today",
                                style = HabbitAtTypography.labelMedium,
                                color = TurmericGreenSuccess,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 7-Column Day Labels Header
            Row(modifier = Modifier.fillMaxWidth()) {
                listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun").forEach { day ->
                    Text(
                        text = day,
                        style = HabbitAtTypography.labelSmall,
                        color = InkMuted,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // 7-Column Calendar Grid for this Habit
            LazyVerticalGrid(
                columns = GridCells.Fixed(7),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            ) {
                items(calendarDays) { dayInfo ->
                    DayTile(
                        dayInfo = dayInfo,
                        onClick = {
                            if (dayInfo.record != null) {
                                selectedRecordForView = dayInfo.record
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Primary Action: Photo Proof or Already Verified Badge
            if (isCompletedToday) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(TurmericLight)
                        .padding(vertical = 14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Rounded.Verified,
                            contentDescription = "Verified Today",
                            tint = TurmericGreenSuccess,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Verified & Completed Today",
                            style = HabbitAtTypography.labelLarge,
                            color = TurmericGreenSuccess,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            } else {
                Button(
                    onClick = {
                        onDismiss()
                        onOpenProofCapture()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary)
                ) {
                    Text(
                        text = "Log Photo Proof",
                        style = HabbitAtTypography.labelLarge,
                        color = InkPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Discreet Secondary / Emergency Action: Quick Mark Completed
            OutlinedButton(
                onClick = {
                    if (!isCompletedToday) {
                        onQuickMarkCompleted()
                        onDismiss()
                    }
                },
                enabled = !isCompletedToday,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = if (isCompletedToday) "Quick Mark (Already Completed Today)" else "Emergency Quick Mark (No Photo)",
                    style = HabbitAtTypography.labelMedium,
                    color = if (isCompletedToday) InkMuted else InkSecondary
                )
            }
        }
    }

    // Modal to view full photo proof if a tile is tapped
    selectedRecordForView?.let { record ->
        ModalBottomSheet(
            onDismissRequest = { selectedRecordForView = null },
            containerColor = CardSurface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Proof Recorded on ${record.date}",
                    style = HabbitAtTypography.headlineMedium,
                    color = InkPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "AI Confidence: ${(record.confidence * 100).toInt()}% • Streak: ${record.streakAtCompletion}",
                    style = HabbitAtTypography.bodyMedium,
                    color = InkSecondary
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (!record.imageLocalUri.isNullOrBlank()) {
                    AsyncImage(
                        model = File(record.imageLocalUri),
                        contentDescription = "Proof Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(280.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(TurmericLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Rounded.CheckCircle,
                                contentDescription = null,
                                tint = TurmericGreenSuccess,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Manually Verified Record",
                                style = HabbitAtTypography.bodyMedium,
                                color = TurmericGreenSuccess,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { selectedRecordForView = null },
                    colors = ButtonDefaults.buttonColors(containerColor = SaffronLight),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Close", color = InkPrimary)
                }
            }
        }
    }
}

data class CalendarDayInfo(
    val dayNumber: Int,
    val dateString: String,
    val isToday: Boolean,
    val isPast: Boolean,
    val record: CompletionRecord?
)

@Composable
private fun DayTile(
    dayInfo: CalendarDayInfo,
    onClick: () -> Unit
) {
    if (dayInfo.dayNumber == -1) {
        Box(modifier = Modifier.aspectRatio(1f))
        return
    }

    val isCompleted = dayInfo.record != null && dayInfo.record.verified
    val hasPhoto = isCompleted && !dayInfo.record?.imageLocalUri.isNullOrBlank()

    val borderModifier = when {
        isCompleted -> Modifier.border(1.dp, TurmericGreenSuccess, RoundedCornerShape(8.dp))
        dayInfo.isToday -> Modifier.border(1.5.dp, SaffronPrimary, RoundedCornerShape(8.dp))
        dayInfo.isPast -> Modifier.border(0.8.dp, TerracottaHairline, RoundedCornerShape(8.dp))
        else -> Modifier.border(0.5.dp, GlassBorder, RoundedCornerShape(8.dp))
    }

    val bgModifier = when {
        isCompleted -> Modifier.background(TurmericLight)
        dayInfo.isToday -> Modifier.background(SaffronGlow)
        else -> Modifier.background(CardSurface)
    }

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
            .then(bgModifier)
            .then(borderModifier)
            .clickable(enabled = isCompleted, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (hasPhoto) {
            AsyncImage(
                model = File(dayInfo.record!!.imageLocalUri!!),
                contentDescription = "Day ${dayInfo.dayNumber} proof",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentScale = ContentScale.Crop
            )
        } else if (isCompleted) {
            Icon(
                imageVector = Icons.Rounded.Check,
                contentDescription = "Completed",
                tint = TurmericGreenSuccess,
                modifier = Modifier.size(16.dp)
            )
        } else {
            Text(
                text = "${dayInfo.dayNumber}",
                style = HabbitAtTypography.labelSmall,
                color = if (dayInfo.isToday) SaffronPrimary else if (dayInfo.isPast) InkMuted else InkSecondary
            )
        }
    }
}

@Composable
private fun MetaBadge(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    bgColor: androidx.compose.ui.graphics.Color,
    contentColor: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(bgColor)
            .padding(horizontal = 8.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = label,
                style = HabbitAtTypography.labelSmall,
                color = contentColor,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
