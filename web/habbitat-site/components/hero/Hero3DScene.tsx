"use client";

import React from "react";
import { motion } from "framer-motion";
import { Flame, ShieldCheck, Camera, CheckCircle2, Calendar } from "lucide-react";

interface Hero3DSceneProps {
  scrollProgress: number; // 0.0 to 1.0 driven by GSAP
}

export default function Hero3DScene({ scrollProgress = 0 }: Hero3DSceneProps) {
  // Calculate 3D transforms based on scrollProgress
  const rotateY = -12 + scrollProgress * 28; // -12deg to +16deg
  const rotateX = 8 - scrollProgress * 16;   // +8deg to -8deg
  const rotateZ = -2 + scrollProgress * 4;   // -2deg to +2deg
  const translateY = scrollProgress * -20;    // slight upward drift
  const scale = 0.96 + scrollProgress * 0.08;  // slight scale up

  // Parallax offsets for floating 3D layers
  const flameOffset = scrollProgress * 45;
  const badgeOffset = scrollProgress * -35;
  const proofOffset = scrollProgress * 40;

  return (
    <div className="w-full h-[520px] md:h-[620px] relative flex items-center justify-center perspective-[1200px] overflow-visible">
      {/* Main 3D Tilted Device Container */}
      <motion.div
        className="w-[290px] sm:w-[320px] h-[540px] sm:h-[580px] bg-surface border-2 border-ink/80 rounded-[40px] relative p-3 flex flex-col justify-between transition-transform duration-100 ease-out"
        style={{
          transform: `rotateY(${rotateY}deg) rotateX(${rotateX}deg) rotateZ(${rotateZ}deg) translateY(${translateY}px) scale(${scale})`,
          transformStyle: "preserve-3d",
        }}
      >
        {/* Phone Outer Edge Trim */}
        <div className="absolute -inset-1 border border-border rounded-[44px] pointer-events-none" />

        {/* Centered Top Punch-Hole Camera Cutout (Nothing Phone 3a reference) */}
        <div className="absolute top-4 left-1/2 -translate-x-1/2 w-3.5 h-3.5 bg-ink rounded-full z-30" />

        {/* Screen Content Wrapper */}
        <div className="w-full h-full bg-bg rounded-[32px] overflow-hidden border border-border flex flex-col justify-between p-4 pt-7 relative">
          
          {/* Top Screen Header */}
          <div className="flex items-center justify-between border-b border-border pb-3">
            <div className="flex items-center gap-2">
              <div className="w-6 h-6 rounded-[4px] bg-accent/10 border border-accent/20 flex items-center justify-center text-accent">
                <Flame size={14} />
              </div>
              <span className="font-serif font-bold text-sm text-ink tracking-tight">
                HabbitAt
              </span>
            </div>
            {/* Streak Counter Pill */}
            <div className="flex items-center gap-1.5 px-2.5 py-1 bg-accent text-surface text-xs font-semibold rounded-full">
              <Flame size={12} className="fill-surface" />
              <span>19 Days</span>
            </div>
          </div>

          {/* Screen Content Body: Active Habit Verification Card */}
          <div className="space-y-3 my-auto">
            {/* Today Habit Card */}
            <div className="bg-surface border border-border p-3.5 rounded-[12px] space-y-2.5">
              <div className="flex items-center justify-between">
                <span className="text-xs font-semibold text-ink-muted uppercase tracking-wider">
                  Today &bull; 6:30 AM
                </span>
                <span className="inline-flex items-center gap-1 text-[11px] font-semibold text-accent-2 bg-accent-2/10 px-2 py-0.5 rounded-[4px] border border-accent-2/20">
                  <CheckCircle2 size={12} />
                  Verified
                </span>
              </div>
              <h4 className="font-serif font-bold text-base text-ink leading-tight">
                Morning 5km Run
              </h4>
              
              {/* Photo Proof Thumbnail Box */}
              <div className="w-full h-24 bg-bg border border-border rounded-[8px] flex flex-col items-center justify-center gap-1 text-ink-muted p-2">
                <div className="w-8 h-8 rounded-full bg-accent/10 border border-accent/20 flex items-center justify-center text-accent">
                  <Camera size={16} />
                </div>
                <span className="text-[11px] font-mono text-ink">
                  Photo Proof Submitted
                </span>
              </div>
            </div>

            {/* Monthly Calendar Proof Grid Fragment */}
            <div className="bg-surface border border-border p-3 rounded-[12px] space-y-2">
              <div className="flex items-center justify-between text-xs text-ink-muted">
                <span className="font-medium flex items-center gap-1">
                  <Calendar size={13} />
                  September Proof Ledger
                </span>
                <span className="font-mono text-[11px]">19/19 Completed</span>
              </div>
              {/* Tile Grid */}
              <div className="grid grid-cols-7 gap-1.5 pt-1">
                {[...Array(14)].map((_, i) => (
                  <div
                    key={i}
                    className={`h-4 rounded-[3px] border ${
                      i < 11
                        ? "bg-accent-2 border-accent-2"
                        : i === 11
                        ? "bg-accent border-accent"
                        : i === 12
                        ? "bg-accent-3 border-accent-3"
                        : "bg-bg border-border"
                    }`}
                  />
                ))}
              </div>
            </div>
          </div>

          {/* Bottom Screen Navigation Bar */}
          <div className="w-full bg-surface border border-border rounded-full py-1.5 px-4 flex items-center justify-around text-ink-muted text-xs">
            <span className="font-bold text-accent">Home</span>
            <span>Calendar</span>
            <span>Settings</span>
          </div>

        </div>

        {/* ------------------------------------------------------------------ */}
        {/* FLOATING 3D PARALLAX LAYERS (Hovering outside the device frame)  */}
        {/* ------------------------------------------------------------------ */}

        {/* Layer A: Floating Saffron Streak Flame Card (Top Right) */}
        <motion.div
          className="absolute -top-6 -right-10 bg-surface border-2 border-accent p-3.5 rounded-[12px] flex items-center gap-3 z-30 pointer-events-none"
          style={{
            transform: `translateZ(60px) translateY(${flameOffset}px)`,
          }}
        >
          <div className="w-10 h-10 rounded-[8px] bg-accent text-surface flex items-center justify-center shrink-0">
            <Flame size={22} className="fill-surface" />
          </div>
          <div className="flex flex-col">
            <span className="text-xs font-bold text-ink">Streak Active</span>
            <span className="text-[11px] font-mono text-accent font-semibold">
              19 Days &bull; Zero Flakes
            </span>
          </div>
        </motion.div>

        {/* Layer B: Floating Vision AI Verification Shield (Top Left) */}
        <motion.div
          className="absolute top-1/3 -left-12 bg-surface border-2 border-accent-2 p-3 rounded-[12px] flex items-center gap-3 z-30 pointer-events-none"
          style={{
            transform: `translateZ(75px) translateY(${badgeOffset}px)`,
          }}
        >
          <div className="w-9 h-9 rounded-[8px] bg-accent-2/10 border border-accent-2/20 flex items-center justify-center text-accent-2 shrink-0">
            <ShieldCheck size={20} />
          </div>
          <div className="flex flex-col">
            <span className="text-xs font-bold text-ink">Vision AI Guard</span>
            <span className="text-[11px] font-mono text-accent-2">
              99.4% Match
            </span>
          </div>
        </motion.div>

        {/* Layer C: Floating Proof Ledger Badge (Bottom Right) */}
        <motion.div
          className="absolute -bottom-4 -right-8 bg-surface border-2 border-border p-3 rounded-[12px] flex items-center gap-2.5 z-30 pointer-events-none"
          style={{
            transform: `translateZ(50px) translateY(${proofOffset}px)`,
          }}
        >
          <div className="w-8 h-8 rounded-[6px] bg-bg border border-border flex items-center justify-center text-ink shrink-0">
            <Camera size={16} />
          </div>
          <div className="flex flex-col">
            <span className="text-xs font-bold text-ink">Photo Proof</span>
            <span className="text-[10px] text-ink-muted">Timestamp Logged</span>
          </div>
        </motion.div>

      </motion.div>
    </div>
  );
}
