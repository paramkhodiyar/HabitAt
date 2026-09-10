# Phase 4 — Calendar & History UI (the signature screen)

This is the phase where the app has to feel worth building. Read section 8 of `DESIGN.md` before starting — this doc only adds implementation detail on top of it.

## Scope
1. Habit detail screen: months stack chronologically (most recent first), each month renders a 7-column grid of day tiles pulled from `CompletionRecord`.
2. Tile states exactly per the design doc: completed (thumbnail + saffron accent), missed (dark/muted, terracotta hairline), today-incomplete (pulsing outline), future (outlined only).
3. Tapping a completed tile triggers the shared-element transition from design doc §7: the thumbnail visibly grows into a full-screen proof view showing the image, completion time, AI verification confidence/reason, and streak-at-that-point.
4. Month entrance uses the staggered tile animation specified in the design doc.
5. Streak stats block on this screen: current streak, best streak, completion rate — rendered in the display typeface as the emotional focal point of the screen, not a small caption.
6. Home screen's habit card → this detail screen also uses a shared-element transition (the card itself expanding), completing the transition chain set up as a stub in Phase 1.

## Explicitly out of scope
- Alternative views (grid/timeline/streak-only) — noted in the product doc as "Later," don't build them now
- Achievements/gamification badges (Later)
- Any Drive-backed image loading — this phase reads local `imageLocalUri` only; Drive-backed thumbnails arrive naturally once Phase 5 lands, no rework needed if the image-loading layer is abstracted correctly now

## Done criteria
- Scrolling through several months of real completion data feels smooth at 60fps on a Nothing Phone 3a-class device — profile it, don't guess.
- The card → detail → full-screen-proof transition chain is a single continuous physical motion, not three separate screen pushes.
- Missed days are visually honest but never punitive-looking (no red, no shame iconography) per the design doc's tone.
