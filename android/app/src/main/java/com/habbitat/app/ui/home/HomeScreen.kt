package com.habbitat.app.ui.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.GridView
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.SelfImprovement
import androidx.compose.material.icons.rounded.ViewAgenda
import androidx.compose.material.icons.rounded.ViewList
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.core.content.ContextCompat
import com.habbitat.app.data.HabitScheduleHelper
import com.habbitat.app.data.UserPreferences
import com.habbitat.app.data.local.entity.Habit
import com.habbitat.app.ui.components.BackgroundMotif
import com.habbitat.app.ui.components.MotifVariant
import com.habbitat.app.ui.components.PermissionDialog
import com.habbitat.app.ui.theme.CardSurface
import com.habbitat.app.ui.theme.GlassBorder
import com.habbitat.app.ui.theme.HabbitAtTypography
import com.habbitat.app.ui.theme.IndigoLight
import com.habbitat.app.ui.theme.IndigoSecondary
import com.habbitat.app.ui.theme.InkMuted
import com.habbitat.app.ui.theme.InkPrimary
import com.habbitat.app.ui.theme.InkSecondary
import com.habbitat.app.ui.theme.SaffronPrimary
import com.habbitat.app.ui.theme.WarmIvory

enum class HomeViewMode {
    COMPACT_LIST,   // Default compact list
    DETAILED_CARD,  // Expanded detailed cards
    GRID            // 2-column grid
}

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onOpenProofCapture: (Long) -> Unit = {},
    onOpenHabitDetails: (Long) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val userPrefs = remember { UserPreferences(context) }
    val habits by viewModel.habits.collectAsState()
    val completionRecords by viewModel.completionRecords.collectAsState()
    val isCreateSheetOpen by viewModel.isCreateSheetOpen.collectAsState()

    var showPermissionDialog by remember { mutableStateOf(false) }
    var selectedHabitForDetail by remember { mutableStateOf<Habit?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var viewMode by remember { mutableStateOf(HomeViewMode.COMPACT_LIST) }

    // Organize records per habit
    val recordsByHabit = remember(completionRecords) {
        completionRecords.groupBy { it.habitId }
    }

    // Filter habits by search query
    val filteredHabits = remember(habits, searchQuery) {
        if (searchQuery.isBlank()) habits
        else habits.filter {
            it.name.contains(searchQuery, ignoreCase = true) ||
            it.proofDescription.contains(searchQuery, ignoreCase = true)
        }
    }

    // Calculate due status for all habits to compute accurate banner count
    val dueStatuses = remember(habits, completionRecords) {
        habits.associate { habit ->
            val habitRecords = recordsByHabit[habit.id] ?: emptyList()
            habit.id to HabitScheduleHelper.getDueStatusInfo(habit, habitRecords)
        }
    }

    val totalDueToday = remember(dueStatuses) {
        dueStatuses.values.count { it.isDueToday || it.isCompletedPeriod }
    }

    val completedTodayCount = remember(dueStatuses) {
        dueStatuses.values.count { it.isCompletedPeriod }
    }

    val permissionsToRequest = remember {
        val list = mutableListOf<String>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                list.add(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
        list.toTypedArray()
    }

    val multiplePermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { _ ->
        showPermissionDialog = false
    }

    val topInset = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = WarmIvory,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.openCreateSheet() },
                containerColor = SaffronPrimary,
                contentColor = InkPrimary,
                shape = CircleShape,
                modifier = Modifier.padding(bottom = 88.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add Habit",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            BackgroundMotif(variant = MotifVariant.KOLAM_DOT_GRID)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
                    .padding(top = topInset + 12.dp)
            ) {
                // Header Title with Welcome Greeting Above App Name
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        val displayName = userPrefs.nickname.ifBlank { "User" }
                        Text(
                            text = "Welcome $displayName,",
                            style = HabbitAtTypography.bodyMedium.copy(
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium
                            ),
                            color = SaffronPrimary
                        )
                        Spacer(modifier = Modifier.height(1.dp))
                        Text(
                            text = "HabitAt",
                            style = HabbitAtTypography.displayMedium.copy(
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            color = InkPrimary
                        )
                        Spacer(modifier = Modifier.height(1.dp))
                        Text(
                            text = "Today's Discipline & Proof Ledger",
                            style = HabbitAtTypography.labelMedium.copy(
                                fontSize = 12.sp
                            ),
                            color = InkSecondary
                        )
                    }

                    IconButton(onClick = { showPermissionDialog = true }) {
                        Icon(
                            imageVector = Icons.Rounded.Notifications,
                            contentDescription = "Permissions",
                            tint = IndigoSecondary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Search Bar & View Mode Switcher Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Search Input Box
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(14.dp))
                            .background(CardSurface)
                            .border(width = 1.dp, color = GlassBorder, shape = RoundedCornerShape(14.dp))
                            .padding(horizontal = 12.dp, vertical = 10.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Rounded.Search,
                                contentDescription = "Search Habits",
                                tint = InkMuted,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(modifier = Modifier.weight(1f)) {
                                if (searchQuery.isEmpty()) {
                                    Text(
                                        text = "Search habits...",
                                        style = HabbitAtTypography.bodyMedium,
                                        color = InkMuted
                                    )
                                }
                                BasicTextField(
                                    value = searchQuery,
                                    onValueChange = { searchQuery = it },
                                    singleLine = true,
                                    textStyle = HabbitAtTypography.bodyMedium.copy(color = InkPrimary),
                                    cursorBrush = SolidColor(SaffronPrimary),
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                            if (searchQuery.isNotEmpty()) {
                                IconButton(
                                    onClick = { searchQuery = "" },
                                    modifier = Modifier.size(18.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Clear,
                                        contentDescription = "Clear",
                                        tint = InkSecondary
                                    )
                                }
                            }
                        }
                    }

                    // View Mode Switcher Buttons (Centered 34dp touch targets)
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(CardSurface)
                            .border(1.dp, GlassBorder, RoundedCornerShape(12.dp))
                            .padding(3.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(9.dp))
                                .background(if (viewMode == HomeViewMode.COMPACT_LIST) IndigoLight else CardSurface)
                                .clickable { viewMode = HomeViewMode.COMPACT_LIST },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.ViewList,
                                contentDescription = "Compact List",
                                tint = if (viewMode == HomeViewMode.COMPACT_LIST) IndigoSecondary else InkMuted,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(9.dp))
                                .background(if (viewMode == HomeViewMode.DETAILED_CARD) IndigoLight else CardSurface)
                                .clickable { viewMode = HomeViewMode.DETAILED_CARD },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.ViewAgenda,
                                contentDescription = "Detailed Cards",
                                tint = if (viewMode == HomeViewMode.DETAILED_CARD) IndigoSecondary else InkMuted,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(9.dp))
                                .background(if (viewMode == HomeViewMode.GRID) IndigoLight else CardSurface)
                                .clickable { viewMode = HomeViewMode.GRID },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.GridView,
                                contentDescription = "Grid View",
                                tint = if (viewMode == HomeViewMode.GRID) IndigoSecondary else InkMuted,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                if (filteredHabits.isEmpty()) {
                    // Empty State
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.SelfImprovement,
                                contentDescription = "Empty Habits",
                                tint = InkMuted,
                                modifier = Modifier.size(56.dp)
                            )
                            Spacer(modifier = Modifier.height(14.dp))
                            Text(
                                text = if (searchQuery.isNotBlank()) "No matching habits" else "No habits tracked yet",
                                style = HabbitAtTypography.headlineMedium,
                                color = InkPrimary
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = if (searchQuery.isNotBlank()) "Try searching for a different term." else "Tap + to create a habit and start building your photographic record of discipline.",
                                style = HabbitAtTypography.bodyMedium,
                                textAlign = TextAlign.Center,
                                color = InkSecondary
                            )
                        }
                    }
                } else {
                    when (viewMode) {
                        HomeViewMode.COMPACT_LIST -> {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                contentPadding = PaddingValues(bottom = 110.dp)
                            ) {
                                itemsIndexed(
                                    items = filteredHabits,
                                    key = { _, habit -> habit.id }
                                ) { index, habit ->
                                    val habitRecords = recordsByHabit[habit.id] ?: emptyList()
                                    val dueStatus = dueStatuses[habit.id] ?: HabitScheduleHelper.getDueStatusInfo(habit, habitRecords)
                                    val streak = HabitScheduleHelper.calculateStreak(habitRecords)

                                    AnimatedVisibility(
                                        visible = true,
                                        enter = slideInVertically(initialOffsetY = { 30 * (index + 1) }) + fadeIn()
                                    ) {
                                        CompactHabitRow(
                                            habit = habit,
                                            streakCount = streak,
                                            dueStatus = dueStatus,
                                            onOpenDetails = { selectedHabitForDetail = habit },
                                            onProofClick = {
                                                if (!dueStatus.isCompletedPeriod) {
                                                    onOpenProofCapture(habit.id)
                                                }
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        HomeViewMode.DETAILED_CARD -> {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(14.dp),
                                contentPadding = PaddingValues(bottom = 110.dp)
                            ) {
                                itemsIndexed(
                                    items = filteredHabits,
                                    key = { _, habit -> habit.id }
                                ) { index, habit ->
                                    val habitRecords = recordsByHabit[habit.id] ?: emptyList()
                                    val dueStatus = dueStatuses[habit.id] ?: HabitScheduleHelper.getDueStatusInfo(habit, habitRecords)
                                    val streak = HabitScheduleHelper.calculateStreak(habitRecords)

                                    AnimatedVisibility(
                                        visible = true,
                                        enter = slideInVertically(initialOffsetY = { 30 * (index + 1) }) + fadeIn()
                                    ) {
                                        HabitCard(
                                            habit = habit,
                                            streakCount = streak,
                                            isCompletedToday = dueStatus.isCompletedPeriod,
                                            onOpenDetails = { selectedHabitForDetail = habit },
                                            onProofClick = {
                                                if (!dueStatus.isCompletedPeriod) {
                                                    onOpenProofCapture(habit.id)
                                                }
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        HomeViewMode.GRID -> {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                contentPadding = PaddingValues(bottom = 110.dp)
                            ) {
                                itemsIndexed(
                                    items = filteredHabits,
                                    key = { _, habit -> habit.id }
                                ) { index, habit ->
                                    val habitRecords = recordsByHabit[habit.id] ?: emptyList()
                                    val dueStatus = dueStatuses[habit.id] ?: HabitScheduleHelper.getDueStatusInfo(habit, habitRecords)
                                    val streak = HabitScheduleHelper.calculateStreak(habitRecords)

                                    AnimatedVisibility(
                                        visible = true,
                                        enter = slideInVertically(initialOffsetY = { 30 * (index + 1) }) + fadeIn()
                                    ) {
                                        GridHabitCard(
                                            habit = habit,
                                            streakCount = streak,
                                            dueStatus = dueStatus,
                                            onOpenDetails = { selectedHabitForDetail = habit },
                                            onProofClick = {
                                                if (!dueStatus.isCompletedPeriod) {
                                                    onOpenProofCapture(habit.id)
                                                }
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Habit Detail Modal Sheet
            selectedHabitForDetail?.let { targetHabit ->
                val habitRecords = recordsByHabit[targetHabit.id] ?: emptyList()
                val dueStatus = dueStatuses[targetHabit.id] ?: HabitScheduleHelper.getDueStatusInfo(targetHabit, habitRecords)

                HabitDetailModal(
                    habit = targetHabit,
                    completionRecords = habitRecords,
                    isCompletedToday = dueStatus.isCompletedPeriod,
                    onDismiss = { selectedHabitForDetail = null },
                    onOpenProofCapture = {
                        onOpenProofCapture(targetHabit.id)
                    },
                    onQuickMarkCompleted = {
                        viewModel.simulateCompletion(context, targetHabit.id)
                    },
                    onUpdateHabit = { updated ->
                        viewModel.updateHabit(updated)
                    },
                    onDeleteHabit = { deleted ->
                        viewModel.deleteHabit(deleted)
                    }
                )
            }

            if (isCreateSheetOpen) {
                CreateHabitSheet(
                    onDismiss = { viewModel.closeCreateSheet() },
                    onAddHabit = { name, description, duration, proof, interval, frequency ->
                        viewModel.addHabit(context, name, description, duration, proof, interval, frequency)
                    }
                )
            }

            if (showPermissionDialog) {
                PermissionDialog(
                    onDismiss = { showPermissionDialog = false },
                    onRequestPermissions = {
                        showPermissionDialog = false
                        if (permissionsToRequest.isNotEmpty()) {
                            multiplePermissionLauncher.launch(permissionsToRequest)
                        } else {
                            try {
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                    data = Uri.fromParts("package", context.packageName, null)
                                }
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                // Fallback
                            }
                        }
                    }
                )
            }
        }
    }
}
