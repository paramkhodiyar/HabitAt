import SwiftUI

@main
struct HabitAtSyncApp: App {
    @StateObject private var syncService = DriveSyncService()

    var body: some Scene {
        // Main Desktop Window App (opens automatically on launch)
        WindowGroup("HabitAt Sync Ledger") {
            MainWindowView()
                .environmentObject(syncService)
        }
        .windowStyle(.titleBar)
        .windowToolbarStyle(.unified)

        // Menu Bar Extra for status bar icon
        MenuBarExtra("HabitAt", systemImage: "checkmark.circle.fill") {
            MenuBarPopoverView()
                .environmentObject(syncService)
        }
        .menuBarExtraStyle(.window)
    }
}
