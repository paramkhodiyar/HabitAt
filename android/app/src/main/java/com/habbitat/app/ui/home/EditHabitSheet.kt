package com.habitAt.app.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.habitAt.app.data.local.entity.Habit
import com.habitAt.app.ui.theme.CardSurface
import com.habitAt.app.ui.theme.GlassBorder
import com.habitAt.app.ui.theme.habitAtTypography
import com.habitAt.app.ui.theme.IndigoLight
import com.habitAt.app.ui.theme.IndigoSecondary
import com.habitAt.app.ui.theme.InkMuted
import com.habitAt.app.ui.theme.InkPrimary
import com.habitAt.app.ui.theme.InkSecondary
import com.habitAt.app.ui.theme.SaffronLight
import com.habitAt.app.ui.theme.SaffronPrimary
import com.habitAt.app.ui.theme.TerracottaTertiary

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun EditHabitSheet(
    habit: Habit,
    onDismiss: () -> Unit,
    onUpdateHabit: (Habit) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var habitName by remember { mutableStateOf(habit.name) }
    var habitDescription by remember { mutableStateOf(habit.description) }
    var selectedFrequency by remember { mutableStateOf(habit.frequency) }
    var targetDurationMinutes by remember { mutableIntStateOf(habit.targetDurationMinutes) }
    var proofDescription by remember { mutableStateOf(habit.proofDescription) }
    var reminderIntervalMinutes by remember { mutableIntStateOf(habit.reminderIntervalMinutes) }

    var isError by remember { mutableStateOf(false) }

    val quickPresets = listOf(
        QuickPresetIdea("📖 Reading", "Reading", "Read 20 pages of non-fiction or book chapter", 30, "Photo of book open to current page"),
        QuickPresetIdea("🧘 Mindfulness", "Mindfulness", "15 minutes of meditation & breathwork", 15, "Photo of calm meditation space"),
        QuickPresetIdea("💧 Hydration", "Hydration", "Drink 3 liters of water throughout the day", 5, "Photo of water container"),
        QuickPresetIdea("🏋️ Fitness", "Fitness", "High-intensity morning workout session", 45, "Photo of workout space or gym gear"),
        QuickPresetIdea("💻 Skill Practice", "Skill Practice", "Deep work block practicing code or design", 60, "Photo of IDE / workspace screen")
    )

    val durationPresets = listOf(10, 15, 30, 45, 60, 90, 120)
    val frequencyOptions = listOf("DAILY" to "Daily", "WEEKLY" to "Weekly", "MONTHLY" to "Monthly")
    val intervalPresets = listOf(15, 30, 45, 60, 90)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = CardSurface,
        scrimColor = InkPrimary.copy(alpha = 0.32f),
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp)
                .padding(bottom = 32.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Edit Habit",
                    style = habitAtTypography.displaySmall,
                    color = InkPrimary
                )
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "Close",
                        tint = InkMuted
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Quick Idea Preset Pills
            Text(
                text = "Quick Inspiration",
                style = habitAtTypography.labelMedium,
                color = InkSecondary
            )
            Spacer(modifier = Modifier.height(6.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                quickPresets.forEach { preset ->
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(IndigoLight)
                            .border(width = 1.dp, color = GlassBorder, shape = CircleShape)
                            .clickable {
                                habitName = preset.habitName
                                habitDescription = preset.description
                                targetDurationMinutes = preset.durationMinutes
                                proofDescription = preset.proofDescription
                                if (isError) isError = false
                            }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = preset.pillName,
                            style = habitAtTypography.labelMedium,
                            color = IndigoSecondary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Habit Title Input
            Text(
                text = "Habit Title",
                style = habitAtTypography.labelLarge,
                color = InkPrimary
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = habitName,
                onValueChange = {
                    habitName = it
                    if (isError && it.isNotBlank()) isError = false
                },
                placeholder = {
                    Text(
                        text = "Enter habit title…",
                        color = InkMuted.copy(alpha = 0.45f)
                    )
                },
                isError = isError,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = SaffronPrimary,
                    unfocusedBorderColor = GlassBorder,
                    focusedContainerColor = CardSurface,
                    unfocusedContainerColor = CardSurface
                )
            )
            if (isError) {
                Text(
                    text = "Please enter a habit title",
                    style = habitAtTypography.labelMedium,
                    color = TerracottaTertiary,
                    modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Optional Habit Description
            Text(
                text = "Habit Description (Optional)",
                style = habitAtTypography.labelLarge,
                color = InkPrimary
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = habitDescription,
                onValueChange = { habitDescription = it },
                placeholder = {
                    Text(
                        text = "Optional context or goal details…",
                        color = InkMuted.copy(alpha = 0.45f)
                    )
                },
                singleLine = false,
                maxLines = 2,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = SaffronPrimary,
                    unfocusedBorderColor = GlassBorder,
                    focusedContainerColor = CardSurface,
                    unfocusedContainerColor = CardSurface
                )
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Schedule Cadence (Daily, Weekly, Monthly)
            Text(
                text = "Schedule Cadence",
                style = habitAtTypography.labelLarge,
                color = InkPrimary
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                frequencyOptions.forEach { (code, label) ->
                    val isSelected = code == selectedFrequency
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) SaffronLight else CardSurface)
                            .border(
                                width = 1.dp,
                                color = if (isSelected) SaffronPrimary else GlassBorder,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable { selectedFrequency = code }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = label,
                            style = habitAtTypography.labelMedium,
                            color = if (isSelected) SaffronPrimary else InkPrimary,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Target Duration Picker & Stepper
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Target Duration",
                    style = habitAtTypography.labelLarge,
                    color = InkPrimary
                )
                Text(
                    text = "$targetDurationMinutes mins",
                    style = habitAtTypography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = SaffronPrimary
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Duration Stepper + Preset Chips
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Minus button
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(SaffronLight)
                        .clickable {
                            if (targetDurationMinutes > 5) targetDurationMinutes -= 5
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Remove,
                        contentDescription = "Decrease",
                        tint = SaffronPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }

                // Preset Pills
                FlowRow(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    durationPresets.forEach { dur ->
                        val isSel = dur == targetDurationMinutes
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(if (isSel) SaffronPrimary else CardSurface)
                                .border(
                                    width = 1.dp,
                                    color = if (isSel) SaffronPrimary else GlassBorder,
                                    shape = CircleShape
                                )
                                .clickable { targetDurationMinutes = dur }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "${dur}m",
                                style = habitAtTypography.labelMedium,
                                color = if (isSel) CardSurface else InkPrimary
                            )
                        }
                    }
                }

                // Plus button
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(SaffronLight)
                        .clickable { targetDurationMinutes += 5 },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "Increase",
                        tint = SaffronPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Photo Proof Description
            Text(
                text = "Required Photo Proof",
                style = habitAtTypography.labelLarge,
                color = InkPrimary
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = proofDescription,
                onValueChange = { proofDescription = it },
                placeholder = {
                    Text(
                        text = "Describe photo evidence required…",
                        color = InkMuted.copy(alpha = 0.45f)
                    )
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = SaffronPrimary,
                    unfocusedBorderColor = GlassBorder,
                    focusedContainerColor = CardSurface,
                    unfocusedContainerColor = CardSurface
                )
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Escalation Reminder Interval
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Nudge Reminder Frequency",
                    style = habitAtTypography.labelLarge,
                    color = InkPrimary
                )
                Text(
                    text = "Every $reminderIntervalMinutes mins",
                    style = habitAtTypography.titleSmall,
                    color = InkSecondary
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                intervalPresets.forEach { interval ->
                    val isSelected = interval == reminderIntervalMinutes
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) SaffronLight else CardSurface)
                            .border(
                                width = 1.dp,
                                color = if (isSelected) SaffronPrimary else GlassBorder,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable { reminderIntervalMinutes = interval }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${interval}m",
                            style = habitAtTypography.labelMedium,
                            color = if (isSelected) SaffronPrimary else InkPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Submit Button
            Button(
                onClick = {
                    if (habitName.isBlank()) {
                        isError = true
                    } else {
                        val updated = habit.copy(
                            name = habitName.trim(),
                            description = habitDescription.trim(),
                            targetDurationMinutes = targetDurationMinutes,
                            proofDescription = proofDescription.trim(),
                            reminderIntervalMinutes = reminderIntervalMinutes,
                            frequency = selectedFrequency
                        )
                        onUpdateHabit(updated)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SaffronPrimary,
                    contentColor = InkPrimary
                )
            ) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Save Changes",
                    style = habitAtTypography.titleMedium,
                    color = InkPrimary
                )
            }
        }
    }
}
