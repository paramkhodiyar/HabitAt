# 00 — Master Plan: habitAt Landing + ParamStore + About Website

## Project Identity

This is the public web presence for habitAt, an AI-verified habit-enforcement Android app (photo-proof submission, AI image verification, real streak tracking, frequency-aware scheduling). This website is a **separate codebase** from the Android app and the Mac sync app. It does not read from Room, Drive, or any app data source. All copy and feature claims come from the docs in this set — nothing is invented by the agent beyond what is specified here.

Three pages, one project:

1. **Landing (`/`)** — tells the full feature story with a scroll-driven cinematic hero.
2. **ParamStore (`/paramstore`)** — a self-contained, app-store-style page to browse and download the APK directly (sideload distribution, not Play Store).
3. **About (`/about`)** — the developer page: Param Khodiyar, contact, story, portfolio links.

## Non-Negotiable Constraints (apply to every phase)

- Zero emojis anywhere — copy, comments, commit messages, code.
- Zero mock/placeholder data in the shipped build. Placeholder text is allowed only in the About-page story block, explicitly marked `TODO: story copy pending`, until Param supplies the real text.
- No CSS gradients, no box-shadows, anywhere, including inside any 3D/canvas material or post-processing.
- No emoji favicons or emoji-based iconography — use the icon set defined in `DESIGN.md`.
- No layout that produces horizontal overflow at any supported breakpoint.
- No wasted whitespace on laptop/desktop widths — content must use the grid system in `DESIGN.md`, not float in a narrow centered column with empty gutters.
- Mobile and desktop are two separate implementations of each section (not one component crushed with `hidden md:block` everywhere) — see `phases/phase-7-responsive-mobile-build.md` for the exact split rule.
- Every phase ends with the self-healing verification loop below before being marked complete.

## Reference Docs (read before every phase)

- `INSTRUCTIONS.md` — tech stack, project structure, commands, deployment.
- `DESIGN.md` — design tokens, layout grid, motion system, page-by-page content spec.
- `AGENTS.md` — behavioral rules for the coding agent, drift-prevention rules.
- The relevant `phases/phase-N-*.md` file for the phase being executed.

Do not start a phase without re-reading `DESIGN.md` and `AGENTS.md` in full, even if they were read for a previous phase. Design tokens and constraints are the source of truth every time, not memory of a prior read.

## Phase Index

| Phase | File | Delivers |
|---|---|---|
| 0 | `phases/phase-0-project-setup.md` | Next.js + TypeScript scaffold, tooling, folder structure, no pages yet |
| 1 | `phases/phase-1-design-system-tokens.md` | Design tokens, base layout primitives, typography scale, icon set wired in |
| 2 | `phases/phase-2-hero-3d-scroll.md` | Landing hero: scroll-driven 3D asset sequence, Apple-style pacing |
| 3 | `phases/phase-3-features-sections.md` | Landing feature sections below the hero, grid-based, scroll-reveal |
| 4 | `phases/phase-4-paramstore-page.md` | ParamStore page: listing, screenshots, changelog, APK download flow |
| 5 | `phases/phase-5-about-story-page.md` | About page: dev profile, story block, contact, portfolio links |
| 6 | `phases/phase-6-error-pages-metadata-seo.md` | 404/500/error boundary, per-page metadata, OG tags, favicon, cookie banner (conditional) |
| 7 | `phases/phase-7-responsive-mobile-build.md` | Dedicated mobile implementation pass for every section built in phases 2–5 |
| 8 | `phases/phase-8-qa-selfhealing-pipeline.md` | Final full-site verification pass, performance budget check, sign-off checklist |

Phases run strictly in order. A phase cannot begin until the previous phase has passed its own verification loop.

## Self-Healing Verification Loop (run at the end of every phase, no exceptions)

1. **Typecheck**: `npx tsc --noEmit` — zero errors required.
2. **Lint**: `npm run lint` — zero errors required; warnings must be triaged and either fixed or explicitly justified in the phase's completion note.
3. **Build**: `npm run build` — must complete with no failed routes and no unresolved-import warnings.
4. **Static audit against `DESIGN.md`**: grep the changed files for `shadow`, `gradient`, `linear-gradient`, `radial-gradient`, emoji unicode ranges. Any hit is a failure — fix before proceeding.
5. **Overflow check**: run the dev server, inspect the changed page(s) at 375px, 768px, 1280px, 1920px widths. Any horizontal scrollbar is a failure.
6. **Content check**: confirm no placeholder text (`lorem`, `TODO`, `TBD`, sample image URLs) exists outside the one explicitly allowed About-page story placeholder.
7. If any check fails: fix, then restart the loop from step 1. Do not proceed to the next phase on a partial pass.
8. On full pass: write a one-paragraph completion note at the bottom of the phase file stating what was verified and any deviations taken, with reasoning.

This loop is what keeps debugging cycles short — each phase is fully closed out before the next begins, so failures never compound across phases.

## Definition of Done (whole project)

- All 8 phases closed with passing verification loops.
- Three routes live and correct: `/`, `/paramstore`, `/about`.
- 404 and error boundary in place and styled, not framework defaults.
- Full site passes the phase-8 checklist including a Lighthouse pass (see `phases/phase-8-qa-selfhealing-pipeline.md` for thresholds).
- No console errors or warnings in the browser devtools on any of the three pages, desktop or mobile viewport.
