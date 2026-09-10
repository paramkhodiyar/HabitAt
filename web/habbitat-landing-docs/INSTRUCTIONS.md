# INSTRUCTIONS.md — Tech Stack, Structure, Commands

## Tech Stack

- **Framework**: Next.js 14+, App Router, TypeScript strict mode. No `pages/` directory — `app/` only.
- **Styling**: Tailwind CSS with a custom token layer (see `DESIGN.md`) defined via CSS variables in `app/globals.css` and mapped into `tailwind.config.ts`. No component-level inline gradient/shadow utilities — these are removed from the Tailwind theme entirely (`boxShadow` and gradient utilities disabled in the theme config, not just unused).
- **Animation**:
  - **Framer Motion** for UI-level transitions (page transitions, hover/tap states, reveal-on-scroll for non-3D sections).
  - **GSAP + ScrollTrigger** for the scroll-pinned hero sequence on desktop (precise scrubbing tied to scroll position, matching the Apple product-page pacing model).
  - **React Three Fiber (`@react-three/fiber`) + `@react-three/drei`** for the 3D hero asset on desktop only. Mobile uses a lighter 2D/sprite-based substitute — see `phases/phase-7-responsive-mobile-build.md`.
- **Icons**: a single icon set, `lucide-react`, used consistently across all three pages. No mixed icon libraries.
- **Fonts**: self-hosted via `next/font/local` or `next/font/google` (final choice recorded in `DESIGN.md`) — no runtime font-loading flash.
- **Package manager**: npm (matches Param's existing workflow — do not introduce yarn/pnpm lockfiles).

## Project Structure

```
habbitat-site/
  app/
    layout.tsx                 (root layout, fonts, metadata defaults)
    page.tsx                   (landing page)
    globals.css
    paramstore/
      page.tsx
    about/
      page.tsx
    not-found.tsx               (404)
    error.tsx                   (error boundary)
    loading.tsx                 (route-level loading skeleton, flat/no-shimmer-gradient)
  components/
    hero/
      Hero3DScene.tsx            (desktop R3F canvas)
      HeroMobile.tsx              (mobile substitute)
      HeroScrollController.tsx    (GSAP ScrollTrigger wiring, desktop only)
    features/
      FeatureSection.tsx
      FeatureGrid.tsx
    paramstore/
      AppListingHeader.tsx
      ScreenshotCarousel.tsx
      ChangelogList.tsx
      DownloadButton.tsx
    about/
      ProfileCard.tsx
      StoryBlock.tsx
      SocialLinks.tsx
    layout/
      SiteHeader.tsx
      SiteFooter.tsx
      Container.tsx               (max-width grid wrapper, see DESIGN.md)
    ui/
      (shared primitives: Button.tsx, Chip.tsx, IconBadge.tsx, etc.)
  lib/
    metadata.ts                   (shared metadata builder for per-page overrides)
    constants.ts                  (site-wide copy constants: email, links, APK version info)
  public/
    apk/                          (hosted APK file or a redirect config to GitHub Releases — decide in phase 4)
    models/                       (3D assets, .glb format)
    images/
      screenshots/
      icons/
  next.config.ts
  tailwind.config.ts
  tsconfig.json
  package.json
```

Do not deviate from this structure without noting the deviation and reason in the relevant phase's completion note.

## Commands

- `npm run dev` — local dev server.
- `npm run build` — production build; must pass with zero errors before any phase is marked complete.
- `npm run lint` — ESLint; must pass with zero errors.
- `npx tsc --noEmit` — typecheck; must pass with zero errors.
- `npm run start` — serve the production build locally for final QA in phase 8.

## Deployment Notes

- Target platform: Vercel (matches Next.js App Router support out of the box). If Param deploys elsewhere, the only change should be the APK hosting strategy in phase 4 — flag if so.
- Environment variables (leave blank in `.env.local.example`, Param supplies real values):
  - `NEXT_PUBLIC_SITE_URL` — canonical URL, used for metadata/OG tags.
  - `NEXT_PUBLIC_APK_DOWNLOAD_URL` — direct link to the latest APK (GitHub Releases asset URL or self-hosted path).
  - `NEXT_PUBLIC_ANALYTICS_ID` — only if analytics is added in phase 6; leave unset otherwise, and the cookie banner logic must no-op when unset.
- No backend, no database, no API routes beyond a single optional route handler for redirecting `/paramstore/download` to the current APK URL (keeps the download link stable across version bumps without editing page content).

## APK Distribution Note

Since this is direct sideload distribution (not Play Store), the ParamStore page must clearly explain that this is a direct-install APK, note the Android "install from unknown sources" step, and never imply Play Store affiliation in copy, iconography, or the page's visual language.
