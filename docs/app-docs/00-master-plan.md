# habitAt — Master Plan & Agent Index

## 0. How to use these docs (read this first, every time)
This folder is the single source of truth. Start with `AGENTS.md` — it defines the full reading order (`INSTRUCTIONS.md` → `DESIGN.md` → this file → `ENVIRONMENT.md` → `LOGO.md` when relevant → the active phase doc) and the non-negotiable rules, including the strict no-mock policy. Everything below assumes you've already read those.

## 1. One-line product description
habitAt is an AI-powered habit enforcer for personal use: you log a habit, it schedules escalating reminders, you prove completion with a photo, an AI verifies it, and a calendar of photographic evidence becomes your record of discipline. A companion Mac app mirrors that record on the desktop.

## 2. Two apps, one system
- **habitAt (Android, Kotlin)** — the primary app. Habit creation, scheduling, camera proof, AI verification, calendar history, notifications.
- **habitAt Sync (macOS, Swift/SwiftUI - Under Development)** — a real native app (menu bar + full window), not a script. Pulls the same Drive-backed data and renders the same calendar/streak experience on the Mac, read-first, with a small set of desktop-only conveniences.

Both apps share one design language (see `DESIGN.md`) and one data contract (Room schema + Drive folder structure), defined in Phase 1 and Phase 5 respectively.

## 3. Decisions and assumptions locked in for this project
State these explicitly to any agent picking this up:
- **Personal app, not a Play Store release.** No Play Console policies, no in-app billing, no "withdrawals." Distribution is a debug/release APK installed directly from Android Studio or over local Wi-Fi ADB from the Mac. This removes an entire category of complexity — don't build for it.
- **No navigation drawer, no sidebar navigation, anywhere.** Navigation is a single floating bottom bar, 3–4 destinations max. This is a hard UI constraint, not a suggestion.
- **Design departs from Param's usual flat/monochrome default for this one project.** This app is explicitly light, modern-colored, and artistic with an Indian cultural motif in the backgrounds — see the design system doc for exact tokens. This is a deliberate, scoped exception, not a change to how other projects should look.
- **Reference device: Nothing Phone 3a.** All safe-area, gesture-nav, and punch-hole considerations are built around this device first, then verified as reasonable on a generic Android baseline.
- **Visual reference for the calendar/photo-grid concept:** the "One Photo / Day" app screenshot (Instagram, `DcYbAPViMev`) — steal the *interaction concept* (chronological photo tiles in a compact monthly grid, rounded phone-frame presentation, minimal typography), not its product purpose. habitAt's calendar is a proof-of-work ledger, not a photo diary.
- **AI is the only external dependency.** Notifications, scheduling, storage of app state, and navigation are all local. Only image verification (and later, notification copywriting) calls out to an AI backend.

## 4. Tech stack
| Layer | Choice |
|---|---|
| Android UI | Kotlin, Jetpack Compose, Material 3 as a base only — visually overridden by the custom design system |
| Architecture | MVVM, single-activity, Compose Navigation |
| Local DB | Room |
| Scheduling | WorkManager (deferrable escalation logic) + AlarmManager (exact-time first reminder) |
| Notifications | Native Android notification channels |
| Camera | CameraX |
| Image loading | Coil |
| Cloud storage | Google Drive API (proof images + a single sync metadata file) |
| AI | Pluggable interface; MVP uses a free/student-tier hosted vision model (see Phase 3) |
| Mac app | Swift, SwiftUI, macOS 14+, Google Drive REST API via URLSession |
| Version control | GitHub (Student Developer Pack benefits applied where relevant) |

## 5. Phase index (build in this order; each phase should be shippable and demoable on its own)
1. **Phase 1 — Foundation & Design Shell**: project skeleton, design tokens implemented as real code, Room schema, floating bottom nav, habit creation + plain list. No AI, no real notifications yet.
2. **Phase 2 — Scheduling & Notification Engine (static copy)**: exact-alarm first reminder, escalating reminder cadence, ignore-tracking, permission handling. Notification text is hand-written/templated — AI-generated copy comes later.
3. **Phase 3 — Camera Proof & AI Verification**: CameraX capture flow, submit → verifying → accept/reject states, pluggable AI verification interface with confidence thresholds.
4. **Phase 4 — Calendar & History UI (the signature screen)**: monthly grid with tile states, tap-to-expand into full proof, streak stats. This is the most animation-heavy phase.
5. **Phase 5 — Drive Sync & Dynamic AI Notifications**: Google Drive as the proof-image backend with a defined folder/metadata contract; upgrade Phase 2's static notifications to AI-generated copy using habit/streak/time context.
6. **Phase 6 — habitAt Sync (macOS app)**: native Mac app consuming the same Drive contract, mirroring the design system, read-first with optional desktop conveniences.

Do not start a phase until the previous one is demoable end-to-end. Do not let a phase's scope creep into "while I'm here, let me also add..." — flag it as a note for a later phase instead.
