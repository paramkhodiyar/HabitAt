# Phase 5 — Drive Sync & Dynamic AI Notifications

Two related upgrades that both depend on everything before them working. Can be built in either order, but both must land before Phase 6 (the Mac app needs the Drive contract this phase defines).

## Part A — Google Drive sync
1. Google Sign-In (personal account), scoped only to a dedicated app folder — not full Drive access. OAuth client credentials come from `ENVIRONMENT.md` and will be blank until Param supplies them — build the real Sign-In flow now; see `INSTRUCTIONS.md` for the correct not-yet-configured behavior.
2. Folder contract (must match exactly, the Mac app in Phase 6 depends on this):
   ```
   habitAt/
     <habit-slug>/
       YYYY-MM-DD.jpg
     sync-metadata.json   ← array of CompletionRecord-equivalent entries: habitId, date, driveFileId, verified, confidence, completedAt, streak
   ```
3. Room stays the source of truth for app state; Drive is upload-only for images plus the metadata snapshot, written via a `WorkManager` upload queue so it survives being offline at capture time.
4. Reconcile-on-launch: if `sync-metadata.json` has entries Room doesn't (e.g. app reinstalled), rebuild local state from it — this is what makes the Mac app viewing "the same data" meaningful.

## Part B — Dynamic AI notifications
1. Replace Phase 2's static templates with a `NotificationCopywriter` interface:
   ```
   interface NotificationCopywriter {
     suspend fun generate(context: NotificationContext): String
   }
   ```
   `NotificationContext` carries exactly the fields from the product doc: habit name, streak, scheduled time, current time, ignored-count-today, historical completion rate, usual completion time.
2. One LLM call per notification, short prompt, single-sentence output enforced (max length, strip anything longer).
3. Personality/intensity is a stored user setting from day one of this phase (motivational/sarcastic/aggressive/teasing/disappointed/celebratory), even if the settings UI to change it is minimal — don't hardcode one tone.
4. Fallback: if the AI call fails or times out, fall back to Phase 2's static templates silently — reminders must never fail to fire because a network call hung.

## Explicitly out of scope
- Mac app itself (Phase 6)
- Any change to the escalation *mechanics* — only the copy source changes

## Done criteria
- Proof images and metadata reliably appear in the exact Drive folder structure above.
- Reinstalling the app and reconciling from `sync-metadata.json` restores streaks correctly.
- Notification copy varies meaningfully by context and never blocks/delays delivery on an AI call.
