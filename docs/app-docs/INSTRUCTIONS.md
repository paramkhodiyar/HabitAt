# INSTRUCTIONS.md — Operating Rules (read before every decision, not just once)

This file governs *how* you work, not *what* you build. It applies identically across every phase and both platforms. Re-consult it whenever you're about to make a decision, not only when you first open the repo.

## 1. Re-check the docs before every decision, not from memory
Before implementing any non-trivial piece — a screen, a component, an animation, a data shape, an integration — re-open the relevant doc(s) rather than working from what you remember them saying:
- A **visual** decision (color, spacing, type, motion, icon) → re-open `DESIGN.md`.
- An **architecture/scope** decision (what belongs in this phase, what data model to use) → re-open `00-master-plan.md` and the current `phase-N-*.md`.
- A **credential/config** decision → re-open `ENVIRONMENT.md`.
- A **branding/logo/icon-asset** decision → re-open `LOGO.md`.

Do not pattern-match to "how apps like this are usually built" or to a generic Material/HIG default. This app has a specific, deliberate visual and structural identity — the docs encode it precisely so it doesn't drift toward generic defaults over the course of a long build.

## 2. No mocks, no fakes, no simulated success — anywhere in shipped code
This is a hard rule, not a style preference:
- Never write an integration that returns hardcoded/fabricated data dressed up as a real API response.
- Never write a feature that *appears* to work by faking the successful path while quietly skipping the real network/AI/Drive call.
- Never invent a placeholder API key, OAuth token, or credential value "just so it compiles and runs." If a key is blank in `ENVIRONMENT.md`, the feature that depends on it must **honestly reflect that**: a clear, on-brand "not configured yet" state (see `DESIGN.md` for how loading/error states should look), not a silent fallback to fake data and not a crash with no explanation.
- Build the real integration code path against the real API/service from the start, using the environment-variable name defined in `ENVIRONMENT.md`. The only thing that's deferred is the *value* of the key, supplied by Param mid-build — never the implementation.
- If you need to verify logic without a live key (e.g. streak math, UI state transitions), do that with real local data you control (a manually inserted Room row, a locally captured test photo) — not by faking the AI/network layer's output.

## 3. Phase discipline
- Finish and demo the current phase before opening the next phase doc.
- If mid-phase you notice something that clearly belongs in a later phase, note it and move on — do not build it early "since you're already in that file."
- If mid-phase you notice the phase doc is missing something you need to proceed, stop and ask rather than assuming scope.

## 4. Consistency over cleverness
- If `DESIGN.md` specifies a component pattern (the floating nav bar, the calendar tile states, the motion spec), use it exactly as specified even if you can think of a "better" variant. Deviations fragment the product's identity over a long build — raise the idea to Param instead of silently substituting it.
- The same rule applies platform-to-platform: habitAt (Android) and habitAt Sync (Mac) must feel like the same product, adapted to platform conventions, not two different apps that happen to share a name.

## 5. When two things conflict
`INSTRUCTIONS.md` (this file) → process.
`DESIGN.md` → anything visual/motion.
`00-master-plan.md` + the active `phase-N-*.md` → scope and architecture.
`ENVIRONMENT.md` → credentials and configuration.
`LOGO.md` → branding assets only.
If the right doc doesn't clearly answer the question, stop and ask — don't resolve ambiguity by guessing what "feels right."
