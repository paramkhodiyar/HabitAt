# HabbitAt — Design System (canonical, read by every phase)

This doc is the visual and motion law for both the Android app and the Mac sync app. Any agent implementing any screen reads this first. If a phase doc doesn't mention a visual detail, this doc decides it — don't invent a new pattern ad hoc.

## 1. Direction: "Modern Indian Minimalism"
Not a festive/gaudy skin, not a stock Material app. The goal is: warm, light, editorial, with Indian motifs used as *texture*, not decoration slapped on top. Think hand-printed block-print linework, kolam/rangoli geometry, and miniature-painting border patterns — reduced to thin, low-opacity line art sitting quietly behind content, in a Scandinavian-minimalist layout.

## 2. Color tokens
Light theme only for MVP (dark mode is a Phase 6+/later concern, not before).
- **Base / surface:** warm ivory `#FAF6EF`, card surface `#FFFFFF`
- **Ink:** deep charcoal-brown `#2A2320` for primary text (not pure black — keeps the warmth)
- **Primary accent — Saffron:** `#E8A33D` (streaks, primary CTA, "on fire" states)
- **Secondary accent — Indigo:** `#3C4B8C` (secondary actions, links, calm states)
- **Tertiary accent — Terracotta:** `#C1543C` (rejection/miss states, used sparingly — never alarming red)
- **Success — Turmeric-green:** `#7A8B4C` (verified/accepted proof)
- **Muted line art:** ink color at 4–8% opacity only, never competing with content
- No gradients as a crutch; a single soft radial glow behind the streak flame is the one intentional exception, used consistently.

## 3. Typography
- Display/headline: a distinctive humanist serif or high-contrast serif-adjacent display face (e.g. something in the spirit of Fraunces or Tiempos) for streak numbers, screen titles, big moments ("Day 19 survives.").
- Body/UI: a clean grotesk (e.g. Inter or Manrope) for everything functional — habit lists, settings, notification text.
- Numbers (streaks, percentages) always use the display face and tabular figures — they are the emotional payload of this app, they should look like a headline, not a label.

## 4. Backgrounds
Every screen has a very subtle full-bleed background layer of line-art motif (kolam dot-grid lines, or a paisley/mango-leaf line pattern, or a miniature-painting border) at low opacity, unique per major screen (Home ≠ Calendar ≠ Settings) so screens are visually distinguishable at a glance without color-coding.
- Source these as SVG line-art assets from open, attribution-free sources (e.g. SVGRepo, unDraw-style pattern packs, or Freepik assets licensed for the use case) — vectorized so they scale and can be recolored to the ink token, or recreate simplified versions as native vector paths if licensing is unclear. Never bake a raster photo into a background.
- Never let the motif reduce text contrast — verify against the ink/surface tokens.

## 5. Navigation — floating bottom bar (hard constraint)
- No navigation drawer. No sidebar. No hamburger menu. Ever.
- A single floating pill-shaped bottom bar, inset from the screen edges and from the bottom, sitting above the system gesture area — never touching the edges of the display.
- 3–4 destinations max: Home, Calendar/Habits, Settings (add a 4th only if Phase 4+ genuinely needs it, e.g. Stats).
- Frosted/blurred translucent surface over the background motif, saffron indicator for the active tab, spring-based icon scale/lift on selection, not a flat color swap.
- Camera/proof-submission is **not** a nav destination — it's a contextual action from a habit card, so the bar stays clean.

## 6. Safe areas — Nothing Phone 3a as reference device
- Design for edge-to-edge with a centered top punch-hole camera; keep any top app-bar content clear of the camera cutout with real safe-area insets, not a hardcoded pixel guess.
- Gesture navigation (no 3-button nav assumed) — the floating bottom bar's bottom inset must respect the system gesture inset so it never overlaps or gets obscured by the gesture pill.
- Verify layouts also hold up on a generic ~6.1"–6.7" Android baseline (Nothing Phone 3a-class), not just the exact device.

## 7. Motion — this is where "crazy" lives, and it means smooth, not gimmicky
- Every transition uses spring physics (natural damping/stiffness), never linear/ease curves, so motion feels physical.
- Shared-element transitions are mandatory for: a habit card on Home → its Calendar detail screen; a calendar tile's thumbnail → its full-screen proof view. The photo should visibly travel and scale, not cut.
- Micro-interactions with intent, not decoration for its own sake:
  - Streak flame has a subtle idle animation and a distinct "level up" burst when a streak milestone is hit.
  - Verification result reveals with a short suspense beat ("VERIFYING…") before a decisive accept/reject animation — this is a core emotional moment in the product, don't rush it, don't overdo it either (under ~1.5s).
  - Calendar tiles animate in with a staggered entrance when a month first renders.
  - Haptic feedback accompanies: proof accepted, streak milestone, and notification escalation opening the app — tuned to feel purposeful, not buzzy.
- Loading states are motion, not spinners: skeleton shimmer or the app's own line-art motif animating subtly, never a bare circular progress indicator.

## 8. Calendar/grid component (signature UI — detailed further in Phase 4)
Borrowing the *interaction concept* from the "One Photo / Day" reference screenshot: months stack chronologically, each day is a small rounded tile, completed days show a thumbnail of the actual submitted proof. HabbitAt's version adds state beyond "has photo or not":
- ✓ completed → proof thumbnail, subtle saffron corner accent
- ✗ missed → dark/muted empty tile, terracotta hairline border, no shame-red
- 🔥 today, incomplete → animated pulsing outline, not filled
- pending/future → outlined tile only, no fill

## 9. Component consistency rule
Whatever animation/gesture library is chosen in Phase 1 (e.g. Compose's built-in animation APIs plus a motion-spec helper module) is used for *every* transition in the app — no screen gets a one-off hand-rolled animation because it was faster to write. Same rule for the Mac app: one animation approach (SwiftUI's native transition/spring system), used everywhere, adapted to macOS conventions (no bottom nav bar on Mac — see Phase 6 for the desktop-appropriate equivalent).
