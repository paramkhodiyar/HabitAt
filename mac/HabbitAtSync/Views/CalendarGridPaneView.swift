import SwiftUI

struct CalendarGridPaneView: View {
    let habit: HabitItem
    @EnvironmentObject var syncService: DriveSyncService

    @Namespace private var animationNamespace
    @State private var selectedTile: DayTileData? = nil

    private let columns = Array(repeating: GridItem(.flexible(), spacing: 10), count: 7)
    private let weekDays = ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"]

    private var currentMonthName: String {
        let formatter = DateFormatter()
        formatter.dateFormat = "MMMM yyyy"
        return formatter.string(from: Date())
    }

    private var daysInCurrentMonth: Int {
        let calendar = Calendar.current
        let range = calendar.range(of: .day, in: .month, for: Date())
        return range?.count ?? 30
    }

    var body: some View {
        ZStack {
            VStack(alignment: .leading, spacing: 20) {
                // Header Headline & Streak Stats
                HStack(alignment: .center) {
                    VStack(alignment: .leading, spacing: 4) {
                        Text(habit.name)
                            .font(.system(size: 26, weight: .bold, design: .serif))
                            .foregroundColor(ThemeTokens.inkPrimary)
                        
                        Text("\(habit.category) • \(habit.targetDurationMinutes) min daily target")
                            .font(.system(size: 13))
                            .foregroundColor(ThemeTokens.inkSecondary)
                    }

                    Spacer()

                    // Streak Banner Block
                    HStack(spacing: 8) {
                        Image(systemName: "flame.fill")
                            .font(.system(size: 20))
                            .foregroundColor(ThemeTokens.saffronPrimary)

                        VStack(alignment: .leading, spacing: 0) {
                            Text("\(habit.streak) DAYS")
                                .font(.system(size: 18, weight: .black, design: .monospaced))
                                .foregroundColor(ThemeTokens.saffronPrimary)

                            Text("CURRENT STREAK")
                                .font(.system(size: 9, weight: .semibold))
                                .foregroundColor(ThemeTokens.inkMuted)
                        }
                    }
                    .padding(.horizontal, 14)
                    .padding(.vertical, 10)
                    .background(ThemeTokens.saffronLight)
                    .cornerRadius(14)
                    .overlay(
                        RoundedRectangle(cornerRadius: 14)
                            .stroke(ThemeTokens.saffronPrimary.opacity(0.3), lineWidth: 1)
                    )
                }

                Divider()

                // Month Year Ledger Header
                HStack {
                    Text("\(currentMonthName) Ledger")
                        .font(.system(size: 16, weight: .semibold))
                        .foregroundColor(ThemeTokens.inkPrimary)

                    Spacer()

                    HStack(spacing: 12) {
                        LegendPill(label: "Done", color: ThemeTokens.turmericGreenSuccess)
                        LegendPill(label: "Missed", color: ThemeTokens.terracottaTertiary)
                        LegendPill(label: "Today", color: ThemeTokens.saffronPrimary)
                    }
                }

                // 7-Column Weekday Header
                HStack(spacing: 10) {
                    ForEach(weekDays, id: \.self) { day in
                        Text(day)
                            .font(.system(size: 11, weight: .bold))
                            .foregroundColor(ThemeTokens.inkMuted)
                            .frame(maxWidth: .infinity)
                    }
                }

                // 7-Column Month Grid (Dynamic days for current month)
                LazyVGrid(columns: columns, spacing: 10) {
                    ForEach(1...daysInCurrentMonth, id: \.self) { dayNum in
                        let dateStr = makeDateString(dayNum: dayNum)
                        let tileData = evaluateTileData(dayNum: dayNum, dateStr: dateStr)

                        DayTileView(
                            dayNumber: dayNum,
                            tileData: tileData,
                            namespace: animationNamespace
                        )
                        .onTapGesture {
                            if case .completed = tileData.state {
                                withAnimation(.spring(response: 0.35, dampingFraction: 0.8)) {
                                    selectedTile = tileData
                                }
                            }
                        }
                    }
                }

                Spacer()
            }
            .padding(24)

            // Proof Expansion Modal
            if let tile = selectedTile {
                Color.black.opacity(0.4)
                    .ignoresSafeArea()
                    .onTapGesture {
                        withAnimation(.spring(response: 0.3, dampingFraction: 0.8)) {
                            selectedTile = nil
                        }
                    }

                VStack(spacing: 16) {
                    HStack {
                        VStack(alignment: .leading, spacing: 2) {
                            Text("Proof Verified")
                                .font(.system(size: 16, weight: .bold))
                                .foregroundColor(ThemeTokens.inkPrimary)

                            Text(tile.dateString)
                                .font(.system(size: 12))
                                .foregroundColor(ThemeTokens.inkSecondary)
                        }

                        Spacer()

                        Button(action: {
                            withAnimation(.spring(response: 0.3, dampingFraction: 0.8)) {
                                selectedTile = nil
                            }
                        }) {
                            Image(systemName: "xmark.circle.fill")
                                .font(.system(size: 20))
                                .foregroundColor(ThemeTokens.inkMuted)
                        }
                        .buttonStyle(.plain)
                    }

                    // Photo Proof Frame
                    ZStack {
                        RoundedRectangle(cornerRadius: 16)
                            .fill(ThemeTokens.turmericLight)
                            .frame(height: 240)

                        VStack(spacing: 8) {
                            Image(systemName: "checkmark.seal.fill")
                                .font(.system(size: 48))
                                .foregroundColor(ThemeTokens.turmericGreenSuccess)

                            Text("Photo Proof Verified")
                                .font(.system(size: 14, weight: .semibold))
                                .foregroundColor(ThemeTokens.inkPrimary)

                            Text("Drive Ledger Entry: HabbitAt/\(driveSlug(habit.name))/\(tile.dateString).jpg")
                                .font(.system(size: 11))
                                .foregroundColor(ThemeTokens.inkSecondary)
                        }
                    }
                    .matchedGeometryEffect(id: tile.dateString, in: animationNamespace)

                    Button("Close Detail") {
                        withAnimation(.spring(response: 0.3, dampingFraction: 0.8)) {
                            selectedTile = nil
                        }
                    }
                    .buttonStyle(.borderedProminent)
                    .tint(ThemeTokens.saffronPrimary)
                }
                .padding(20)
                .frame(width: 400)
                .background(ThemeTokens.cardSurface)
                .cornerRadius(20)
                .shadow(radius: 20)
            }
        }
    }

    private func makeDateString(dayNum: Int) -> String {
        let calendar = Calendar.current
        var components = calendar.dateComponents([.year, .month], from: Date())
        components.day = dayNum
        let date = calendar.date(from: components) ?? Date()
        let formatter = DateFormatter()
        formatter.dateFormat = "dd-MM-yyyy"
        return formatter.string(from: date)
    }

    private func evaluateTileData(dayNum: Int, dateStr: String) -> DayTileData {
        let calendar = Calendar.current
        let todayDay = calendar.component(.day, from: Date())

        if let entry = syncService.entries.first(where: { $0.habitId == habit.id && $0.date == dateStr && $0.verified }) {
            return DayTileData(dateString: dateStr, state: .completed(photoUri: entry.driveFileId, confidence: entry.confidence))
        }

        if dayNum == todayDay {
            return DayTileData(dateString: dateStr, state: .todayPending)
        } else if dayNum < todayDay {
            return DayTileData(dateString: dateStr, state: .missed)
        } else {
            return DayTileData(dateString: dateStr, state: .future)
        }
    }

    private func driveSlug(_ text: String) -> String {
        text.lowercased().replacingOccurrences(of: " ", with: "-")
    }
}

struct DayTileView: View {
    let dayNumber: Int
    let tileData: DayTileData
    let namespace: Namespace.ID

    var body: some View {
        VStack {
            ZStack {
                switch tileData.state {
                case .completed:
                    RoundedRectangle(cornerRadius: 10)
                        .fill(ThemeTokens.turmericLight)
                        .overlay(
                            RoundedRectangle(cornerRadius: 10)
                                .stroke(ThemeTokens.turmericGreenSuccess, lineWidth: 1.5)
                        )
                    
                    VStack(spacing: 2) {
                        Text("\(dayNumber)")
                            .font(.system(size: 12, weight: .bold))
                            .foregroundColor(ThemeTokens.turmericGreenSuccess)
                        Image(systemName: "checkmark")
                            .font(.system(size: 10, weight: .black))
                            .foregroundColor(ThemeTokens.turmericGreenSuccess)
                    }

                case .missed:
                    RoundedRectangle(cornerRadius: 10)
                        .fill(ThemeTokens.terracottaLight)
                        .overlay(
                            RoundedRectangle(cornerRadius: 10)
                                .stroke(ThemeTokens.terracottaTertiary.opacity(0.6), lineWidth: 1)
                        )
                    
                    VStack(spacing: 2) {
                        Text("\(dayNumber)")
                            .font(.system(size: 12, weight: .medium))
                            .foregroundColor(ThemeTokens.terracottaTertiary)
                        Circle()
                            .fill(ThemeTokens.terracottaTertiary)
                            .frame(width: 4, height: 4)
                    }

                case .todayPending:
                    RoundedRectangle(cornerRadius: 10)
                        .fill(ThemeTokens.saffronLight)
                        .overlay(
                            RoundedRectangle(cornerRadius: 10)
                                .stroke(ThemeTokens.saffronPrimary, lineWidth: 2)
                        )
                    
                    Text("\(dayNumber)")
                        .font(.system(size: 13, weight: .bold))
                        .foregroundColor(ThemeTokens.saffronPrimary)

                case .future:
                    RoundedRectangle(cornerRadius: 10)
                        .fill(ThemeTokens.cardSurface)
                        .overlay(
                            RoundedRectangle(cornerRadius: 10)
                                .stroke(ThemeTokens.glassBorder, lineWidth: 1)
                        )
                    
                    Text("\(dayNumber)")
                        .font(.system(size: 12))
                        .foregroundColor(ThemeTokens.inkMuted)
                }
            }
            .frame(height: 52)
            .matchedGeometryEffect(id: tileData.dateString, in: namespace)
        }
    }
}

struct LegendPill: View {
    let label: String
    let color: Color

    var body: some View {
        HStack(spacing: 4) {
            Circle()
                .fill(color)
                .frame(width: 6, height: 6)
            Text(label)
                .font(.system(size: 11))
                .foregroundColor(ThemeTokens.inkSecondary)
        }
    }
}
