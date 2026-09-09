# Phase 1 — Foundation & Design Shell

Read `DESIGN.md` fully before starting. This phase has no AI and no real reminders — it proves the skeleton and the visual language work together.

## Scope (and only this)
1. New Kotlin/Compose Android project, MVVM structure, Compose Navigation set up for 3 destinations: Home, Calendar, Settings.
2. Design tokens implemented as real code (a `Theme.kt`/tokens file), not hardcoded hex values scattered across screens — colors, type scale, spacing scale, and the motion-spec constants from the design doc all live in one place.
3. The floating bottom nav bar, fully built to spec (frosted surface, spring selection animation, safe-area aware), even though only 3 empty-ish screens exist behind it.
4. Room database with the initial schema:
   - `Habit` (id, name, frequency, targetDurationMinutes, proofDescription, reminderIntervalMinutes, createdAt)
   - `CompletionRecord` (id, habitId, date, completedAt, imageLocalUri, verified, confidence, streakAtCompletion)
   - (Notification and verification-detail tables arrive in Phase 2/3 — don't build them now.)
5. Habit creation flow: a simple, well-animated form (not the final polish target, but on-brand) capturing the fields above.
6. Home screen shows real habit cards from Room (name, streak placeholder, progress bar) with the shared-element transition target set up for Phase 4, even if the destination is a stub.
7. One background motif variant applied correctly behind Home, proving the low-opacity line-art layer doesn't hurt readability.

## Explicitly out of scope for this phase
- Notifications/scheduling (Phase 2)
- Camera/AI verification (Phase 3)
- Real calendar grid (Phase 4) — Calendar destination can be a placeholder screen
- Drive sync (Phase 5)
- Mac app (Phase 6)

## Done criteria
- App installs and runs on a Nothing Phone 3a-class device with correct safe-area behavior (nothing clipped by punch-hole or gesture bar).
- Creating a habit persists to Room and appears on Home immediately with entrance animation.
- Bottom nav feels physically springy, not a flat tab switch.
- No navigation drawer/sidebar exists anywhere in the codebase.
