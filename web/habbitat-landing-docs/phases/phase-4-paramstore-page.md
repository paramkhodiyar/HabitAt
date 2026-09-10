# Phase 4 — ParamStore Page

## Goal

Build the `/paramstore` page: an app-store-style listing for the APK, with its own distinct visual identity (not a copy of the Play Store), per `DESIGN.md`.

## Scope

- `components/paramstore/AppListingHeader.tsx`: icon, name, tagline, version, primary download button.
- `components/paramstore/ScreenshotCarousel.tsx`: horizontally scrollable/swipeable carousel of real app screenshots. If screenshots aren't yet supplied, use correctly-sized flat placeholder frames labeled "Screenshot pending" — not stretched/cropped stand-in images.
- `components/paramstore/ChangelogList.tsx`: data-driven from a typed array in `lib/constants.ts` (`{ version: string; date: string; changes: string[] }[]`), rendered as a clean list, most recent first.
- `components/paramstore/DownloadButton.tsx`: primary CTA, links to `/paramstore/download`.
- `app/paramstore/download/route.ts`: a route handler that 302-redirects to `process.env.NEXT_PUBLIC_APK_DOWNLOAD_URL`, so the changelog/marketing copy never needs to reference a specific file URL directly.
- Install-instructions block: plain, confident explanation of enabling "install from unknown sources," framed as a normal one-time step, not an apology.

## Explicit File List

- `app/paramstore/page.tsx`, `app/paramstore/download/route.ts`
- `components/paramstore/AppListingHeader.tsx`, `ScreenshotCarousel.tsx`, `ChangelogList.tsx`, `DownloadButton.tsx`
- `lib/constants.ts` (extended with changelog data and APK metadata)

## Constraints Specific to This Phase

- No visual element may resemble Play Store branding (green robot, Play Store badge shape/colors, star-rating widget mimicking Play Store's specific rating UI). ParamStore should read as its own thing.
- The redirect route must not hardcode the APK URL — it reads from the environment variable so future version bumps don't require a code change, only an env var update (or a small constants-file update if Param prefers static hosting under `public/apk/`, in which case flag that this env var approach was swapped for a static path and why).
- Screenshot carousel must not overflow horizontally on any breakpoint; confirm swipe/scroll works with mouse-drag on desktop, not just touch.

## Verification

Full Self-Healing Verification Loop, plus: click through the download button and confirm the redirect route resolves (even if the target URL is a placeholder value in dev, confirm the redirect mechanics work, not just that the button exists).

## Completion Note

Phase 4 completed & verified.
- Built `/paramstore` app store page with custom non-Play-Store aesthetic.
- Components implemented: `AppListingHeader.tsx`, `DownloadButton.tsx`, `ScreenshotCarousel.tsx`, `ChangelogList.tsx`.
- Implemented `/paramstore/download/route.ts` 302 redirect route handler for APK download.
- Sideload installation guide added with step-by-step instructions.
- Extended `lib/constants.ts` with changelog and APK spec metadata.
- All checks (`npx tsc --noEmit` and `npm run lint`) passed clean.
