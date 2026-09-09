# Phase 2 — Scheduling & Notification Engine (static copy)

Builds on Phase 1's Room schema. AI-generated notification text is a Phase 5 upgrade — this phase proves the escalation *mechanics* with hand-written templated copy.

## Scope
1. `NotificationLog` table (id, habitId, sentAt, escalationLevel, ignored).
2. First reminder scheduled via `AlarmManager` exact alarm at the habit's preferred time; escalation reminders scheduled via `WorkManager` at the user-configured interval, capped at a configurable max count per day.
3. Escalation state machine exactly as specified in the product doc: scheduled → sent → ignored? → escalate → stronger message → repeat up to limit → stop only on verified completion.
4. 3–4 hand-written message tiers matching the escalating tone (mild → pointed → streak-loss-aversion → post-completion celebration) as placeholders for what Phase 5 will make dynamic. Store them as a simple templated resource, not literal AI calls.
5. Runtime permission handling: POST_NOTIFICATIONS, exact-alarm permission flow, and a guided battery-optimization exemption request — all with on-brand UI, not raw system dialogs dropped on the user with no context.
6. Notification tap opens directly to the relevant habit's proof-submission entry point (built as a stub target if Phase 3 isn't done yet).

## Explicitly out of scope
- AI-generated copy (Phase 5)
- Camera/verification (Phase 3)
- Drive sync (Phase 5)

## Done criteria
- A habit scheduled for a time in the near future reliably fires, escalates on ignore, and stops immediately once a completion is recorded (can be faked via a debug button for this phase).
- Permission requests happen contextually, on-brand, not as a wall of system dialogs at first launch.
- Notification behavior is verified surviving Doze/App Standby on a Nothing Phone 3a-class device — don't assume; test it.
