package com.habbitat.app.ui.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.SelfImprovement
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.habbitat.app.HabbitAtApp
import com.habbitat.app.data.UserPreferences
import com.habbitat.app.data.local.entity.Habit
import com.habbitat.app.ui.components.BackgroundMotif
import com.habbitat.app.ui.components.MotifVariant
import com.habbitat.app.ui.theme.CardSurface
import com.habbitat.app.ui.theme.GlassBorder
import com.habbitat.app.ui.theme.HabbitAtMotion
import com.habbitat.app.ui.theme.HabbitAtTypography
import com.habbitat.app.ui.theme.IndigoLight
import com.habbitat.app.ui.theme.IndigoSecondary
import com.habbitat.app.ui.theme.InkMuted
import com.habbitat.app.ui.theme.InkPrimary
import com.habbitat.app.ui.theme.InkSecondary
import com.habbitat.app.ui.theme.SaffronGlow
import com.habbitat.app.ui.theme.SaffronPrimary
import com.habbitat.app.ui.theme.TurmericGreenSuccess
import com.habbitat.app.ui.theme.TurmericLight
import com.habbitat.app.ui.theme.WarmIvory
import androidx.compose.material.icons.rounded.Book
import androidx.compose.material.icons.rounded.Code
import androidx.compose.material.icons.rounded.DirectionsRun
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material.icons.rounded.WaterDrop
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import com.habbitat.app.ui.components.GPaySuccessCheckmark
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class PresetHabitItem(
    val name: String,
    val durationMinutes: Int,
    val frequency: String,
    val proofDescription: String,
    val description: String = "",
    val icon: ImageVector,
    val iconBgColor: Color,
    val iconTint: Color
)

@Composable
fun OnboardingScreen(
    onOnboardingComplete: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val userPrefs = remember { UserPreferences(context) }
    val repository = remember { (context.applicationContext as HabbitAtApp).repository }

    var step by remember { mutableStateOf(1) }
    var nicknameInput by remember { mutableStateOf("") }
    var isSubmitting by remember { mutableStateOf(false) }
    var showCompletionAnimation by remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }

    val presetList = remember {
        listOf(
            PresetHabitItem(
                name = "Morning Workout",
                durationMinutes = 20,
                frequency = "DAILY",
                proofDescription = "Photo of gym gear or workout space",
                description = "High-energy morning exercise session to ignite focus.",
                icon = Icons.Rounded.DirectionsRun,
                iconBgColor = SaffronGlow,
                iconTint = SaffronPrimary
            ),
            PresetHabitItem(
                name = "Read 20 Pages",
                durationMinutes = 30,
                frequency = "DAILY",
                proofDescription = "Photo of book open to current chapter",
                description = "Non-fiction or literature reading for continuous learning.",
                icon = Icons.Rounded.Book,
                iconBgColor = IndigoLight,
                iconTint = IndigoSecondary
            ),
            PresetHabitItem(
                name = "Meditation & Breathing",
                durationMinutes = 15,
                frequency = "DAILY",
                proofDescription = "Photo of meditation space",
                description = "Mindfulness and deep breathwork for mental clarity.",
                icon = Icons.Rounded.SelfImprovement,
                iconBgColor = TurmericLight,
                iconTint = TurmericGreenSuccess
            ),
            PresetHabitItem(
                name = "Daily Code Commit",
                durationMinutes = 45,
                frequency = "DAILY",
                proofDescription = "Photo of terminal / editor code",
                description = "Dedicated deep work coding block for personal projects.",
                icon = Icons.Rounded.Code,
                iconBgColor = SaffronGlow,
                iconTint = SaffronPrimary
            ),
            PresetHabitItem(
                name = "Cold Shower",
                durationMinutes = 5,
                frequency = "DAILY",
                proofDescription = "Photo of bathroom setup",
                description = "Physical resilience building and instant wakefulness.",
                icon = Icons.Rounded.WaterDrop,
                iconBgColor = IndigoLight,
                iconTint = IndigoSecondary
            ),
            PresetHabitItem(
                name = "Night Journal",
                durationMinutes = 10,
                frequency = "DAILY",
                proofDescription = "Photo of handwritten journal page",
                description = "Reflective journaling on daily wins and lessons learned.",
                icon = Icons.Rounded.EditNote,
                iconBgColor = TurmericLight,
                iconTint = TurmericGreenSuccess
            )
        )
    }

    val selectedPresets = remember { mutableStateListOf<PresetHabitItem>().apply { add(presetList[0]) } }

    val topInset = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmIvory)
    ) {
        BackgroundMotif(variant = MotifVariant.KOLAM_DOT_GRID)

        if (showCompletionAnimation) {
            // Animated spring checkmark tick screen
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    GPaySuccessCheckmark(sizeDp = 84)

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Setup Completed",
                        style = HabbitAtTypography.headlineLarge,
                        color = InkPrimary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Your photographic proof ledger is active and ready.",
                        style = HabbitAtTypography.bodyMedium,
                        textAlign = TextAlign.Center,
                        color = InkSecondary
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding()
                    .padding(horizontal = 24.dp)
                    .padding(top = topInset + 16.dp, bottom = 24.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Header Step Indicator
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "HabitAt",
                        style = HabbitAtTypography.displayMedium,
                        color = SaffronPrimary
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        for (i in 1..3) {
                            Box(
                                modifier = Modifier
                                    .size(if (i == step) 20.dp else 8.dp, 8.dp)
                                    .clip(CircleShape)
                                    .background(if (i == step) SaffronPrimary else GlassBorder)
                            )
                        }
                    }
                }

                when (step) {
                    1 -> {
                        // Step 1: Welcome Screen
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(CircleShape)
                                    .background(SaffronGlow),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.SelfImprovement,
                                    contentDescription = null,
                                    tint = SaffronPrimary,
                                    modifier = Modifier.size(36.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            Text(
                                text = "Welcome to HabitAt",
                                style = HabbitAtTypography.displayLarge,
                                fontSize = 32.sp,
                                color = InkPrimary
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Your intelligent habit tracker & photographic proof-of-work ledger. Build unshakeable discipline.",
                                style = HabbitAtTypography.bodyMedium,
                                fontSize = 16.sp,
                                color = InkSecondary
                            )
                        }

                        Button(
                            onClick = { step = 2 },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Get Started",
                                    style = HabbitAtTypography.labelLarge,
                                    color = InkPrimary,
                                    fontSize = 16.sp
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(
                                    imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                                    contentDescription = null,
                                    tint = InkPrimary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }

                    2 -> {
                        // Step 2: Nickname Input (Auto Focus Keyboard + Elevator Grouping)
                        LaunchedEffect(Unit) {
                            focusRequester.requestFocus()
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f, fill = false),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "What should we call you?",
                                style = HabbitAtTypography.displayLarge,
                                fontSize = 26.sp,
                                color = InkPrimary
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "Enter your nickname for personalized daily greetings.",
                                style = HabbitAtTypography.bodyMedium,
                                color = InkSecondary
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            OutlinedTextField(
                                value = nicknameInput,
                                onValueChange = { nicknameInput = it },
                                singleLine = true,
                                placeholder = { Text("Enter your name...", color = InkMuted) },
                                textStyle = HabbitAtTypography.headlineMedium.copy(color = InkPrimary),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = SaffronPrimary,
                                    unfocusedBorderColor = GlassBorder,
                                    focusedContainerColor = CardSurface,
                                    unfocusedContainerColor = CardSurface
                                ),
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .focusRequester(focusRequester)
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            Button(
                                onClick = {
                                    if (nicknameInput.isNotBlank()) {
                                        userPrefs.nickname = nicknameInput
                                        step = 3
                                    }
                                },
                                enabled = nicknameInput.isNotBlank(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(52.dp),
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "Continue",
                                        style = HabbitAtTypography.labelLarge,
                                        color = InkPrimary,
                                        fontSize = 16.sp
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                                        contentDescription = null,
                                        tint = InkPrimary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }

                    3 -> {
                        // Step 3: Preset Habit Selection
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            Text(
                                text = "Choose initial habits",
                                style = HabbitAtTypography.displayLarge,
                                fontSize = 26.sp,
                                color = InkPrimary
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "Select habits to seed your discipline ledger or tap Skip.",
                                style = HabbitAtTypography.bodyMedium,
                                color = InkSecondary
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                items(presetList) { preset ->
                                    val isSelected = selectedPresets.contains(preset)

                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(16.dp))
                                            .clickable {
                                                if (isSelected) selectedPresets.remove(preset)
                                                else selectedPresets.add(preset)
                                            }
                                            .border(
                                                width = 1.dp,
                                                color = if (isSelected) SaffronPrimary else GlassBorder,
                                                shape = RoundedCornerShape(16.dp)
                                            ),
                                        shape = RoundedCornerShape(16.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = if (isSelected) SaffronGlow else CardSurface
                                        )
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(14.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            // Icon Badge
                                            Box(
                                                modifier = Modifier
                                                    .size(42.dp)
                                                    .clip(CircleShape)
                                                    .background(preset.iconBgColor),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Icon(
                                                    imageVector = preset.icon,
                                                    contentDescription = preset.name,
                                                    tint = preset.iconTint,
                                                    modifier = Modifier.size(22.dp)
                                                )
                                            }

                                            Spacer(modifier = Modifier.width(12.dp))

                                            Column(modifier = Modifier.weight(1f)) {
                                                Text(
                                                    text = preset.name,
                                                    style = HabbitAtTypography.titleMedium,
                                                    color = InkPrimary
                                                )
                                                Spacer(modifier = Modifier.height(2.dp))
                                                Text(
                                                    text = "${preset.durationMinutes} min • ${preset.frequency} • ${preset.description}",
                                                    style = HabbitAtTypography.bodyMedium,
                                                    color = InkSecondary,
                                                    fontSize = 12.sp,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                            }

                                            Spacer(modifier = Modifier.width(8.dp))

                                            Box(
                                                modifier = Modifier
                                                    .size(24.dp)
                                                    .clip(CircleShape)
                                                    .background(if (isSelected) SaffronPrimary else CardSurface)
                                                    .border(1.dp, if (isSelected) SaffronPrimary else GlassBorder, CircleShape),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                if (isSelected) {
                                                    Icon(
                                                        imageVector = Icons.Rounded.Check,
                                                        contentDescription = null,
                                                        tint = InkPrimary,
                                                        modifier = Modifier.size(16.dp)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            // Skip CTA
                            Button(
                                onClick = {
                                    if (isSubmitting) return@Button
                                    isSubmitting = true
                                    userPrefs.isOnboardingCompleted = true
                                    onOnboardingComplete()
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(50.dp),
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = IndigoLight)
                            ) {
                                Text(
                                    text = "Skip",
                                    style = HabbitAtTypography.labelLarge,
                                    color = IndigoSecondary
                                )
                            }

                            // Complete Setup CTA
                            Button(
                                onClick = {
                                    if (isSubmitting) return@Button
                                    isSubmitting = true
                                    coroutineScope.launch {
                                        // Save selected preset habits to Room
                                        for (preset in selectedPresets) {
                                            repository.insertHabit(
                                                Habit(
                                                    name = preset.name,
                                                    description = preset.description,
                                                    targetDurationMinutes = preset.durationMinutes,
                                                    proofDescription = preset.proofDescription,
                                                    reminderIntervalMinutes = 60,
                                                    frequency = preset.frequency
                                                )
                                            )
                                        }
                                        userPrefs.isOnboardingCompleted = true
                                        showCompletionAnimation = true
                                        delay(1800L)
                                        onOnboardingComplete()
                                    }
                                },
                                modifier = Modifier
                                    .weight(2f)
                                    .height(50.dp),
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary)
                            ) {
                                Text(
                                    text = "Complete Setup",
                                    style = HabbitAtTypography.labelLarge,
                                    color = InkPrimary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
