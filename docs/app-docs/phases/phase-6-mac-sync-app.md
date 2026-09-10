# Phase 6 — habitAt Sync (macOS app)

A real native app, not a script. Depends entirely on the Drive contract defined in Phase 5, Part A — do not start this phase before that contract is stable.

## Scope
1. Swift/SwiftUI app, macOS 14+, distributed as a signed `.app` you run directly (personal use — no App Store submission needed, no notarization headaches to solve unless Gatekeeper actually blocks a self-built binary on your own Mac).
2. Two presentation modes:
   - **Menu bar mode** (default): a compact popover showing today's habits, current streaks, and a one-glance "done / not done" state — read-only status at a glance.
   - **Full window mode**: the real calendar/history experience, adapted from the Android design system — same color tokens, typography, and tile states, but navigation becomes a macOS-appropriate sidebar list of habits (this is the one place a "sidebar" is correct — it's a desktop convention, not the mobile drawer the design doc bans) with the calendar grid as the main content pane.
3. Data access: reads `sync-metadata.json` and images directly from the Drive API (read-only scope), polling on an interval (e.g. every few minutes, or on window focus) — no push infrastructure needed for a personal single-device-pair use case.
4. Shared-element-style transitions reimplemented with SwiftUI's native `matchedGeometryEffect` for the calendar-tile → full-image interaction, keeping the same emotional beat as Android even though the underlying transition API differs.
5. One desktop-only convenience, kept deliberately small: the ability to mark a habit as done from the Mac when you did it away from your phone. This writes directly to `sync-metadata.json` in the same shape the Android app expects and lets the phone reconcile it on next launch. No camera/proof capture on Mac — proof stays a phone-only concept.

## Explicitly out of scope
- Any write access beyond the one convenience above
- Notification generation or scheduling — that's the phone's job entirely
- Full offline mode — this app assumes it can reach Drive; a "last synced at" timestamp is enough graceful degradation

## Done criteria
- Launching the Mac app after a day of phone usage shows the same streaks and proof photos, with no manual refresh needed beyond the polling interval.
- Visual language is unmistakably the same product as the phone app, not a reskinned generic macOS app.
- The one Mac-side "mark done" convenience round-trips correctly back into the phone app's Room state after reconciliation.
