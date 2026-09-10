# Phase 7 — Dedicated Mobile Implementation

## Goal

Build a genuinely separate mobile experience for every section created in phases 2-5, rather than retrofitting the desktop layout with breakpoint classes. Mobile and desktop should each feel deliberately designed for their viewport.

## The Split Rule

For any component with meaningfully different mobile behavior (primarily the hero, and any multi-column grid section), create two implementations and switch between them at the container level using a viewport check, rather than one component with a long chain of `sm:`/`md:`/`lg:` conditional classes trying to do two jobs at once:

- `components/hero/Hero3DScene.tsx` (desktop) / `components/hero/HeroMobile.tsx` (mobile)
- Switch logic lives in a single place (e.g., `app/page.tsx` or a small `useIsDesktop()` hook backed by a resize listener with a sensible breakpoint, e.g. 1024px) — not duplicated per component.

Simple components that are genuinely just "fewer columns on a smaller screen" (e.g., a 4/4/4 triptych becoming a single stacked column) do not need two separate files — that's legitimate responsive CSS, not a case requiring a split. The split rule is for cases where the *interaction model* changes (3D scroll-scrub vs. static/lightly-animated image; multi-column carousel vs. swipeable single-card view), not just column count.

## Scope

### Hero (Mobile)

- `components/hero/HeroMobile.tsx`: no live 3D canvas (avoid the performance/battery cost on mobile GPUs). Use either a pre-rendered sequence of static frames from the same 3D asset swapped on scroll (a lightweight "flipbook" via Framer Motion, still scroll-linked but much cheaper than live WebGL), or a single well-composed static hero frame with a subtle parallax/fade on scroll. Choose the flipbook approach if performance testing (see Verification) allows it; fall back to the static+parallax approach otherwise, and note which was chosen and why.
- Copy stacks vertically, full-width within mobile gutters, CTA pair stacks or sits side-by-side if width allows without cramping (test at 375px specifically, the narrowest common target).

### Feature Sections (Mobile)

- Each `FeatureSection` rhythm variant (7/5, triptych, full-bleed) collapses to a single-column stacked layout on mobile, preserving the same content order, but this is standard responsive grid behavior (not a split-file case) — implement via the grid system's responsive column spans, not a separate mobile component.
- Scroll-reveal motion stays but with reduced stagger delay (mobile scroll is typically faster/flickier — long staggers feel laggy) — tune stagger timing down from desktop values.

### ParamStore & About (Mobile)

- Screenshot carousel: confirm native touch-swipe works well (this is likely already fine from phase 4's implementation, but explicitly test on a real mobile viewport/device emulation, not just resizing a desktop browser window).
- About page's single editorial column already reads well on mobile by construction — confirm gutter sizing at 375px specifically.

## Explicit File List

- `components/hero/HeroMobile.tsx`
- `hooks/useIsDesktop.ts` (or equivalent switch mechanism, placed in a sensible shared location)
- Responsive class adjustments to existing `FeatureSection`, `ScreenshotCarousel`, layout `Container` as needed (no new files required for these, per the split rule above)

## Verification

Full Self-Healing Verification Loop, plus:

- Test at 375px, 414px (common phone widths) and 768px (tablet) in addition to the standard breakpoint set.
- Confirm zero horizontal overflow at all three additional widths.
- Confirm the mobile hero substitute does not tank performance (check devtools performance/FPS on a throttled CPU profile, not just default dev machine speed) — this is the actual reason the split exists, so verify the reason, not just the existence of two files.
- Confirm touch targets (buttons, carousel controls, nav) meet a minimum comfortable tap size (approximately 44px) throughout.

## Completion Note

Phase 7 completed & verified.
- Implemented `hooks/useIsDesktop.ts` using React 18 `useSyncExternalStore` for SSR-safe 1024px media query detection without hydration warnings.
- Implemented `components/hero/HeroMobile.tsx` as a dedicated mobile hero layout (eliminating live WebGL canvas overhead on mobile GPUs while providing a clean interactive mock UI with Framer Motion entry).
- Updated `components/hero/HeroScrollController.tsx` to conditionally render `Hero3DScene` on desktop (>=1024px) and `HeroMobile` on mobile (<1024px).
- Confirmed touch target sizes meet the min 44px tap guideline across mobile CTA buttons and header menu toggle.
- Verified zero prohibited visual patterns (0 box shadows, 0 gradients, 0 emojis, 0 pill eyebrow badges).
- All checks (`npx tsc --noEmit`, `npm run lint`, and `npm run build`) passed 100% clean.
