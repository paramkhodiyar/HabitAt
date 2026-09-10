# AGENTS.md — habitAt & habitAt Sync

This file is the entry point for any coding agent (Claude Code, Cursor, Copilot Workspace, or a human following the same discipline) working in this repository. Read this in full before writing a single line of code, and re-read it whenever you're unsure.

## What this project is
habitAt is a two-app system:
- **habitAt** — Android/Kotlin habit-enforcement app (photo-proof + AI verification + escalating notifications + a photographic calendar history).
- **habitAt Sync** — native macOS/SwiftUI companion that mirrors the same data (Under Development).

Full product spec, phase breakdown, and design law live in `docs/app-docs/`. This file does not repeat them — it tells you the order to read them in and the rules that apply no matter which phase you're on.

## Required reading order, every time
1. `docs/app-docs/INSTRUCTIONS.md` — the operating rules. Non-negotiable.
2. `docs/app-docs/DESIGN.md` — the complete visual and motion language. Applies to every screen on both platforms.
3. `docs/app-docs/00-master-plan.md` — architecture, stack, and the phase index.
4. `docs/app-docs/ENVIRONMENT.md` — what credentials exist, what's still blank, and how to treat a blank key.
5. `docs/app-docs/LOGO.md` — only relevant when working on branding/app-icon/launch-screen assets.
6. The specific `docs/app-docs/phases/phase-N-*.md` file for the task at hand — and **only** that phase. Do not read ahead and pre-build later phases "since you're in there."

If any of these documents conflict, `INSTRUCTIONS.md` wins on process questions, `DESIGN.md` wins on anything visual, and the specific phase doc wins on scope. If a genuine gap exists between them, stop and ask Param rather than resolving it yourself.

## The rules that apply on every single task
- No mocked, faked, stubbed, or hardcoded-to-look-successful integrations anywhere in shipped code. See `INSTRUCTIONS.md` §2 for exactly what this means and how to handle credentials that aren't available yet.
- No navigation drawer, no sidebar, no hamburger menu — ever, on Android. (The Mac app's habit-list sidebar is the one explicitly approved exception — it's a desktop convention, not the banned mobile drawer.)
- One UI/animation approach per platform, used everywhere. Don't introduce a second library or a one-off hand-rolled animation because it's faster for a single screen.
- One phase at a time, fully done, before starting the next.
- Every environment variable / API key is read from configuration, never hardcoded, and starts blank — see `ENVIRONMENT.md`.

## Repo expectations (fill in once the actual project is scaffolded)
- Android module: `/android` — Gradle build, run via Android Studio or `./gradlew installDebug`.
- macOS module: `/mac` — Xcode project (Under Development), run via Xcode or `xcodebuild`.
- Shared contract (Drive folder structure, `sync-metadata.json` shape): defined in `phase-5-drive-sync-ai-notifications.md`, consumed by both modules — do not let the two platforms drift into incompatible assumptions about this shape.

## When you're blocked
If a decision isn't covered by the docs, or two docs seem to disagree, or a required credential is still blank and you're not sure whether that should block the current task — stop and ask. Do not guess, and do not silently work around it with a fake value.
