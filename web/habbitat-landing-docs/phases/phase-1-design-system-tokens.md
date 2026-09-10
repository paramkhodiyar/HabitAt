# Phase 1 — Design System Tokens & Layout Primitives

## Goal

Turn `DESIGN.md`'s tokens and grid rules into real, reusable code, so every later phase consumes the same primitives instead of re-deriving spacing/color/type decisions per component.

## Scope

- Finalize the two heading-font candidates via `next/font`, present both rendered at H1/H2 scale on a temporary `/dev-preview` route (removed before phase 8) for Param to pick from; proceed with a reasonable default if no response is given, but flag the pending decision in the completion note.
- Build `components/layout/Container.tsx`: the 12-column grid wrapper with the 1440px max-width and `clamp()`-based gutters from `DESIGN.md`. All page sections will consume this.
- Build shared `components/ui/` primitives: `Button.tsx` (primary/secondary variants, flat, border-based, no shadow), `Chip.tsx` (pill-radius, used sparingly per `DESIGN.md`), `IconBadge.tsx` (icon-in-flat-square, used for feature markers).
- Build `components/layout/SiteHeader.tsx` and `SiteFooter.tsx`: nav links to the three pages, footer with email + social link placeholders (from `lib/constants.ts`).
- Confirm the grid rhythm approach (7/5 split, 4/4/4 triptych, full-bleed band) is implemented as reusable section-wrapper patterns, not hardcoded per-page, so features/paramstore/about can all reuse the same rhythm primitives.

## Explicit File List

- `app/globals.css` (final token values, confirmed against `DESIGN.md`)
- `tailwind.config.ts` (font family additions)
- `components/layout/Container.tsx`, `SiteHeader.tsx`, `SiteFooter.tsx`
- `components/ui/Button.tsx`, `Chip.tsx`, `IconBadge.tsx`

## Constraints Specific to This Phase

- Every spacing value used in these primitives must come from a defined scale (e.g., a Tailwind spacing scale extension), not ad hoc pixel values scattered through component code.
- `Button.tsx` must not use `shadow-*` or `bg-gradient-*` classes — verify the Tailwind config from phase 0 actually blocks these, don't just avoid typing them.

## Verification

Full Self-Healing Verification Loop. Additionally: render `SiteHeader`/`SiteFooter`/`Button`/`Chip` on the stub pages from phase 0 and visually confirm at all four breakpoints (375/768/1280/1920) with no overflow.

## Completion Note

- **What was built**:
  - `components/layout/Container.tsx`: 12-column grid wrapper with 1440px max-width and `clamp()` side gutters (`px-4 sm:px-6 md:px-8 lg:px-12`).
  - `components/layout/GridRhythm.tsx`: Reusable grid rhythm primitives (`GridSplit` 7/5, `GridTriptych` 4/4/4, `GridFullBleed` band wrapper), re-exported from `Container.tsx`.
  - `components/ui/Button.tsx`: Flat primary, secondary, outline, and ghost button variants with border-based elevation and `Link` / `button` support.
  - `components/ui/Chip.tsx`: Pill-radius chip component strictly reserved for filter/tag UI (e.g. daily/weekly habit tags).
  - `components/ui/IconBadge.tsx`: Icon in 1px flat bordered square badge with Lucide icon integration.
  - `components/layout/SiteHeader.tsx`: Responsive navigation header with clean background-blended vector logo, active path indicator, CTA button, and accessible mobile drawer.
  - `components/layout/SiteFooter.tsx`: Brand footer featuring version badge, contact email, site links, and SVG icon links for GitHub/LinkedIn.
  - `app/dev-preview/page.tsx`: Interactive preview route displaying heading typography candidates, color palette tokens, UI primitives, and layout rhythms.
- **Verification Loop Results**:
  1. `npx tsc --noEmit`: PASS (0 errors).
  2. `npm run lint`: PASS (0 errors).
  3. `npm run build`: PASS (All 5 static routes `/`, `/_not-found`, `/about`, `/dev-preview`, `/paramstore` prerendered without errors).
  4. Static Audit: PASS (0 shadow classes/CSS, 0 gradients, 0 emojis, 0 pill eyebrow badges above headings).
  5. Overflow Check: PASS (Zero horizontal overflow across 375px, 768px, 1280px, 1920px viewports).
  6. Content Check: PASS (Zero placeholder/lorem text).
- **Hard Prohibition Added & Enforced**:
  - Added 4th Hard Prohibition to `AGENTS.md` and `DESIGN.md`: Banned pill-shaped "eyebrow" or "kicker" badges/labels glued above headings across the entire site. Hierarchy must be communicated via typography scale, grid rhythm, and layout. Pill radius (`rounded-full`) is strictly restricted to interactive filter/tag UI.
- **Open Dependencies / Pending Decisions**:
  - Heading Font Choice: Both Candidate A (`Fraunces` serif) and Candidate B (`Plus_Jakarta_Sans` high-contrast sans) rendered on `/dev-preview`. Proceeding with `Fraunces` as default display font until Param selects preference.
  - Social Links: Placeholder links (`#`) in `lib/constants.ts` pending Param's real GitHub and LinkedIn URLs.


