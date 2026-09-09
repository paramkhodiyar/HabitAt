import Foundation

struct SyncMetadataEntry: Codable, Identifiable {
    var id: String { "\(habitId)_\(date)" }
    let habitId: Int64
    let habitName: String
    let habitSlug: String
    let date: String // DD-MM-YYYY format
    let driveFileId: String
    let verified: Bool
    let confidence: Float
    let completedAt: Int64
    let streak: Int
}

struct HabitItem: Codable, Identifiable, Hashable {
    let id: Int64
    let name: String
    let targetDurationMinutes: Int
    let category: String
    let proofDescription: String
    var streak: Int
}

struct SyncMetadataFile: Codable {
    let version: Int
    let lastSyncedAt: Int64
    let habits: [HabitItem]
    let entries: [SyncMetadataEntry]
}

enum DayTileState {
    case completed(photoUri: String?, confidence: Float)
    case missed
    case todayPending
    case future
}

struct DayTileData: Identifiable {
    var id: String { dateString }
    let dateString: String // DD-MM-YYYY format
    let state: DayTileState
}
