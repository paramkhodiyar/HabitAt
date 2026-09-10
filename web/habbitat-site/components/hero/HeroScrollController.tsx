"use client";

import React, { useEffect, useRef, useState } from "react";
import gsap from "gsap";
import { ScrollTrigger } from "gsap/ScrollTrigger";
import dynamic from "next/dynamic";
import Container from "@/components/layout/Container";
import Button from "@/components/ui/Button";
import HeroMobile from "./HeroMobile";
import { useIsDesktop } from "@/hooks/useIsDesktop";
import { ArrowDownToLine, ArrowDown, ShieldCheck, Flame } from "lucide-react";

// Dynamically import Canvas component to prevent SSR hydration mismatches
const Hero3DScene = dynamic(() => import("./Hero3DScene"), {
  ssr: false,
  loading: () => (
    <div className="w-full h-[450px] md:h-[600px] flex items-center justify-center border border-border bg-surface/50 rounded-[4px]">
      <span className="text-xs font-mono text-ink-muted">
        Loading 3D Canvas...
      </span>
    </div>
  ),
});

export default function HeroScrollController() {
  const isDesktop = useIsDesktop(1024);
  const pinTargetRef = useRef<HTMLDivElement>(null);
  const containerRef = useRef<HTMLDivElement>(null);
  const [scrollProgress, setScrollProgress] = useState(0);
  const [isReducedMotion, setIsReducedMotion] = useState(false);

  useEffect(() => {
    // Only run GSAP ScrollTrigger if on desktop and reduced motion is off
    if (!isDesktop) return;

    gsap.registerPlugin(ScrollTrigger);

    const mediaQuery = window.matchMedia("(prefers-reduced-motion: reduce)");
    if (mediaQuery.matches) {
      requestAnimationFrame(() => {
        setIsReducedMotion(true);
        setScrollProgress(1.0);
      });
      return;
    }

    const pinTarget = pinTargetRef.current;
    if (!pinTarget) return;

    const trigger = ScrollTrigger.create({
      trigger: pinTarget,
      start: "top top",
      end: "+=200%", // 200vh pinned distance
      pin: true,
      pinSpacing: true,
      scrub: 0.3,
      onUpdate: (self) => {
        setScrollProgress(self.progress);
      },
    });

    return () => {
      trigger.kill();
    };
  }, [isDesktop]);

  // Mobile viewport: render lightweight HeroMobile
  if (!isDesktop) {
    return <HeroMobile />;
  }

  // Desktop viewport: render full 3D pinned Hero sequence
  return (
    <div ref={pinTargetRef} className="w-full min-h-screen bg-bg relative flex items-center">
      <Container className="py-12 md:py-20">
        <div ref={containerRef} className="grid grid-cols-1 md:grid-cols-12 gap-8 lg:gap-12 items-center">
          {/* Hero Copy (7 Columns, Left-Aligned) */}
          <div className="md:col-span-7 space-y-6">
            <h1 className="font-serif text-4xl sm:text-5xl lg:text-6xl font-bold tracking-tight text-ink leading-[1.1]">
              Discipline isn&apos;t claimed. <br />
              <span className="text-accent">It&apos;s verified.</span>
            </h1>

            <p className="text-ink-muted text-base sm:text-lg lg:text-xl font-normal max-w-xl leading-relaxed">
              HabbitAt enforces personal discipline with daily photo-proof, vision AI verification, and honest streak tracking that starts at zero.
            </p>

            {/* Feature Highlights (No eyebrow badges) */}
            <div className="grid grid-cols-1 sm:grid-cols-2 gap-4 pt-2 pb-4">
              <div className="flex items-center gap-3 border border-border p-3 rounded-[4px] bg-surface">
                <div className="w-8 h-8 rounded-[4px] bg-accent/10 border border-accent/20 flex items-center justify-center text-accent shrink-0">
                  <ShieldCheck size={18} />
                </div>
                <span className="text-xs sm:text-sm font-medium text-ink">
                  Vision AI Photo Verification
                </span>
              </div>
              <div className="flex items-center gap-3 border border-border p-3 rounded-[4px] bg-surface">
                <div className="w-8 h-8 rounded-[4px] bg-accent-2/10 border border-accent-2/20 flex items-center justify-center text-accent-2 shrink-0">
                  <Flame size={18} />
                </div>
                <span className="text-xs sm:text-sm font-medium text-ink">
                  Honest Zero-Based Streaks
                </span>
              </div>
            </div>

            {/* Action CTA Pair */}
            <div className="flex flex-wrap items-center gap-4 pt-2">
              <Button href="/paramstore" variant="primary" size="lg">
                <ArrowDownToLine size={18} />
                <span>Get the App</span>
              </Button>
              <Button href="#features" variant="outline" size="lg">
                <span>See how it works</span>
                <ArrowDown size={16} />
              </Button>
            </div>
          </div>

          {/* Hero 3D Canvas (5 Columns, Right-Aligned) */}
          <div className="md:col-span-5 relative w-full">
            <Hero3DScene scrollProgress={isReducedMotion ? 1.0 : scrollProgress} />
          </div>
        </div>
      </Container>
    </div>
  );
}
