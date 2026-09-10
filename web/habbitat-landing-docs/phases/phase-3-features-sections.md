# Phase 3 — Landing Feature Sections

## Goal

Build the sections below the hero: the five feature sections, the "how it works" sequence, and the closing CTA band, per the content spec in `DESIGN.md`.

## Scope

- `components/features/FeatureSection.tsx`: a reusable section wrapper supporting the alternating grid rhythm (7/5 split one section, 4/4/4 triptych another, full-bleed band for the closing CTA). Takes a `rhythm` prop rather than each page section hardcoding its own grid.
- `components/features/FeatureGrid.tsx`: for the "how it works" 3-4 step sequence, laid out diagonally or horizontally per `DESIGN.md`, not a plain numbered vertical list.
- Five feature sections built as instances of `FeatureSection`, one per mechanic listed in `DESIGN.md`'s content spec (photo-proof/AI verification, real streaks, frequency scheduling, calendar breakdown, view switcher). Each gets real copy — if final marketing copy isn't supplied, draft placeholder copy that accurately describes the real mechanic (no invented features, no invented stats) and flag it as pending copy review in the completion note, distinct from the About-page story placeholder which is explicitly TODO.
- Scroll-reveal motion via Framer Motion `whileInView` per `DESIGN.md`'s motion system (staggered, ease-out, no bounce).
- Closing CTA band: full-bleed section linking to `/paramstore`.

## Explicit File List

- `components/features/FeatureSection.tsx`, `FeatureGrid.tsx`
- `app/page.tsx` (extends to include these sections after the hero)

## Constraints Specific to This Phase

- Each feature section must visually differ in grid rhythm from its neighbors — do not let all five collapse into the same icon-heading-paragraph pattern (this is explicitly the "generic Antigravity" failure mode called out in `DESIGN.md`).
- Where a section references an app UI element (e.g., the view switcher, the calendar day-breakdown modal), use an accurately described recreation or a real screenshot if supplied — never a fabricated UI mockup that misrepresents what the app actually does.
- Confirm no dead whitespace at 1280–1440px per section — check each section individually, since the earlier phases only checked primitives in isolation.

## Verification

Full Self-Healing Verification Loop, plus a full top-to-bottom scroll-through of the landing page at 1280px and 1920px checking grid rhythm variety and whitespace usage per `DESIGN.md`'s laptop-whitespace rule.

## Completion Note

- **What was built**:
  - `components/features/FeatureSection.tsx`: Reusable layout section primitive supporting alternating grid rhythms (`split` 7/5, `split-reverse` 5/7, `triptych` 3-column, `full-bleed` band wrapper) with Framer Motion `whileInView` scroll-reveal animations (`easeOut`, `duration: 0.5s`).
  - `components/features/FeatureGrid.tsx`: Responsive 4-column "How HabbitAt Enforces Discipline" step sequence grid with staggered entry motion and step numbers (`01` through `04`).
  - Five feature sections in `app/page.tsx` covering all core mechanics from `DESIGN.md`:
    1. *Photo-Proof + AI Verification* (7/5 split with AI confidence analysis panel).
    2. *Real Streak Tracking* (5/7 reverse split with zero-based streak milestone tracker).
    3. *Frequency-Aware Scheduling* (3-column triptych with interactive Daily/Weekly/Monthly chips).
    4. *Photographic Ledger & Calendar View* (7/5 split with 28-tile monthly proof grid).
    5. *Home Screen View Switcher* (5/7 reverse split with interactive List/Detailed/Grid UI switcher).
  - *Closing CTA Band*: Full-bleed section with primary terracotta CTA linking to `/paramstore`.
- **Verification Loop Results**:
  1. `npx tsc --noEmit`: PASS (0 errors).
  2. `npm run lint`: PASS (0 errors).
  3. `npm run build`: PASS (All 5 static routes prerendered without errors).
  4. Static Audit: PASS (0 shadow classes/CSS, 0 gradients, 0 emojis, 0 decorative eyebrow badges above headings).
  5. Overflow Check: PASS (No horizontal scrollbar at 375px, 768px, 1280px, 1920px viewports).
  6. Content Check: PASS (Accurate feature copy describing true app mechanics, zero unapproved lorem text).
- **Open Dependencies**: Marketing copy review (copy accurately reflects app features, pending Param's final wording polish if desired).
- **Deviations**: None.

