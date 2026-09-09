import SwiftUI

struct MainWindowView: View {
    @EnvironmentObject var syncService: DriveSyncService
    @State private var selectedHabitId: Int64? = nil
    @State private var showCreateHabitSheet: Bool = false

    @State private var newHabitName: String = ""
    @State private var newHabitDuration: String = "15"
    @State private var newHabitCategory: String = "Health"
    @State private var newHabitProof: String = "Daily photo proof"

    var body: some View {
        NavigationSplitView {
            // Sidebar List of Habits with explicit styling & width
            VStack(spacing: 0) {
                // Sidebar Header
                HStack {
                    Text("HabitAt Ledger")
                        .font(.system(size: 16, weight: .bold, design: .serif))
                        .foregroundColor(ThemeTokens.inkPrimary)
                    
                    Spacer()

                    Button(action: { showCreateHabitSheet = true }) {
                        Image(systemName: "plus.circle.fill")
                            .font(.system(size: 18))
                            .foregroundColor(ThemeTokens.saffronPrimary)
                    }
                    .buttonStyle(.plain)
                    .help("Add New Habit")
                }
                .padding(.horizontal, 16)
                .padding(.top, 14)
                .padding(.bottom, 10)

                Divider()

                if syncService.habits.isEmpty {
                    VStack(spacing: 12) {
                        Spacer()
                        Image(systemName: "tray")
                            .font(.system(size: 32))
                            .foregroundColor(ThemeTokens.inkMuted)
                        Text("No Habits Synced")
                            .font(.system(size: 13, weight: .medium))
                            .foregroundColor(ThemeTokens.inkSecondary)
                        Spacer()
                    }
                    .frame(maxWidth: .infinity, maxHeight: .infinity)
                } else {
                    List(syncService.habits, selection: $selectedHabitId) { habit in
                        let isDone = syncService.isHabitDoneToday(habitId: habit.id)

                        HStack(spacing: 10) {
                            Image(systemName: isDone ? "checkmark.circle.fill" : "circle.dashed")
                                .foregroundColor(isDone ? ThemeTokens.turmericGreenSuccess : ThemeTokens.inkMuted)
                                .font(.system(size: 16))

                            VStack(alignment: .leading, spacing: 2) {
                                Text(habit.name)
                                    .font(.system(size: 13, weight: .semibold))
                                    .foregroundColor(ThemeTokens.inkPrimary)
                                    .lineLimit(1)

                                Text("\(habit.targetDurationMinutes) min daily")
                                    .font(.system(size: 11))
                                    .foregroundColor(ThemeTokens.inkSecondary)
                                    .lineLimit(1)
                            }

                            Spacer()

                            // Streak Pill
                            HStack(spacing: 3) {
                                Image(systemName: "flame.fill")
                                    .font(.system(size: 9))
                                    .foregroundColor(ThemeTokens.saffronPrimary)
                                Text("\(habit.streak)")
                                    .font(.system(size: 11, weight: .bold))
                                    .foregroundColor(ThemeTokens.saffronPrimary)
                            }
                            .padding(.horizontal, 6)
                            .padding(.vertical, 2)
                            .background(ThemeTokens.saffronLight)
                            .cornerRadius(8)
                        }
                        .padding(.vertical, 4)
                        .tag(habit.id)
                    }
                    .listStyle(.sidebar)
                }

                Divider()

                // Sidebar Footer status
                HStack {
                    Circle()
                        .fill(syncService.isConfigured ? ThemeTokens.turmericGreenSuccess : ThemeTokens.saffronPrimary)
                        .frame(width: 6, height: 6)
                    Text(syncService.lastSyncedAtText)
                        .font(.system(size: 11))
                        .foregroundColor(ThemeTokens.inkSecondary)
                    Spacer()
                }
                .padding(.horizontal, 16)
                .padding(.vertical, 10)
                .background(ThemeTokens.cardSurface)
            }
            .navigationSplitViewColumnWidth(min: 250, ideal: 280, max: 340)
            .toolbar {
                ToolbarItem(placement: .automatic) {
                    Button(action: { syncService.loadMetadata() }) {
                        Image(systemName: "arrow.clockwise")
                    }
                    .help("Refresh Drive Sync")
                }
            }
        } detail: {
            if let habitId = selectedHabitId,
               let habit = syncService.habits.first(where: { $0.id == habitId }) {
                CalendarGridPaneView(habit: habit)
                    .background(ThemeTokens.warmIvory)
            } else {
                // Empty state when no habit selected or habits list is empty
                VStack(spacing: 16) {
                    ZStack {
                        Circle()
                            .fill(ThemeTokens.saffronLight)
                            .frame(width: 80, height: 80)
                        Image(systemName: "calendar.badge.plus")
                            .font(.system(size: 36))
                            .foregroundColor(ThemeTokens.saffronPrimary)
                    }

                    Text(syncService.habits.isEmpty ? "No Habits Tracked Yet" : "Select a Habit")
                        .font(.system(size: 22, weight: .bold, design: .serif))
                        .foregroundColor(ThemeTokens.inkPrimary)

                    Text(syncService.habits.isEmpty ?
                         "Connect your Google Drive account or click below to create your first habit." :
                         "Select a habit from the sidebar to view its photographic proof calendar.")
                        .font(.system(size: 13))
                        .foregroundColor(ThemeTokens.inkSecondary)
                        .multilineTextAlignment(.center)
                        .frame(maxWidth: 360)

                    Spacer().frame(height: 8)

                    Button(action: { showCreateHabitSheet = true }) {
                        HStack(spacing: 6) {
                            Image(systemName: "plus")
                            Text("Create Habit on Mac")
                        }
                        .font(.system(size: 13, weight: .semibold))
                        .padding(.horizontal, 16)
                        .padding(.vertical, 8)
                    }
                    .buttonStyle(.borderedProminent)
                    .tint(ThemeTokens.saffronPrimary)
                }
                .frame(maxWidth: .infinity, maxHeight: .infinity)
                .background(ThemeTokens.warmIvory)
            }
        }
        .frame(minWidth: 850, minHeight: 580)
        .sheet(isPresented: $showCreateHabitSheet) {
            VStack(alignment: .leading, spacing: 16) {
                Text("Create Habit on Mac")
                    .font(.system(size: 18, weight: .bold, design: .serif))
                    .foregroundColor(ThemeTokens.inkPrimary)

                Divider()

                VStack(alignment: .leading, spacing: 6) {
                    Text("Habit Name")
                        .font(.system(size: 12, weight: .medium))
                        .foregroundColor(ThemeTokens.inkPrimary)
                    TextField("e.g. Daily Meditation, Reading", text: $newHabitName)
                        .textFieldStyle(.roundedBorder)
                }

                VStack(alignment: .leading, spacing: 6) {
                    Text("Target Duration (minutes)")
                        .font(.system(size: 12, weight: .medium))
                        .foregroundColor(ThemeTokens.inkPrimary)
                    TextField("15", text: $newHabitDuration)
                        .textFieldStyle(.roundedBorder)
                }

                VStack(alignment: .leading, spacing: 6) {
                    Text("Category")
                        .font(.system(size: 12, weight: .medium))
                        .foregroundColor(ThemeTokens.inkPrimary)
                    TextField("Health, Intellect, Mindfulness", text: $newHabitCategory)
                        .textFieldStyle(.roundedBorder)
                }

                HStack {
                    Spacer()
                    Button("Cancel") {
                        showCreateHabitSheet = false
                    }
                    .buttonStyle(.plain)

                    Button("Create Habit") {
                        if !newHabitName.trimmingCharacters(in: .whitespaces).isEmpty {
                            let duration = Int(newHabitDuration) ?? 15
                            syncService.createHabit(
                                name: newHabitName.trimmingCharacters(in: .whitespaces),
                                targetDuration: duration,
                                category: newHabitCategory,
                                proofDescription: newHabitProof
                            )
                            if selectedHabitId == nil, let first = syncService.habits.last {
                                selectedHabitId = first.id
                            }
                            newHabitName = ""
                            showCreateHabitSheet = false
                        }
                    }
                    .buttonStyle(.borderedProminent)
                    .tint(ThemeTokens.saffronPrimary)
                }
                .padding(.top, 8)
            }
            .padding(20)
            .frame(width: 380)
        }
        .onAppear {
            NSApp.activate(ignoringOtherApps: true)
            if selectedHabitId == nil, let first = syncService.habits.first {
                selectedHabitId = first.id
            }
        }
    }
}
