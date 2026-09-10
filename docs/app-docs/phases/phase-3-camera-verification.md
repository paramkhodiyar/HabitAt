# Phase 3 — Camera Proof & AI Verification

## Scope
1. CameraX capture screen matching the design system: minimal chrome, a large floating capture button, gallery-picker fallback, entered from a habit card's contextual action (not the nav bar).
2. Submission flow with three visible states: `capturing` → `verifying` (on-brand loading motion per design doc, not a spinner) → `result` (accept/reject with the decisive animation beat).
3. A pluggable `ProofVerifier` interface so the backend can be swapped later without touching UI:
   ```
   interface ProofVerifier {
     suspend fun verify(habit: Habit, imageUri: Uri): VerificationResult
   }
   data class VerificationResult(val verified: Boolean, val confidence: Float, val reason: String)
   ```
4. MVP implementation calls a real hosted vision-capable model over a simple backend-less REST call (a free/student-tier API — audit GitHub Student Developer Pack + provider free tiers before picking one; don't default to a paid API out of convenience). The API key is read from the environment per `ENVIRONMENT.md` — it will be blank until Param supplies it mid-build. Build the real integration now; see `INSTRUCTIONS.md` for how to handle the missing-key state honestly.
5. Confidence thresholds exactly as specified: high → auto-accept, medium → ask for a second photo or brief clarification, low → reject with the on-brand "nice try" copy.
6. On accept: writes `CompletionRecord`, recalculates streak, cancels remaining notifications for the day (Phase 2's engine), triggers the accept animation + haptic.
7. On reject: habit stays incomplete, reminders continue on schedule.

## Explicitly out of scope
- Calendar grid rendering of these records (Phase 4)
- Any on-device/offline ML model — that's a possible "Later" item, not MVP
- Drive upload of the image — for this phase, store proof images locally only; Drive sync is Phase 5

## Done criteria
- A real photo submission round-trips through capture → AI call → accept/reject UI with correct animation and haptic per the design doc.
- Streak math is correct across accept, reject, and a skipped day.
- The verifier is swappable behind the interface without UI changes — the UI must only ever depend on the `ProofVerifier` interface, never on the concrete implementation, so the real backend can be swapped later with zero UI changes.
