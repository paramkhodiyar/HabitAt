# ENVIRONMENT.md — Credentials & Configuration

All keys below are **intentionally blank**. Param will supply real values mid-build. Until a value is filled in:
- The feature that depends on it must show a clear, on-brand "not configured" state (per `DESIGN.md`'s loading/error treatment) — never a fake success, never a silent skip, never a placeholder value standing in for a real one. See `INSTRUCTIONS.md` §2.
- Do not commit real key values to version control once they're added — these belong in a local, gitignored env file (`local.properties` / `.env` / Xcode config, per platform), referenced here only as names.

## Android (`/android/.env` or `local.properties`, gitignored)
| Variable | Purpose | Value |
|---|---|---|
| `AI_VERIFICATION_API_KEY` | Auth for the hosted vision model used in Phase 3 proof verification | *(blank — Param provides)* |
| `AI_VERIFICATION_API_BASE_URL` | Endpoint for the above, once the specific provider is chosen | *(blank — decide in Phase 3, confirm with Param)* |
| `AI_NOTIFICATION_API_KEY` | Auth for the LLM used in Phase 5 for dynamic notification copy (may be the same provider/key as above — confirm before assuming a second key is needed) | *(blank — Param provides)* |
| `GOOGLE_OAUTH_CLIENT_ID` | Google Sign-In for Drive access, scoped to the app-specific folder only (Phase 5) | *(blank — Param provides)* |
| `GOOGLE_DRIVE_FOLDER_NAME` | Root Drive folder name for the sync contract | `HabbitAt` (fixed default per the folder contract in Phase 5 — not a secret, but kept here for one source of truth) |

## macOS (`/mac`, Xcode config / gitignored plist)
| Variable | Purpose | Value |
|---|---|---|
| `GOOGLE_OAUTH_CLIENT_ID_MAC` | Read-only Drive access for HabbitAt Sync (Phase 6) — may be a separate OAuth client from the Android one since it's a different platform registration in Google Cloud Console | *(blank — Param provides)* |

## Rules for agents
1. Never fabricate a value for any row above, even temporarily "to test."
2. Never hardcode a value directly in source — always read from the platform's standard config mechanism (Gradle `local.properties` / Kotlin `BuildConfig`, or Xcode build settings / `Info.plist` + a gitignored `.xcconfig`).
3. If a task can't be meaningfully progressed without a blank key (e.g. you can't verify the AI call actually authenticates), say so explicitly rather than working around it — build everything up to that point, then flag exactly what's needed to finish.
4. When Param supplies a real value, it goes directly into the gitignored local config — never into this document, never into a commit.
