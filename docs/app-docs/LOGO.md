# LOGO.md — Logo & App Icon Design Guide

Same design law as `DESIGN.md` applies here: Modern Indian Minimalism. For the mark itself, go one layer deeper and pull specifically from **Rajasthani visual language** — not generic "Indian pattern," but the actual vocabulary of Rajasthan: jharokha arches, jali lattice screens, block-print (Bagru/Sanganeri) botanicals, the kalash, and the diya flame — all reduced to clean, modern linework. Ornate source, minimal execution. That tension is the whole point.

## 1. What the mark has to communicate
habitAt is about a streak that's alive and a photographic proof-ledger. The logo should carry at least one of: **fire/flame (the streak)**, **a completed mark (proof/verification)**, or **a window/frame (the jharokha, echoing "looking into your day")**. Don't try to cram all three into one mark — pick the strongest single idea and execute it with total confidence.

## 2. Four concept directions — build all four as quick explorations, then pick one to refine

### A. Jharokha Flame (primary recommendation)
A jharokha's arched window silhouette — the classic Rajasthani balcony-window shape, scalloped or pointed arch — rendered as a single-weight monoline outline, with a small abstracted diya-flame shape sitting inside/emerging from the arch. Reads as "a window onto your streak." Scales down cleanly to an app-icon-sized glyph because it's one continuous line.

### B. Kalash Checkmark
The kalash (the auspicious pot shape used at thresholds and completions in Rajasthani/Indian visual culture) abstracted so its silhouette doubles as a checkmark — the pot's neck-and-body curve reads as a "done" tick from a small distance. Ties completion/auspiciousness to "habit completed" without using a generic checkmark.

### C. Jali Grid Mark
A small grid of dots or square tiles — 3×3 or 4×4 — styled after jali lattice screens, arranged so the negative space between filled tiles forms either an "H" or a simple flame silhouette. This one deliberately echoes the app's own calendar-grid UI, so the logo and the product's signature screen feel like the same idea at two scales.

### D. Diya Streak
A single diya (oil lamp) flame where the flame's tip flicks upward into a small arrow/tick shape — flame-as-progress. Most literal of the four; keep as a backup if A–C don't resolve well at small sizes.

**Recommendation:** build A (Jharokha Flame) first — it has the clearest single silhouette, the best small-size legibility, and the most direct Rajasthani reference without needing color to read.

## 3. Construction rules
- **Monoline first.** Draw the chosen mark as a single consistent stroke weight before considering any fill — this is what keeps an ornate-inspired shape from becoming cluttered. Fill/negative-space versions come after the line version reads clearly.
- **Grid it properly.** Build on a simple square construction grid (e.g. 24×24 or 48×48 base units) so the arch curvature, flame taper, and any lattice spacing are proportionally consistent — not eyeballed.
- **One silhouette, no fine detail.** At app-icon size (as small as ~24dp in a notification), any interior detail below roughly 1/8th of the mark's total size will disappear — if a version relies on fine linework to read, it has failed the size test, simplify further.
- **Optical, not mathematical, centering.** Arches and flames are asymmetric shapes — center them by eye against the icon's safe area, not by bounding-box math.

## 4. Color application
- **Primary lockup:** saffron (`#E8A33D`) mark on the warm ivory (`#FAF6EF`) surface — matches `DESIGN.md` exactly, no separate "brand palette."
- **Reversed/dark-surface version:** ivory mark on deep charcoal-brown ink (`#2A2320`) — needed for splash screens or any future dark-mode surface, build it now even though dark mode is a later phase.
- **Monochrome-only version:** a single flat ink-color silhouette, no accent color at all — required for the Android notification icon (Android renders notification icons as flat white/ink silhouettes regardless of source color, so this version must read correctly with zero color information).
- Never use the indigo, terracotta, or turmeric-green tokens in the primary mark — those are UI-state colors from `DESIGN.md`, not brand colors; keep the mark itself to the saffron/ivory/ink relationship so it stays recognizable independent of in-app state colors.

## 5. Required deliverables
- Primary full-color lockup (mark + wordmark, mark alone)
- Reversed (dark-surface) version
- Flat monochrome silhouette (for Android adaptive icon's foreground layer and the notification-bar icon)
- Adaptive icon split: foreground (the mark, with safe padding per Android's adaptive icon spec) and background (a flat ivory or a very subtle version of the low-opacity line-art motif from `DESIGN.md`, never busy)
- Favicon/tiny-size version (verify legibility at 16–32px equivalent before finalizing)
- macOS app icon treatment: apply the same mark inside macOS's rounded-square icon convention, consistent with the Android adaptive icon's foreground so both platforms are unmistakably the same product

## 6. Do / Don't
- **Do** keep the Rajasthani reference recognizable to someone who knows the source (a jharokha silhouette should actually read as a jharokha) — don't sand it down into an abstract shape with no cultural specificity left.
- **Do** test every candidate at real app-icon size early, not after full refinement.
- **Don't** add a gradient, drop shadow, or bevel to "modernize" it — flat, confident linework/silhouette only, consistent with `DESIGN.md`.
- **Don't** use a generic flame clip-art shape — the flame (in options A/D) should feel hand-drawn/calligraphic in spirit, matching the display typeface's character, not a stock icon-font glyph.
- **Don't** finalize a direction without checking it at both the largest context (a splash screen) and the smallest (a notification icon) — a mark that only works at one size isn't done.
