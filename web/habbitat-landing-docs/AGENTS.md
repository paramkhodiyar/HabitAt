# AGENTS.md — Rules for the Coding Agent

These rules apply to every phase in this doc set, without exception. If any instruction elsewhere seems to conflict with a rule here, this file wins and the conflict should be flagged back to Param rather than silently resolved.

## Before Writing Any Code

- Re-read `00-master-plan.md`, `DESIGN.md`, and this file in full. Do not rely on a earlier read from a previous session or phase.
- Read the specific `phases/phase-N-*.md` file for the current phase in full before touching any file.
- If the phase file references a component or token not yet defined in `DESIGN.md`, stop and flag it rather than inventing one.

## Hard Prohibitions

- Never use `box-shadow`, `filter: drop-shadow`, or Three.js/canvas shadow maps.
- Never use CSS gradients (`linear-gradient`, `radial-gradient`, `conic-gradient`) anywhere, including as Tailwind utility classes (`bg-gradient-*` is banned — remove it from any generated boilerplate).
- Never use emoji characters in code, comments, copy, commit messages, or alt text.
- Never use pill-shaped "eyebrow" / "kicker" badges or labels sitting above headings to announce section identity or title (e.g. "Feature Spotlight", "How It Works", "Design System & Token Preview"). Pill radius (`rounded-full`) is strictly reserved for interactive tag/filter UI (e.g. frequency filters), never for decorative heading kickers. Section hierarchy must be communicated through heading scale, grid rhythm, and layout.
- Never introduce mock data, sample screenshots, or lorem ipsum into a component that ships. If a real asset is not yet available, use a clearly labeled placeholder block (flat rectangle, correct dimensions, label text stating what belongs there) and log it in the phase completion note as an open asset dependency.
- Never fetch from or write to any HabbitAt app data source (Room DB, Google Drive, Mac sync folder). This site is static/content-driven only, per `INSTRUCTIONS.md`.
- Never restructure the design tokens defined in `DESIGN.md` without flagging the change and the reason.
- Never mark a phase complete without running the full Self-Healing Verification Loop from `00-master-plan.md` and recording the result.


## Drift Prevention

- Every component you create must be listed with its exact file path before you write it, matching the structure in `INSTRUCTIONS.md`. If a needed component isn't in that structure, add it to the structure list in the same commit and note the addition in the phase completion note.
- Do not refactor or "improve" a previous phase's code while working on a later phase unless the current phase file explicitly asks for it. Flag suggested improvements to Param instead of applying them silently.
- Keep desktop and mobile implementations in separate, clearly named files or clearly separated code paths per `phases/phase-7-responsive-mobile-build.md` — do not solve responsiveness by cramming both layouts into one component with a long chain of conditional Tailwind breakpoints.
- Match copy exactly as written in `DESIGN.md`'s content spec for each page. If copy is missing for a section, flag it rather than writing new marketing copy unprompted — except for the About-page story block, which is explicitly a placeholder until Param provides it.

## When Something Breaks

- If a build, lint, or typecheck error appears, fix it before writing any new code, even if it appears unrelated to the current task.
- If a fix requires touching a file outside the current phase's scope, note this in the phase completion note along with the reason.
- If the same category of error recurs across two or more phases, stop and add a rule to this file (with a dated note) describing the recurring failure and its fix, so it does not recur a third time.

## Reporting

At the end of each phase, produce a short completion note appended to that phase's file containing:

- What was built (file list).
- Verification loop results (pass/fail per step, with fixes applied for any initial failures).
- Any open dependencies (missing real assets, copy, or decisions still needed from Param).
- Any deviations from the phase spec and the reasoning.

Do not narrate progress conversationally outside of this note. The note is the record of truth for what happened in the phase.
