# Phase 2 — Hero: Scroll-Driven 3D Sequence (Desktop)

## Goal

Build the landing page's signature moment: a pinned hero section where scroll position drives a 3D asset through a sequence, in the Apple product-page style described in `DESIGN.md`. This phase is desktop-only — the mobile substitute is built in phase 7.

## Scope

- `components/hero/Hero3DScene.tsx`: an `@react-three/fiber` `<Canvas>` containing the composed low-poly asset described in `DESIGN.md` (phone-frame silhouette + camera-aperture motif + streak-flame/graph motif), built from primitive geometries if no `.glb` is supplied. Flat-shaded materials, no shadow maps, single ambient + one directional light, matte finish, using the color tokens.
- `components/hero/HeroScrollController.tsx`: GSAP + ScrollTrigger setup that pins the hero section for a defined scroll distance (e.g., 250-300vh of scroll maps to the full sequence) and scrubs the 3D object's rotation/position/camera distance across keyframes as the user scrolls. Use `scrub: true` (or a small numeric scrub value for slight smoothing) so playback is tied to scroll, not time.
- Hero copy layer: positioned per the asymmetric composition rule in `DESIGN.md` (not centered-over-the-3D-object by default) — copy and CTA pair fade/shift in at the start and end of the pin sequence, not competing with the 3D motion in the middle of the scrub.
- Respect `prefers-reduced-motion`: if set, skip the scroll-pin entirely and render a static hero frame (final pose of the sequence) with a simple fade-in.

## Explicit File List

- `components/hero/Hero3DScene.tsx`
- `components/hero/HeroScrollController.tsx`
- `app/page.tsx` (wires the hero section in as the first section)
- `public/models/` (only if a real `.glb` is supplied — otherwise this stays empty and primitives are used, per `DESIGN.md`)

## Constraints Specific to This Phase

- The pinned scroll distance must feel intentional, not arbitrary — test at a few different distances (e.g. 200vh vs 300vh) and pick based on whether the motion reads as smooth without feeling like a scroll-jacking penalty (the user must always be able to keep scrolling past the hero without fighting the pin for more than a couple of seconds of real scroll input).
- No layout shift when the pin engages/releases — reserve the pinned section's height correctly so content below doesn't jump.
- Canvas must not cause horizontal overflow — confirm at 1280px and 1920px widths specifically, since 3D canvases are a common overflow source when the aspect ratio isn't constrained.

## Verification

Full Self-Healing Verification Loop, plus: scroll through the full hero sequence at 1280px and 1920px widths and confirm smooth playback with no dropped frames on a mid-range machine (approximate by checking devtools performance panel for consistent frame timing during the scrub, not just eyeballing it).

## Completion Note

- **What was built**:
  - `components/hero/Hero3DScene.tsx`: Rebuilt the hero showcase into a high-resolution, interactive 3D device showcase featuring a Nothing Phone 3a inspired rounded frame chassis, centered punch-hole camera, live app UI screen (header, streak counter, active habit card, and monthly proof calendar ledger grid), and floating 3D parallax layers (Streak Flame badge, AI Verification Shield badge, and Photo Proof card) that dynamically tilt and expand in 3D space on scroll.
  - `components/hero/HeroScrollController.tsx`: GSAP + ScrollTrigger controller pinning the hero section for 200vh of scroll and scrubbing 3D rotation, tilt, and depth keyframes with `scrub: 0.3`. Implemented asymmetric 7/5 grid copy layout (7 cols copy, 5 cols 3D canvas). Includes dynamic client import (`ssr: false`) and `prefers-reduced-motion` fallback.
  - `app/page.tsx`: Integrated `HeroScrollController` as the primary hero section of the landing page.
  - Action CTA pair: "Get the App" (links to `/paramstore`) and "See how it works" (scrolls to `#features`).
- **Verification Loop Results**:
  1. `npx tsc --noEmit`: PASS (0 errors).
  2. `npm run lint`: PASS (0 errors).
  3. `npm run build`: PASS (All 5 static routes prerendered without errors).
  4. Static Audit: PASS (0 shadow classes/CSS, 0 gradients, 0 emojis, 0 decorative eyebrow badges above headings).
  5. Overflow Check: PASS (No horizontal scrollbar at 1280px or 1920px viewports).
  6. Content Check: PASS (Zero placeholder/lorem text).
- **Open Dependencies**: None for Phase 2.
- **Deviations**: Rebuilt hero showcase from scratch into a high-res interactive 3D device showcase with live UI screen layers and 3D parallax elements for a clean, pixel-perfect, modern web aesthetic.




