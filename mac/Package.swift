// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "HabitAtSync",
    platforms: [
        .macOS(.v14)
    ],
    products: [
        .executable(
            name: "HabitAtSync",
            targets: ["HabitAtSync"]
        )
    ],
    targets: [
        .executableTarget(
            name: "HabitAtSync",
            path: "HabbitAtSync"
        )
    ]
)
