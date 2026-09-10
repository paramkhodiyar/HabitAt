# Phase 0 — Project Setup

## Goal

Scaffold the Next.js project exactly matching the structure in `INSTRUCTIONS.md`, with all tooling wired and passing, before any real page content is written.

## Scope

- Initialize Next.js 14+ with App Router, TypeScript strict mode.
- Install and configure: Tailwind CSS, `lucide-react`, `framer-motion`, `gsap`, `@react-three/fiber`, `@react-three/drei`, `three`.
- Create the full folder structure from `INSTRUCTIONS.md` with empty/stub files (components can be empty functional stubs returning `null` at this stage — the goal is structure and build correctness, not content).
- Configure `tailwind.config.ts`: disable default `boxShadow` and gradient utility classes in the theme (do not just avoid using them — remove them so a future edit can't accidentally reach for `shadow-md`).
- Wire the CSS variable color tokens from `DESIGN.md` into `globals.css` and `tailwind.config.ts` theme extension.
- Set up `.env.local.example` with the three variables from `INSTRUCTIONS.md`, values left blank.
- Root `app/layout.tsx`: base HTML structure, font loading (two heading-font candidates staged for phase 1 selection), default metadata shell (real per-page metadata comes in phase 6).

## Out of Scope

- No real page content yet — `page.tsx` files can render a minimal placeholder heading confirming the route resolves, nothing more.
- No 3D scene content yet — just confirm the R3F/Three.js packages install and a blank `<Canvas>` renders without error.

## Explicit File List

- `package.json`, `tsconfig.json`, `next.config.ts`, `tailwind.config.ts`
- `app/layout.tsx`, `app/globals.css`, `app/page.tsx`
- `app/paramstore/page.tsx`, `app/about/page.tsx`
- Empty component stubs per the `components/` tree in `INSTRUCTIONS.md`
- `lib/metadata.ts`, `lib/constants.ts` (constants file can hold the email address and placeholder link values now)
- `.env.local.example`

## Verification

Run the full Self-Healing Verification Loop from `00-master-plan.md`. At this phase, steps 4–6 (design audit, overflow, content) are trivially satisfied since there's no real content — confirm anyway rather than skipping, so the habit of running the full loop is established from phase 0.

## Completion Note

- **What was built**: 
  - Scaffolded Next.js 16 (App Router + TypeScript) project at `web/habbitat-site`.
  - Installed all required animation, 3D, and UI packages (`lucide-react`, `framer-motion`, `gsap`, `@react-three/fiber`, `@react-three/drei`, `three`, `@types/three`).
  - Defined CSS variable color tokens from `DESIGN.md` in `app/globals.css` and extended theme in `tailwind.config.ts`.
  - Explicitly disabled `boxShadow` and gradient utility classes in `tailwind.config.ts` and `app/globals.css`.
  - Configured font loading in `app/layout.tsx` using `next/font/google` (`Fraunces` serif heading candidate, `Plus_Jakarta_Sans` sans heading candidate, `Inter` body).
  - Initialized routes `app/page.tsx`, `app/paramstore/page.tsx`, `app/about/page.tsx`, `app/not-found.tsx`, `app/error.tsx`, `app/loading.tsx`.
  - Created component stubs for `hero/`, `features/`, `paramstore/`, `about/`, `layout/`, and `ui/`.
  - Created `.env.local.example`, `lib/constants.ts`, and `lib/metadata.ts`.
- **Verification Loop Results**:
  1. `npx tsc --noEmit`: PASS (0 errors).
  2. `npm run lint`: PASS (0 errors).
  3. `npm run build`: PASS (all 4 static routes prerendered cleanly).
  4. Static Audit: PASS (0 shadow utilities/CSS, 0 gradients, 0 emojis).
  5. Overflow Check: PASS (`overflow-x: hidden` on html/body, zero scrollbars).
  6. Content Check: PASS (no unapproved mock/lorem content).
- **Open Dependencies**: None for Phase 0.
- **Deviations**: Scaffolded project inside `web/habbitat-site` alongside `web/habbitat-landing-docs` to keep docs and site codebase cleanly structured.

