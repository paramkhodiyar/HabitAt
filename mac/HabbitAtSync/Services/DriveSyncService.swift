import Foundation
import Combine

class DriveSyncService: ObservableObject {
    let oauthClientId: String = "149299518288-d5jih5jiojeup33u5vg4ucdt5cbpi8oe.apps.googleusercontent.com"
    
    @Published var habits: [HabitItem] = []
    @Published var entries: [SyncMetadataEntry] = []
    @Published var lastSyncedAtText: String = "Not synced yet"
    @Published var isConfigured: Bool = true
    
    private var timer: Timer?
    private let dateFormatter: DateFormatter = {
        let formatter = DateFormatter()
        formatter.dateFormat = "dd-MM-yyyy"
        return formatter
    }()
    
    private var syncFileUrl: URL {
        let appSupport = FileManager.default.urls(for: .applicationSupportDirectory, in: .userDomainMask).first!
        let dir = appSupport.appendingPathComponent("HabbitAt", isDirectory: true)
        try? FileManager.default.createDirectory(at: dir, withIntermediateDirectories: true)
        return dir.appendingPathComponent("sync-metadata.json")
    }

    private var altSyncFileUrl: URL {
        let home = FileManager.default.homeDirectoryForCurrentUser
        let dir = home.appendingPathComponent(".habbitat_sync", isDirectory: true)
        try? FileManager.default.createDirectory(at: dir, withIntermediateDirectories: true)
        return dir.appendingPathComponent("sync-metadata.json")
    }
    
    init() {
        loadMetadata()
        startPolling()
    }
    
    deinit {
        timer?.invalidate()
    }
    
    func startPolling() {
        timer = Timer.scheduledTimer(withTimeInterval: 15.0, repeats: true) { [weak self] _ in
            self?.loadMetadata()
        }
    }
    
    func loadMetadata() {
        // Read local sync-metadata.json if available
        let urlsToTry = [syncFileUrl, altSyncFileUrl]
        var loaded = false
        
        for url in urlsToTry {
            if FileManager.default.fileExists(atPath: url.path),
               let data = try? Data(contentsOf: url),
               let file = try? JSONDecoder().decode(SyncMetadataFile.self, from: data) {
                DispatchQueue.main.async {
                    self.habits = file.habits
                    self.entries = file.entries
                    let timeFormatter = DateFormatter()
                    timeFormatter.timeStyle = .medium
                    self.lastSyncedAtText = "Synced at \(timeFormatter.string(from: Date(timeIntervalSince1970: Double(file.lastSyncedAt) / 1000.0)))"
                }
                loaded = true
                break
            }
        }
        
        if !loaded {
            DispatchQueue.main.async {
                let timeFormatter = DateFormatter()
                timeFormatter.timeStyle = .medium
                if !self.entries.isEmpty || !self.habits.isEmpty {
                    self.lastSyncedAtText = "Synced at \(timeFormatter.string(from: Date()))"
                } else {
                    self.lastSyncedAtText = "No habits synced"
                }
            }
        }
    }
    
    func createHabit(name: String, targetDuration: Int, category: String, proofDescription: String) {
        let newId = Int64((habits.map { $0.id }.max() ?? 0) + 1)
        let newHabit = HabitItem(
            id: newId,
            name: name,
            targetDurationMinutes: targetDuration,
            category: category.isEmpty ? "General" : category,
            proofDescription: proofDescription.isEmpty ? "Photo proof" : proofDescription,
            streak: 0
        )
        habits.append(newHabit)
        saveMetadataFile()
    }
    
    func markHabitDone(habitId: Int64) {
        let todayStr = dateFormatter.string(from: Date())
        guard let habit = habits.first(where: { $0.id == habitId }) else { return }
        
        entries.removeAll(where: { $0.habitId == habitId && $0.date == todayStr })
        
        let habitSlug = habit.name.lowercased()
            .replacingOccurrences(of: " ", with: "-")
            .replacingOccurrences(of: "[^a-z0-9-]", with: "", options: .regularExpression)
        
        let updatedStreak = habit.streak + 1
        
        let newEntry = SyncMetadataEntry(
            habitId: habitId,
            habitName: habit.name,
            habitSlug: habitSlug,
            date: todayStr,
            driveFileId: "mac_desktop_manual",
            verified: true,
            confidence: 1.0,
            completedAt: Int64(Date().timeIntervalSince1970 * 1000),
            streak: updatedStreak
        )
        
        entries.append(newEntry)
        
        if let idx = habits.firstIndex(where: { $0.id == habitId }) {
            habits[idx].streak = updatedStreak
        }
        
        saveMetadataFile()
    }
    
    private func saveMetadataFile() {
        let fileObj = SyncMetadataFile(
            version: 1,
            lastSyncedAt: Int64(Date().timeIntervalSince1970 * 1000),
            habits: habits,
            entries: entries
        )
        
        if let data = try? JSONEncoder().encode(fileObj) {
            try? data.write(to: syncFileUrl, options: .atomic)
            try? data.write(to: altSyncFileUrl, options: .atomic)
        }
        
        let timeFormatter = DateFormatter()
        timeFormatter.timeStyle = .medium
        lastSyncedAtText = "Synced at \(timeFormatter.string(from: Date()))"
    }
    
    func isHabitDoneToday(habitId: Int64) -> Bool {
        let todayStr = dateFormatter.string(from: Date())
        return entries.contains(where: { $0.habitId == habitId && $0.date == todayStr && $0.verified })
    }
}

extension Date {
    var timeIntervalSince1970Ms: Int64 {
        Int64(self.timeIntervalSince1970 * 1000)
    }
}
