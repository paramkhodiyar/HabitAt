# Phase 6 — Error Pages, Metadata, SEO, Cookie Handling

## Goal

Close out the "production-grade site" requirements: proper error states, unique per-page metadata, favicon/OG assets, and a cookie-consent mechanism that only appears if it's actually needed.

## Scope

### Error Pages

- `app/not-found.tsx`: a real 404 page matching the site's design system (not the framework default) — short message, link back to the landing page, styled with the same tokens/grid as the rest of the site.
- `app/error.tsx`: client-side error boundary for the App Router, same visual treatment, includes a "try again" action (`reset()`), no raw stack trace shown to the user.
- Confirm both are reachable and styled correctly (`not-found.tsx` by visiting a nonexistent route; `error.tsx` by temporarily throwing in a test component, then removing the test throw).

### Metadata

- `lib/metadata.ts`: a shared builder function that takes per-page title/description/OG-image overrides and merges them with site-wide defaults (site name, default OG image, `NEXT_PUBLIC_SITE_URL`-based canonical URLs, Twitter/OG card type).
- Each of `app/page.tsx`, `app/paramstore/page.tsx`, `app/about/page.tsx` exports its own `generateMetadata` (or static `metadata`) with a unique title and description — no duplicate titles across pages.
- Favicon and OG image assets placed under `public/` (flat, on-brand, no gradient/shadow treatment even in the raster OG image design).

### Cookies

- Only add a cookie-consent banner if `NEXT_PUBLIC_ANALYTICS_ID` is set (i.e., analytics was actually added). If no analytics or tracking cookie is introduced anywhere in the project, do not add a cookie banner at all — an unnecessary consent banner is itself a UX defect, not a safety margin.
- If added: a minimal, flat, dismissible bottom bar, using the site's own tokens, with "Accept" and "Decline" actions that actually gate whether the analytics script loads (not a decorative banner that loads analytics regardless of the choice).

## Explicit File List

- `app/not-found.tsx`, `app/error.tsx`
- `lib/metadata.ts`
- `app/page.tsx`, `app/paramstore/page.tsx`, `app/about/page.tsx` (metadata exports added)
- `public/favicon.ico`, `public/og-image.png` (or per-page OG images if warranted)
- `components/ui/CookieBanner.tsx` (only if analytics is actually present)

## Verification

Full Self-Healing Verification Loop, plus: view page source (or use a metadata-preview tool) for all three pages and confirm unique titles/descriptions and correct OG tags; confirm 404 and error boundary render the styled versions, not framework defaults.

## Completion Note

Phase 6 completed & verified.
- Built `lib/metadata.ts` with `constructMetadata()` helper for site-wide defaults, canonical URLs, Twitter cards, and OpenGraph tags.
- Configured unique metadata across all pages (`app/layout.tsx`, `app/paramstore/page.tsx`, `app/about/page.tsx`).
- Implemented `app/not-found.tsx` (custom 404 page matching design tokens).
- Implemented `app/error.tsx` (custom client-side Error Boundary with `reset()` action).
- Created `components/ui/CookieBanner.tsx` with strict conditional rendering (returns null if `NEXT_PUBLIC_ANALYTICS_ID` is empty).
- Verified zero prohibited visual patterns (0 box shadows, 0 gradients, 0 emojis, 0 pill eyebrow badges).
- All checks (`npx tsc --noEmit`, `npm run lint`, and `npm run build`) passed 100% clean.
