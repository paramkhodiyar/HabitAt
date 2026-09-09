import SwiftUI

struct MenuBarPopoverView: View {
    @EnvironmentObject var syncService: DriveSyncService

    var body: some View {
        VStack(alignment: .leading, spacing: 14) {
            // Header Bar
            HStack {
                Image(systemName: "flame.fill")
                    .foregroundColor(ThemeTokens.saffronPrimary)
                    .font(.title3)
                
                Text("HabitAt Sync")
                    .font(.system(size: 15, weight: .bold))
                    .foregroundColor(ThemeTokens.inkPrimary)
                
                Spacer()
                
                Text("macOS")
                    .font(.system(size: 10, weight: .medium))
                    .padding(.horizontal, 6)
                    .padding(.vertical, 2)
                    .background(ThemeTokens.saffronLight)
                    .foregroundColor(ThemeTokens.saffronPrimary)
                    .cornerRadius(4)
            }
            .padding(.bottom, 2)

            Divider()

            // Habit Status List
            if syncService.habits.isEmpty {
                VStack(spacing: 8) {
                    Image(systemName: "tray")
                        .font(.system(size: 24))
                        .foregroundColor(ThemeTokens.inkMuted)
                    Text("No habits synced yet")
                        .font(.system(size: 12))
                        .foregroundColor(ThemeTokens.inkSecondary)
                }
                .padding(.vertical, 16)
                .frame(maxWidth: .infinity)
            } else {
                VStack(spacing: 8) {
                    ForEach(syncService.habits) { habit in
                        let isDone = syncService.isHabitDoneToday(habitId: habit.id)

                        HStack(spacing: 10) {
                            Image(systemName: isDone ? "checkmark.circle.fill" : "circle.dashed")
                                .foregroundColor(isDone ? ThemeTokens.turmericGreenSuccess : ThemeTokens.inkMuted)
                                .font(.system(size: 16))

                            VStack(alignment: .leading, spacing: 2) {
                                Text(habit.name)
                                    .font(.system(size: 13, weight: .medium))
                                    .foregroundColor(ThemeTokens.inkPrimary)

                                Text("\(habit.targetDurationMinutes) min • \(habit.category)")
                                    .font(.system(size: 10))
                                    .foregroundColor(ThemeTokens.inkSecondary)
                            }

                            Spacer()

                            // Streak Pill Badge
                            HStack(spacing: 3) {
                                Image(systemName: "flame.fill")
                                    .font(.system(size: 9))
                                    .foregroundColor(ThemeTokens.saffronPrimary)
                                Text("\(habit.streak)")
                                    .font(.system(size: 11, weight: .bold))
                                    .foregroundColor(ThemeTokens.saffronPrimary)
                            }
                            .padding(.horizontal, 6)
                            .padding(.vertical, 3)
                            .background(ThemeTokens.saffronLight)
                            .cornerRadius(10)

                            // Desktop Convenience Mark Done Action
                            if !isDone {
                                Button(action: {
                                    syncService.markHabitDone(habitId: habit.id)
                                }) {
                                    Image(systemName: "checkmark")
                                        .font(.system(size: 10, weight: .bold))
                                        .foregroundColor(ThemeTokens.turmericGreenSuccess)
                                        .padding(6)
                                        .background(ThemeTokens.turmericLight)
                                        .clipShape(Circle())
                                }
                                .buttonStyle(.plain)
                                .help("Mark done from Mac")
                            }
                        }
                        .padding(8)
                        .background(ThemeTokens.cardSurface)
                        .cornerRadius(10)
                        .overlay(
                            RoundedRectangle(cornerRadius: 10)
                                .stroke(ThemeTokens.glassBorder, lineWidth: 1)
                        )
                    }
                }
            }

            Divider()

            // Status Footer
            HStack {
                Image(systemName: "arrow.triangle.2.circlepath")
                    .font(.system(size: 10))
                    .foregroundColor(ThemeTokens.inkMuted)
                Text(syncService.lastSyncedAtText)
                    .font(.system(size: 10))
                    .foregroundColor(ThemeTokens.inkMuted)
                Spacer()
            }
        }
        .padding(14)
        .frame(width: 320)
        .background(ThemeTokens.warmIvory)
    }
}
