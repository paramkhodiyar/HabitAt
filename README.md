# habitAt — AI-Verified Habit Enforcement System

[![Android Version](https://img.shields.io/badge/Android-8.0%2B%20%28API%2026%2B%29-3DDC84?style=flat-square&logo=android&logoColor=white)](https://github.com/paramkhodiyar/HabitAt)
[![macOS Companion](https://img.shields.io/badge/macOS-Under%20Development-orange?style=flat-square&logo=apple&logoColor=white)](https://github.com/paramkhodiyar/HabitAt)
[![Web App](https://img.shields.io/badge/Next.js-16.3%20%28App%20Router%29-000000?style=flat-square&logo=nextdotjs&logoColor=white)](https://habbitat.app)
[![Developer](https://img.shields.io/badge/Developer-Param%20Khodiyar-B5502E?style=flat-square)](https://www.paramkhodiyar.dev)

> **Habits verified by proof, not promises.**  
> habitAt is a cross-platform habit enforcement ecosystem that replaces unearned checkmarks with timestamped camera proof, vision AI evaluation, honest zero-based streak tracking, and private cloud sync.

---

## Project Architecture

The repository is structured as a unified monorepo containing three core modules:

```
HabitAt/
├── android/                    # Android/Kotlin native app (Compose + CameraX + ML + Room)
├── mac/                        # macOS/SwiftUI native desktop companion app (Under Development)
├── web/                        # Public web platform & ParamStore APK download hub
│   ├── habbitat-landing-docs/  # Web design system & phase execution docs
│   └── habbitat-site/          # Next.js 16 + Tailwind CSS v4 + Framer Motion site
├── apks/                       # Signed Android APK release builds (v1.0.2)
├── assets/branding/            # Official app icons, logos, and vector brand assets
├── docs/app-docs/              # Native app architecture, design laws, and phase specs
└── AGENTS.md                   # Repository guidelines and agent entrypoint
```

---

## Key Features

1. **Proof-Based Verification**:
   No self-reported checkmarks or easy credit. Submit a timestamped photo of your completed habit (e.g. laptop code, gym equipment, reading material).

2. **Vision AI Evaluation Pipeline**:
   Contextual AI vision model evaluates photo evidence against specific habit criteria before approving check-ins and preventing photo spoofing.

3. **Honest Zero-Based Streaks**:
   Streaks start strictly at zero with no artificial initial boosts. Miss a deadline without proof, and the streak resets cleanly without excuses.

4. **Photographic Calendar Ledger**:
   Month-by-month grid displaying actual proof photo thumbnails inside day tiles for a permanent visual history of discipline.

5. **ParamStore APK Distribution**:
   Direct, signed Android APK distribution channel with cryptographic SHA-256 package verification.

6. **Private Cloud Sync**:
   Encrypted Google Drive sync contract for private user-owned cloud backups across Android and macOS (Under Development) devices.

---

## Quick Start & Installation

### Android Application (`/android`)
- **Requirements**: Android Studio Ladybug+ / Gradle 8.x, JDK 17+.
- **Run via CLI**:
  ```bash
  cd android
  ./gradlew installDebug
  ```
- **Direct APK Sideload**: Download `HabitAt-v1.0.2.apk` from [`apks/`](./apks) or via [ParamStore](https://habbitat.app/paramstore).

### Web Site & ParamStore (`/web/habbitat-site`)
- **Requirements**: Node.js 20+, `npm`.
- **Run Locally**:
  ```bash
  cd web/habbitat-site
  npm install
  npm run dev
  ```
  Open `http://localhost:3000` in your browser.

### macOS Companion App (`/mac` - Under Development)
- **Requirements**: macOS 14.0+, Xcode 15+, Swift 5.9+.
- **Run via Xcode**: Open `mac/HabbitAtSync/HabbitAtSync.xcodeproj` and click **Run**.

---

## Documentation Index

- **[Root Agent Guidelines](./AGENTS.md)**: Operating rules and reading order for coding agents.
- **[App Master Plan](./docs/app-docs/00-master-plan.md)**: Native app architecture and phase breakdown.
- **[App Design System](./docs/app-docs/DESIGN.md)**: Visual, motion, and typography guidelines.
- **[Web Landing Specs](./web/habitAt-landing-docs/00-master-plan.md)**: Web platform specifications and design tokens.

---

## Developer & Contact

**Param Khodiyar**  
*Creator & Lead Engineer, habitAt*

- **Website**: [paramkhodiyar.dev](https://www.paramkhodiyar.dev)
- **GitHub**: [@paramkhodiyar](https://github.com/paramkhodiyar)
- **LinkedIn**: [in/paramkhodiyar](https://www.linkedin.com/in/paramkhodiyar)
- **Email**: [paramkhodiyar1008@gmail.com](mailto:paramkhodiyar1008@gmail.com)



---

## License

&copy; 2026 Param Khodiyar. All rights reserved.
