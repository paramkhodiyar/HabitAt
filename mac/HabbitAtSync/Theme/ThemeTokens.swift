import SwiftUI
import AppKit

enum ThemeTokens {
    // Dynamic Dark/Light Mode Adaptive Colors for macOS
    static var warmIvory: Color {
        Color(NSColor(name: nil) { appearance in
            appearance.bestMatch(from: [.aqua, .darkAqua]) == .darkAqua
                ? NSColor(red: 22/255, green: 20/255, blue: 18/255, alpha: 1) // Rich Obsidian Dark
                : NSColor(red: 250/255, green: 246/255, blue: 239/255, alpha: 1) // #FAF6EF Warm Ivory
        })
    }

    static var cardSurface: Color {
        Color(NSColor(name: nil) { appearance in
            appearance.bestMatch(from: [.aqua, .darkAqua]) == .darkAqua
                ? NSColor(red: 32/255, green: 28/255, blue: 26/255, alpha: 1) // Charcoal Card Surface
                : NSColor.white
        })
    }

    static var inkPrimary: Color {
        Color(NSColor(name: nil) { appearance in
            appearance.bestMatch(from: [.aqua, .darkAqua]) == .darkAqua
                ? NSColor(red: 245/255, green: 240/255, blue: 235/255, alpha: 1) // Bright Crisp Text
                : NSColor(red: 42/255, green: 35/255, blue: 32/255, alpha: 1) // #2A2320
        })
    }

    static var inkSecondary: Color {
        Color(NSColor(name: nil) { appearance in
            appearance.bestMatch(from: [.aqua, .darkAqua]) == .darkAqua
                ? NSColor(red: 185/255, green: 175/255, blue: 168/255, alpha: 1) // Warm Secondary Text
                : NSColor(red: 95/255, green: 84/255, blue: 79/255, alpha: 1) // #5F544F
        })
    }

    static var inkMuted: Color {
        Color(NSColor(name: nil) { appearance in
            appearance.bestMatch(from: [.aqua, .darkAqua]) == .darkAqua
                ? NSColor(red: 130/255, green: 120/255, blue: 114/255, alpha: 1)
                : NSColor(red: 158/255, green: 146/255, blue: 140/255, alpha: 1)
        })
    }

    static let saffronPrimary = Color(red: 232/255, green: 163/255, blue: 61/255) // #E8A33D
    
    static var saffronLight: Color {
        Color(NSColor(name: nil) { appearance in
            appearance.bestMatch(from: [.aqua, .darkAqua]) == .darkAqua
                ? NSColor(red: 65/255, green: 48/255, blue: 20/255, alpha: 1)
                : NSColor(red: 255/255, green: 244/255, blue: 227/255, alpha: 1)
        })
    }

    static let saffronGlow = Color(red: 232/255, green: 163/255, blue: 61/255).opacity(0.2)

    static let indigoSecondary = Color(red: 110/255, green: 130/255, blue: 220/255)
    
    static var indigoLight: Color {
        Color(NSColor(name: nil) { appearance in
            appearance.bestMatch(from: [.aqua, .darkAqua]) == .darkAqua
                ? NSColor(red: 32/255, green: 40/255, blue: 72/255, alpha: 1)
                : NSColor(red: 238/255, green: 241/255, blue: 249/255, alpha: 1)
        })
    }

    static let terracottaTertiary = Color(red: 225/255, green: 105/255, blue: 85/255)
    
    static var terracottaLight: Color {
        Color(NSColor(name: nil) { appearance in
            appearance.bestMatch(from: [.aqua, .darkAqua]) == .darkAqua
                ? NSColor(red: 70/255, green: 32/255, blue: 26/255, alpha: 1)
                : NSColor(red: 253/255, green: 240/255, blue: 237/255, alpha: 1)
        })
    }

    static let turmericGreenSuccess = Color(red: 145/255, green: 180/255, blue: 90/255)
    
    static var turmericLight: Color {
        Color(NSColor(name: nil) { appearance in
            appearance.bestMatch(from: [.aqua, .darkAqua]) == .darkAqua
                ? NSColor(red: 38/255, green: 48/255, blue: 25/255, alpha: 1)
                : NSColor(red: 243/255, green: 246/255, blue: 236/255, alpha: 1)
        })
    }

    static var glassBorder: Color {
        Color(NSColor(name: nil) { appearance in
            appearance.bestMatch(from: [.aqua, .darkAqua]) == .darkAqua
                ? NSColor.white.withAlphaComponent(0.16)
                : NSColor.black.withAlphaComponent(0.12)
        })
    }

    static var lineArtMuted: Color {
        Color(NSColor(name: nil) { appearance in
            appearance.bestMatch(from: [.aqua, .darkAqua]) == .darkAqua
                ? NSColor.white.withAlphaComponent(0.08)
                : NSColor.black.withAlphaComponent(0.06)
        })
    }
}
