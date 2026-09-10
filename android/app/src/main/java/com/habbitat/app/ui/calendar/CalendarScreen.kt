package com.habitAt.app.ui.calendar

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.habitAt.app.data.local.entity.CompletionRecord
import com.habitAt.app.ui.calendar.components.MonthGrid
import com.habitAt.app.ui.calendar.components.ProofDetailModal
import com.habitAt.app.ui.calendar.components.StreakStatsBlock
import com.habitAt.app.ui.components.BackgroundMotif
import com.habitAt.app.ui.components.MotifVariant
import com.habitAt.app.ui.theme.CardSurface
import com.habitAt.app.ui.theme.GlassBorder
import com.habitAt.app.ui.theme.habitAtTypography
import com.habitAt.app.ui.theme.IndigoLight
import com.habitAt.app.ui.theme.IndigoSecondary
import com.habitAt.app.ui.theme.InkMuted
import com.habitAt.app.ui.theme.InkPrimary
import com.habitAt.app.ui.theme.InkSecondary
import com.habitAt.app.ui.theme.SaffronPrimary
import com.habitAt.app.ui.theme.WarmIvory

import androidx.activity.compose.BackHandler

@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel,
    onNavigateToHome: () -> Unit = {},
    onOpenCreateHabit: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val habits by viewModel.habits.collectAsState()
    val currentHabit by viewModel.currentHabit.collectAsState()
    val records by viewModel.currentRecords.collectAsState()

    val topInset = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    var searchQuery by remember { mutableStateOf("") }
    var selectedFrequencyFilter by remember { mutableStateOf("ALL") }

    var selectedRecordForProofModal: CompletionRecord? by remember { mutableStateOf(null) }
    var selectedDateForBreakdown: String? by remember { mutableStateOf(null) }

    BackHandler(enabled = true) {
        if (selectedRecordForProofModal != null) {
            selectedRecordForProofModal = null
        } else if (selectedDateForBreakdown != null) {
            selectedDateForBreakdown = null
        } else {
            onNavigateToHome()
        }
    }

    val filteredHabits = remember(habits, searchQuery, selectedFrequencyFilter) {
        habits.filter { habit ->
            val matchesSearch = searchQuery.isBlank() || habit.name.contains(searchQuery, ignoreCase = true)
            val matchesFreq = selectedFrequencyFilter == "ALL" || habit.frequency.equals(selectedFrequencyFilter, ignoreCase = true)
            matchesSearch && matchesFreq
        }
    }

    val stats = remember(records) { viewModel.calculateStats(records) }
    val monthGrids = remember(records) { viewModel.buildMonthGrids(records) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(WarmIvory)
    ) {
        BackgroundMotif(variant = MotifVariant.PLEY_MANGO_LEAF)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .padding(top = topInset + 12.dp)
        ) {
            // Header
            Text(
                text = "Photographic Ledger",
                style = habitAtTypography.displayMedium,
                color = InkPrimary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Proof-of-Work History & Calendar",
                style = habitAtTypography.bodyMedium,
                color = InkSecondary
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Search Bar & Frequency Filters
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
                                    text = "Search ledger...",
                                    style = habitAtTypography.bodyMedium,
                                    color = InkMuted
                                )
                            }
                            BasicTextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                singleLine = true,
                                textStyle = habitAtTypography.bodyMedium.copy(color = InkPrimary),
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
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Frequency Filter Chips Row
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                val filters = listOf("ALL", "DAILY", "WEEKLY", "MONTHLY")
                items(filters) { tag ->
                    val isSelected = selectedFrequencyFilter == tag
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(if (isSelected) SaffronPrimary else CardSurface)
                            .border(1.dp, if (isSelected) SaffronPrimary else GlassBorder, CircleShape)
                            .clickable { selectedFrequencyFilter = tag }
                            .padding(horizontal = 12.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = tag,
                            style = habitAtTypography.labelSmall,
                            color = if (isSelected) InkPrimary else InkSecondary
                        )
                    }
                }

                if (filteredHabits.isNotEmpty()) {
                    items(filteredHabits) { habit ->
                        val isSelected = habit.id == currentHabit?.id
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(if (isSelected) IndigoLight else CardSurface)
                                .border(1.dp, if (isSelected) IndigoSecondary else GlassBorder, CircleShape)
                                .clickable { viewModel.selectHabit(habit.id) }
                                .padding(horizontal = 12.dp, vertical = 5.dp)
                        ) {
                            Text(
                                text = habit.name,
                                style = habitAtTypography.labelSmall,
                                color = if (isSelected) IndigoSecondary else InkSecondary
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Streak Stats Block
            StreakStatsBlock(stats = stats)

            Spacer(modifier = Modifier.height(16.dp))

            if (filteredHabits.isEmpty()) {
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
                            imageVector = Icons.Rounded.CalendarToday,
                            contentDescription = null,
                            tint = InkMuted,
                            modifier = Modifier.size(56.dp)
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = if (habits.isEmpty()) "No Habits Tracked" else "No Matching Habits",
                            style = habitAtTypography.headlineMedium,
                            color = InkPrimary
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = if (habits.isEmpty()) "Create a habit to build your photographic ledger of discipline." else "Try clearing filters or search query to view proof ledger.",
                            style = habitAtTypography.bodyMedium,
                            textAlign = TextAlign.Center,
                            color = InkSecondary
                        )

                        if (habits.isEmpty()) {
                            Spacer(modifier = Modifier.height(18.dp))
                            androidx.compose.material3.Button(
                                onClick = onOpenCreateHabit,
                                colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Rounded.Add,
                                        contentDescription = "Create Habit",
                                        tint = InkPrimary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(text = "Create Habit", color = InkPrimary)
                                }
                            }
                        }
                    }
                }
            } else {
                // Monthly Grids
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    contentPadding = PaddingValues(bottom = 100.dp)
                ) {
                    items(monthGrids) { monthGridData ->
                        MonthGrid(
                            monthData = monthGridData,
                            onTileClick = { dayTileData ->
                                if (dayTileData.dayOfMonth != -1 && dayTileData.dateString.isNotBlank()) {
                                    if (dayTileData.record != null) {
                                        selectedRecordForProofModal = dayTileData.record
                                    } else {
                                        selectedDateForBreakdown = dayTileData.dateString
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }

        // Full Proof Detail Modal
        val proofRecord = selectedRecordForProofModal
        if (proofRecord != null) {
            ProofDetailModal(
                habit = currentHabit,
                record = proofRecord,
                onDismiss = { selectedRecordForProofModal = null }
            )
        }

        // Day Breakdown Modal
        selectedDateForBreakdown?.let { dateStr ->
            val dateRecords = records.filter { it.date == dateStr }
            DayBreakdownModal(
                dateString = dateStr,
                allHabits = habits,
                recordsForDate = dateRecords,
                onDismiss = { selectedDateForBreakdown = null }
            )
        }
    }
}
