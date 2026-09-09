package com.habbitat.app.ui.settings

import android.widget.Toast
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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Cloud
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Key
import androidx.compose.material.icons.rounded.Code
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.NotificationsActive
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Security
import androidx.compose.material.icons.rounded.Shield
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.rememberCoroutineScope
import com.habbitat.app.BuildConfig
import com.habbitat.app.HabbitAtApp
import com.habbitat.app.data.HabitScheduleHelper
import com.habbitat.app.data.UserPreferences
import com.habbitat.app.data.local.entity.Habit
import com.habbitat.app.notifications.LlmNotificationCopywriter
import com.habbitat.app.notifications.NotificationHelper
import com.habbitat.app.sync.GoogleDriveSyncManager
import com.habbitat.app.ui.components.BackgroundMotif
import com.habbitat.app.ui.components.MotifVariant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.habbitat.app.ui.theme.CardSurface
import com.habbitat.app.ui.theme.GlassBorder
import com.habbitat.app.ui.theme.HabbitAtTypography
import com.habbitat.app.ui.theme.IndigoLight
import com.habbitat.app.ui.theme.IndigoSecondary
import com.habbitat.app.ui.theme.InkPrimary
import com.habbitat.app.ui.theme.InkSecondary
import com.habbitat.app.ui.theme.SaffronLight
import com.habbitat.app.ui.theme.SaffronPrimary
import com.habbitat.app.ui.theme.TerracottaLight
import com.habbitat.app.ui.theme.TerracottaTertiary
import com.habbitat.app.ui.theme.TurmericGreenSuccess
import com.habbitat.app.ui.theme.TurmericLight

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val topInset = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    val aiVerificationConfigured = BuildConfig.AI_VERIFICATION_API_KEY.isNotBlank()
    val aiNotificationConfigured = BuildConfig.AI_NOTIFICATION_API_KEY.isNotBlank()
    val driveConfigured = GoogleDriveSyncManager().isConfigured()

    val coroutineScope = rememberCoroutineScope()
    var isGeneratingTestNotif by remember { mutableStateOf(false) }
    val repository = remember { (context.applicationContext as HabbitAtApp).repository }

    val userPrefs = remember { UserPreferences(context) }
    var currentNickname by remember { mutableStateOf(userPrefs.nickname) }
    var showNicknameDialog by remember { mutableStateOf(false) }
    var nicknameInput by remember { mutableStateOf(currentNickname) }

    var selectedTone by remember { mutableStateOf("Motivational") }
    var showTermsSheet by remember { mutableStateOf(false) }
    var showStatusSheet by remember { mutableStateOf(false) }

    val toneOptions = listOf(
        "Motivational",
        "Encouraging",
        "Direct & Precise",
        "Daily Summary",
        "Celebratory"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(top = topInset)
    ) {
        BackgroundMotif(variant = MotifVariant.KOLAM_DOT_GRID)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            Text(
                text = "Settings",
                style = HabbitAtTypography.displayMedium,
                color = InkPrimary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Preferences, Privacy & Sync Controls",
                style = HabbitAtTypography.bodyMedium,
                color = InkSecondary
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Reminders
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = GlassBorder, shape = RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardSurface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Rounded.Notifications,
                                contentDescription = null,
                                tint = SaffronPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Reminders",
                                style = HabbitAtTypography.titleMedium,
                                color = InkPrimary
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(TurmericLight)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "Active",
                                style = HabbitAtTypography.labelMedium,
                                color = TurmericGreenSuccess
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Keep gentle, timely prompts for the habits you are building.",
                        style = HabbitAtTypography.bodyMedium,
                        color = InkSecondary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            if (isGeneratingTestNotif) return@Button
                            isGeneratingTestNotif = true
                            coroutineScope.launch(Dispatchers.IO) {
                                try {
                                    var habits = repository.getHabitsList()
                                    if (habits.isEmpty()) {
                                        val defaultHabit = Habit(
                                            name = "Morning Workout",
                                            targetDurationMinutes = 20,
                                            proofDescription = "Photo of gym gear or workout space",
                                            reminderIntervalMinutes = 60,
                                            frequency = "DAILY"
                                        )
                                        val newId = repository.insertHabit(defaultHabit)
                                        habits = listOf(defaultHabit.copy(id = newId))
                                    }

                                    val targetHabit = habits.first()
                                    val records = repository.getAllCompletionRecordsList().filter { it.habitId == targetHabit.id }
                                    val streak = HabitScheduleHelper.calculateStreak(records)

                                    val copywriter = LlmNotificationCopywriter()
                                    val copy = copywriter.generateCopy(
                                        habitName = targetHabit.name,
                                        frequency = targetHabit.frequency,
                                        targetDurationMinutes = targetHabit.targetDurationMinutes,
                                        streakCount = streak,
                                        escalationLevel = 1,
                                        tone = selectedTone
                                    )

                                    withContext(Dispatchers.Main) {
                                        NotificationHelper.showNotification(
                                            context = context,
                                            habitId = targetHabit.id,
                                            title = copy.title,
                                            message = copy.message,
                                            escalationLevel = 1
                                        )
                                        Toast.makeText(context, "AI Demo Reminder sent for '${targetHabit.name}'!", Toast.LENGTH_SHORT).show()
                                    }
                                } catch (e: Exception) {
                                    withContext(Dispatchers.Main) {
                                        Toast.makeText(context, "Reminder error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                                    }
                                } finally {
                                    isGeneratingTestNotif = false
                                }
                            }
                        },
                        enabled = !isGeneratingTestNotif,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = SaffronLight),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (isGeneratingTestNotif) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(18.dp),
                                    color = SaffronPrimary,
                                    strokeWidth = 2.dp
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Generating AI reminder...",
                                    style = HabbitAtTypography.labelLarge,
                                    color = SaffronPrimary
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Rounded.NotificationsActive,
                                    contentDescription = null,
                                    tint = SaffronPrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Send a test reminder (AI)",
                                    style = HabbitAtTypography.labelLarge,
                                    color = SaffronPrimary
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // AI Persona Selector Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = GlassBorder, shape = RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardSurface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Notification Assistant Persona",
                        style = HabbitAtTypography.titleMedium,
                        color = InkPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Choose the communication style for habit reminders",
                        style = HabbitAtTypography.bodyMedium,
                        color = InkSecondary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        toneOptions.forEach { tone ->
                            val isSelected = tone == selectedTone
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(if (isSelected) SaffronLight else CardSurface)
                                    .border(
                                        width = 1.dp,
                                        color = if (isSelected) SaffronPrimary else GlassBorder,
                                        shape = CircleShape
                                    )
                                    .clickable { selectedTone = tone }
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = tone,
                                    style = HabbitAtTypography.labelMedium,
                                    color = if (isSelected) SaffronPrimary else InkPrimary
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Change Nickname Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = GlassBorder, shape = RoundedCornerShape(16.dp))
                    .clickable {
                        nicknameInput = currentNickname
                        showNicknameDialog = true
                    },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardSurface)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(SaffronLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Person,
                            contentDescription = null,
                            tint = SaffronPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Change Nickname",
                            style = HabbitAtTypography.titleMedium,
                            color = InkPrimary
                        )
                        Text(
                            text = "Current: $currentNickname",
                            style = HabbitAtTypography.bodyMedium,
                            color = InkSecondary
                        )
                    }
                    Icon(
                        imageVector = Icons.Rounded.ChevronRight,
                        contentDescription = null,
                        tint = InkSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Developer Credits Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = GlassBorder, shape = RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardSurface)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(IndigoLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Code,
                            contentDescription = null,
                            tint = IndigoSecondary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Developer Credits",
                            style = HabbitAtTypography.titleMedium,
                            color = InkPrimary
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Made by Param Khodiyar • Proof or it didn't happen 📸",
                            style = HabbitAtTypography.bodyMedium,
                            color = InkSecondary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Terms of Service & Privacy Policy Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = GlassBorder, shape = RoundedCornerShape(16.dp))
                    .clickable { showTermsSheet = true },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardSurface)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(IndigoLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Shield,
                            contentDescription = null,
                            tint = IndigoSecondary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Terms of Service & Privacy Policy",
                            style = HabbitAtTypography.titleMedium,
                            color = InkPrimary
                        )
                        Text(
                            text = "Sovereign data ownership & privacy commitments",
                            style = HabbitAtTypography.bodyMedium,
                            color = InkSecondary
                        )
                    }
                    Icon(
                        imageVector = Icons.Rounded.ChevronRight,
                        contentDescription = null,
                        tint = InkSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // App Status
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = GlassBorder, shape = RoundedCornerShape(16.dp))
                    .clickable { showStatusSheet = true },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardSurface)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(TurmericLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Cloud,
                            contentDescription = null,
                            tint = TurmericGreenSuccess,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "App status",
                            style = HabbitAtTypography.titleMedium,
                            color = InkPrimary
                        )
                        Text(
                            text = "Check proof, reminder, and sync availability",
                            style = HabbitAtTypography.bodyMedium,
                            color = InkSecondary
                        )
                    }
                    Icon(
                        imageVector = Icons.Rounded.ChevronRight,
                        contentDescription = null,
                        tint = InkSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(96.dp)) // Inset clearance for floating bottom bar
        }

        // Terms of Service & Privacy Policy Modal Sheet
        if (showTermsSheet) {
            ModalBottomSheet(
                onDismissRequest = { showTermsSheet = false },
                sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
                containerColor = CardSurface
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = "Terms of Service & Privacy Policy",
                        style = HabbitAtTypography.displaySmall,
                        color = InkPrimary
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "HabitAt Sovereign Privacy Guarantee",
                        style = HabbitAtTypography.bodyMedium,
                        color = InkSecondary
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    LegalSectionHeader(title = "1. Sovereign Data Ownership")
                    LegalBodyText(
                        text = "HabitAt operates on a zero-tracking, sovereign model. All habit logs, photo proof records, and personal metrics remain on your local device and your private Google Drive folder."
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    LegalSectionHeader(title = "2. Photo Proof & AI Verification")
                    LegalBodyText(
                        text = "Photos captured for proof verification are analyzed locally or via secure AI vision APIs solely to confirm habit completion. Photos are never shared, sold, or used for model training."
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    LegalSectionHeader(title = "3. Google Drive Sync Scopes")
                    LegalBodyText(
                        text = "HabitAt uses restricted app-data scope (drive.file) which permits access strictly to files created by HabitAt (sync-metadata.json). HabitAt cannot read or touch any other files in your Google Drive."
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    LegalSectionHeader(title = "4. Terms of Service")
                    LegalBodyText(
                        text = "HabitAt is provided 'as is' for personal habit tracking. By using HabitAt, you retain full ownership of your data and habit ledgers."
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = { showTermsSheet = false },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "I Understand & Agree",
                            style = HabbitAtTypography.labelLarge,
                            color = CardSurface
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }

        if (showStatusSheet) {
            ModalBottomSheet(
                onDismissRequest = { showStatusSheet = false },
                sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
                containerColor = CardSurface
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                ) {
                    Text(
                        text = "App status",
                        style = HabbitAtTypography.displaySmall,
                        color = InkPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Availability of the services that support your habit flow.",
                        style = HabbitAtTypography.bodyMedium,
                        color = InkSecondary
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    ConfigStatusRow(title = "Photo proof checking", isConfigured = aiVerificationConfigured)
                    Spacer(modifier = Modifier.height(12.dp))
                    ConfigStatusRow(title = "Smart reminder messages", isConfigured = aiNotificationConfigured)
                    Spacer(modifier = Modifier.height(12.dp))
                    ConfigStatusRow(title = "Device sync", isConfigured = driveConfigured)
                    Spacer(modifier = Modifier.height(28.dp))
                }
            }
        }
        if (showNicknameDialog) {
            AlertDialog(
                onDismissRequest = { showNicknameDialog = false },
                title = {
                    Text(
                        text = "Change Nickname",
                        style = HabbitAtTypography.headlineMedium,
                        color = InkPrimary
                    )
                },
                text = {
                    Column {
                        Text(
                            text = "Enter your nickname for daily welcome greetings.",
                            style = HabbitAtTypography.bodyMedium,
                            color = InkSecondary
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedTextField(
                            value = nicknameInput,
                            onValueChange = { nicknameInput = it },
                            singleLine = true,
                            textStyle = HabbitAtTypography.bodyMedium.copy(color = InkPrimary),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = SaffronPrimary,
                                unfocusedBorderColor = GlassBorder
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (nicknameInput.isNotBlank()) {
                                userPrefs.nickname = nicknameInput
                                currentNickname = nicknameInput
                                showNicknameDialog = false
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary)
                    ) {
                        Text("Save", color = InkPrimary)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showNicknameDialog = false }) {
                        Text("Cancel", color = InkSecondary)
                    }
                },
                containerColor = CardSurface,
                shape = RoundedCornerShape(16.dp)
            )
        }
    }
}

@Composable
private fun ConfigStatusRow(
    title: String,
    isConfigured: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = HabbitAtTypography.bodyMedium,
            color = InkPrimary,
            modifier = Modifier.weight(1f)
        )

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(if (isConfigured) TurmericLight else TerracottaLight)
                .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = if (isConfigured) Icons.Rounded.CheckCircle else Icons.Rounded.Warning,
                    contentDescription = null,
                    tint = if (isConfigured) TurmericGreenSuccess else TerracottaTertiary,
                    modifier = Modifier.size(13.dp)
                )
                Text(
                    text = if (isConfigured) "Active" else "Standard",
                    style = HabbitAtTypography.labelMedium,
                    color = if (isConfigured) TurmericGreenSuccess else TerracottaTertiary
                )
            }
        }
    }
}

@Composable
private fun LegalSectionHeader(title: String) {
    Text(
        text = title,
        style = HabbitAtTypography.titleMedium,
        color = InkPrimary
    )
}

@Composable
private fun LegalBodyText(text: String) {
    Text(
        text = text,
        style = HabbitAtTypography.bodyMedium,
        color = InkSecondary,
        modifier = Modifier.padding(top = 2.dp)
    )
}
