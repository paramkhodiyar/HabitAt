package com.habbitat.app.ui.home

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.habbitat.app.data.local.entity.Habit
import com.habbitat.app.ui.theme.CardSurface
import com.habbitat.app.ui.theme.GlassBorder
import com.habbitat.app.ui.theme.HabbitAtTypography
import com.habbitat.app.ui.theme.IndigoLight
import com.habbitat.app.ui.theme.IndigoSecondary
import com.habbitat.app.ui.theme.InkMuted
import com.habbitat.app.ui.theme.InkPrimary
import com.habbitat.app.ui.theme.InkSecondary
import com.habbitat.app.ui.theme.SaffronPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditHabitSheet(
    habit: Habit,
    onDismiss: () -> Unit,
    onUpdateHabit: (Habit) -> Unit
) {
    var name by remember { mutableStateOf(habit.name) }
    var targetDurationMinutes by remember { mutableStateOf(habit.targetDurationMinutes.toString()) }
    var proofDescription by remember { mutableStateOf(habit.proofDescription) }
    var reminderIntervalMinutes by remember { mutableStateOf(habit.reminderIntervalMinutes.toString()) }
    var frequency by remember { mutableStateOf(habit.frequency) }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

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
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Edit Habit",
                    style = HabbitAtTypography.displaySmall,
                    color = InkPrimary
                )
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "Close",
                        tint = InkSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Habit Name Field
            Text(
                text = "Habit Name",
                style = HabbitAtTypography.labelLarge,
                color = InkPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                singleLine = true,
                placeholder = { Text("e.g. Morning Workout", color = InkMuted) },
                textStyle = HabbitAtTypography.bodyMedium.copy(color = InkPrimary),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = SaffronPrimary,
                    unfocusedBorderColor = GlassBorder
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Frequency Selector
            Text(
                text = "Frequency",
                style = HabbitAtTypography.labelLarge,
                color = InkPrimary
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                listOf("DAILY", "WEEKLY", "MONTHLY").forEach { freq ->
                    val isSelected = frequency.equals(freq, ignoreCase = true)
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) SaffronPrimary else IndigoLight)
                            .clickable { frequency = freq }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = freq,
                            style = HabbitAtTypography.labelMedium,
                            color = if (isSelected) InkPrimary else IndigoSecondary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Target Duration Field
            Text(
                text = "Target Duration (Minutes)",
                style = HabbitAtTypography.labelLarge,
                color = InkPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = targetDurationMinutes,
                onValueChange = { targetDurationMinutes = it.filter { char -> char.isDigit() } },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = HabbitAtTypography.bodyMedium.copy(color = InkPrimary),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = SaffronPrimary,
                    unfocusedBorderColor = GlassBorder
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Photo Proof Instructions
            Text(
                text = "Photo Proof Prompt",
                style = HabbitAtTypography.labelLarge,
                color = InkPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = proofDescription,
                onValueChange = { proofDescription = it },
                placeholder = { Text("What should the photo show?", color = InkMuted) },
                textStyle = HabbitAtTypography.bodyMedium.copy(color = InkPrimary),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = SaffronPrimary,
                    unfocusedBorderColor = GlassBorder
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Escalating Reminder Interval
            Text(
                text = "Reminder Escalation Interval (Minutes)",
                style = HabbitAtTypography.labelLarge,
                color = InkPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = reminderIntervalMinutes,
                onValueChange = { reminderIntervalMinutes = it.filter { char -> char.isDigit() } },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = HabbitAtTypography.bodyMedium.copy(color = InkPrimary),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = SaffronPrimary,
                    unfocusedBorderColor = GlassBorder
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        val updated = habit.copy(
                            name = name.trim(),
                            targetDurationMinutes = targetDurationMinutes.toIntOrNull() ?: habit.targetDurationMinutes,
                            proofDescription = proofDescription.trim(),
                            reminderIntervalMinutes = reminderIntervalMinutes.toIntOrNull() ?: habit.reminderIntervalMinutes,
                            frequency = frequency
                        )
                        onUpdateHabit(updated)
                        onDismiss()
                    }
                },
                enabled = name.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary)
            ) {
                Text(
                    text = "Save Changes",
                    style = HabbitAtTypography.labelLarge,
                    color = InkPrimary
                )
            }
        }
    }
}
