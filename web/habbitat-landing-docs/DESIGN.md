# DESIGN.md — Design System & Page Content Spec

## Design Philosophy

Premium, quiet, confident. The site should feel like it belongs next to the habitAt app itself — light, warm, artistic, with an Indian-modern-minimalist sensibility (drawing on the same jharokha/jali/block-print motif language used in the app's own design docs) — but expressed through layout, typography, and restrained motion rather than through gradients or drop shadows, which are banned outright. Premium is communicated through precise spacing, a strong grid, confident typography, and purposeful motion — not through visual noise.

No gradients. No box-shadows. No emojis. These three rules are absolute across every page, every component, every state (including hover/focus/error states).

## Color Tokens

Define as CSS variables in `app/globals.css`, mapped into Tailwind theme:

```css
:root {
  --color-bg: #FAF7F2;          /* warm off-white, not pure white */
  --color-surface: #FFFFFF;
  --color-ink: #1C1917;         /* near-black warm text */
  --color-ink-muted: #6B6560;
  --color-border: #E4DED4;
  --color-accent: #B5502E;      /* terracotta — Indian block-print red-orange */
  --color-accent-2: #2E5E4E;    /* deep jade green, secondary accent */
  --color-accent-3: #C9A227;    /* muted gold, used sparingly for highlights */
  --color-danger: #A33B2B;      /* error states, distinct from accent */
}
```

Flat fills only. Borders use `--color-border` at 1px, sharp corners or minimally rounded (see Radii below) — never soft glows or shadows to imply elevation. Elevation is implied by border + background contrast only.

## Radii & Borders

- Radius scale: `0px` (default for cards/sections), `4px` (small controls like chips/buttons), `9999px` (pills only where explicitly specified, e.g. the frequency filter chips pattern already used in the app — used sparingly on this site, not as a default button shape).
- **Prohibition on Eyebrow Badges**: Never use pill-shaped (`rounded-full`) or rounded badge "eyebrow" / "kicker" labels sitting above headings to announce section identity (e.g. "Feature Spotlight", "How It Works"). Pill radius is strictly reserved for actual tag/filter UI elements, never as visual decoration above a title.
- Borders: 1px solid `--color-border` for card/section separation. No shadow-based separation anywhere.


## Typography

- Display/heading typeface: a confident serif or high-contrast sans with personality (final selection: agent to propose two options via `next/font` in phase 1 for Param to pick — do not default to Inter/system-ui for headings, that reads as generic/Antigravity-default).
- Body typeface: a clean, highly legible sans (system-ui-adjacent is fine here — body text should disappear, headings should have presence).
- Scale (desktop): H1 64–80px / H2 40–48px / H3 28–32px / Body 16–18px / Small 13–14px. Mobile scale defined in `phases/phase-7-responsive-mobile-build.md`.
- No centered walls of text. Headlines are left-aligned within the grid by default; centered text is reserved for the hero's primary line only, if the hero composition calls for it.

## Layout Grid (this is what prevents the "generic Antigravity landing page" look)

- Desktop container: 12-column CSS grid, max content width **1440px**, side gutters that scale with viewport (`clamp()`-based), not a narrow fixed centered column with large empty side margins.
- No single-column-of-centered-text-and-a-pill-button hero. The hero must use the full grid width: asymmetric composition, with the 3D asset occupying real screen space (not a small centered icon), and copy positioned off-center per the hero phase spec.
- Sections alternate grid rhythm (e.g., a 7/5 split, then a 4/4/4 triptych, then a full-bleed band) so the page has visual variety without ever using a gradient or shadow to create it. Rhythm comes from layout, not decoration.
- Use CSS Grid for section-level layout, Flexbox for component-internal layout (a row of icons, a card's internal stack). Do not use Flexbox for whole-page section layout — that's how the "everything stacked and centered" generic look happens.
- Absolutely no horizontal overflow at any breakpoint. Every image, canvas, and text block must respect `max-width: 100%` inside its grid cell.
- No dead whitespace on laptop widths (1280–1440px): if a section's content doesn't naturally fill the grid, either widen the content treatment (larger type, a secondary visual element in the empty column) or change the grid split for that section — do not leave an empty gutter "for breathing room" as a substitute for a real layout decision.

## Motion System

- **Desktop hero**: GSAP ScrollTrigger pins the hero viewport and scrubs a 3D asset (see Hero Asset below) through a sequence of positions/rotations/camera moves as the user scrolls, exactly like Apple's product pages (e.g., the way a product page rotates and disassembles a device as you scroll past the hero). The scrub must be tied to scroll position (`scrub: true` or a fixed ratio), not to a fixed-duration autoplay — the user's scroll speed controls playback speed.
- **Scroll-reveal for feature sections**: Framer Motion `whileInView` fade/slide-up, staggered per grid item, distance 16–24px, duration 400–600ms, no bounce/elastic easing — use a controlled ease-out. Motion should feel precise, not playful/bouncy.
- **Hover states**: subtle scale (1.0 → 1.02) or border-color shift on interactive elements. No shadow-based hover lift.
- **Reduced motion**: respect `prefers-reduced-motion` — disable the scroll-scrub and swap to a simple crossfade/step sequence.

## Hero 3D Asset (Landing Page)

- Subject: an abstract low-poly composition representing the app's core loop — suggested reading: a stylized phone-frame silhouette with a camera-aperture motif and a rising streak-flame/graph motif, built from flat-shaded geometric primitives, matte materials, single ambient + one directional light (no shadow maps, no specular highlights that read as "shiny/gradient-like").
- Materials: `MeshBasicMaterial` or flat-shaded `MeshStandardMaterial` with shadows disabled, matte, using the color tokens above (terracotta, jade, gold, ink) — the 3D asset should look like it belongs to the same flat palette as the rest of the site, not like a separate glossy 3D-render aesthetic.
- Format: `.glb`, optimized (under 2MB), placed in `public/models/`. If no real model exists yet, phase 2 builds the scene with primitive Three.js geometries (boxes, tori, cones) composed to the same silhouette — this is not "mock data," it's the actual shipped asset, just built from primitives instead of a modeled file. Flag to Param if a custom-modeled `.glb` is wanted instead, as a follow-up.

## Icon Set

`lucide-react` only. Icons used for: feature markers, the view-switcher-style visual motif on the features section (referencing the app's own list/grid/card switcher, as a nice callback), the download button, social links, and nav.

## Page-by-Page Content Spec

### Page 1 — Landing (`/`)

1. **Hero**: scroll-pinned 3D sequence (see above). Primary line: the app's core promise, framed around proof-based habits (final copy TBD by Param — placeholder structure only: a short line naming the app, a subline naming the core mechanic of photo-proof + AI verification + real streaks). CTA pair: "Get the app" (scrolls to or links to ParamStore) and "See how it works" (scrolls to features).
2. **Feature sections** (grid-based, alternating rhythm, one per core mechanic — do not compress into a single feature-icon-grid, each deserves a real section):
   - Photo-proof + AI verification.
   - Real streak tracking (call out that streaks start honestly at zero and build from actual consecutive completions — this is a genuine differentiator, use it).
   - Frequency-aware scheduling (daily/weekly/monthly, "due today" logic, days-until-next-due for weekly/monthly).
   - Calendar view with day-by-day breakdown and proof history.
   - View switcher on the home screen (list/detailed/grid) — a nice visual callback opportunity, screenshot or recreated UI fragment, not invented UI.
3. **How it works**: a short 3-4 step sequence (create habit → set frequency → submit photo proof → AI verifies → streak updates), laid out as a horizontal or diagonal grid sequence, not a plain numbered list.
4. **Closing CTA band**: full-bleed section, restates the core promise, links to ParamStore.

### Page 2 — ParamStore (`/paramstore`)

- App-store-style listing header: app icon, name, one-line tagline, version number, download button (primary action, uses `NEXT_PUBLIC_APK_DOWNLOAD_URL` via the `/paramstore/download` redirect route).
- Screenshot carousel (real app screenshots — flag as an open asset dependency if not yet supplied).
- Feature bullet recap (short, references the landing page sections rather than duplicating full copy).
- Changelog list (version, date, bullet list of changes) — data-driven from a local constant, not hardcoded prose, so it's easy to update per release.
- Install instructions block: explains sideload/"install from unknown sources" step clearly, since this is not a Play Store listing — framed plainly and confidently, not apologetically.
- No Play Store visual language (no Play Store badge, no green Android robot iconography) — this is its own distinct "ParamStore" identity using the site's own design tokens.

### Page 3 — About (`/about`)

- Profile block: Param Khodiyar, role/context (drawn from his profile — CS + Data Science, building habitAt), contact email `paramkhodiyar1008@gmail.com` (fix the typo pattern if the address is ever written with a stray character — always render as this exact address).
- Story block: placeholder container, clearly marked `TODO: story copy pending from Param`, sized and styled as it will appear once real copy is supplied — not left as a blank gap.
- Social/portfolio links: LinkedIn and GitHub icons (via `lucide-react` generic icon fallback is acceptable if brand icons aren't pulled in; if brand icons are added, use a single small brand-icon package consistently, not a mix). Links themselves are placeholders (`#`) until Param supplies the real URLs — flag as an open dependency, do not guess a URL.
- Layout: this page can be quieter/more editorial than the landing page — a single strong column within the grid (not full-width text), generous vertical rhythm, no 3D asset required here.

## What "Generic Antigravity-Style" Looks Like (avoid this explicitly)

- Centered H1, centered subline, one centered pill-shaped CTA button, all inside a narrow max-width column with large empty side gutters on a laptop screen.
- A gradient background blob behind the hero text.
- A 3-column icon-over-heading-over-paragraph feature grid with no rhythm variation for the rest of the page.
- Soft drop-shadows on every card to imply depth.

This project's grid rhythm, flat token system, and scroll-driven hero are the direct countermeasures to each of these, respectively.
