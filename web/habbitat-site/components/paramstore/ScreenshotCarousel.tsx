"use client";

import React from "react";
import Image from "next/image";
import { Camera, Calendar, ShieldCheck, Flame, CheckCircle2, FileCode } from "lucide-react";

// Exact measured native Android device dimensions: 1080 x 2392 px (Aspect ratio: 2.215)
const REAL_SCREENSHOTS = [
  {
    id: 1,
    title: "Today's Discipline Ledger",
    description: "Home dashboard with active daily habits, target times, and camera actions",
    icon: Flame,
    src: "/images/screenshots/Screenshot_20260910-091455.png",
  },
  {
    id: 2,
    title: "Photographic Calendar History",
    description: "Monthly ledger displaying real proof-photo thumbnails inside calendar tiles",
    icon: Calendar,
    src: "/images/screenshots/Screenshot_20260910-091507.png",
  },
  {
    id: 3,
    title: "Habit Rules & AI Criteria",
    description: "Habit sheet detailing target intervals, schedule, and AI vision rules",
    icon: FileCode,
    src: "/images/screenshots/Screenshot_20260910-091522.png",
  },
  {
    id: 4,
    title: "Live Photo-Proof Camera",
    description: "Camera capture interface recording live proof (e.g. coding during lecture)",
    icon: Camera,
    src: "/images/screenshots/Screenshot_20260910-091550.png",
  },
  {
    id: 5,
    title: "Vision AI Analysis Pipeline",
    description: "Real-time AI evaluation checking photo evidence against habit criteria",
    icon: ShieldCheck,
    src: "/images/screenshots/Screenshot_20260910-091552.png",
  },
  {
    id: 6,
    title: "Proof Verified & Streak Updated",
    description: "AI confidence rating (90%), verification summary, and streak increment",
    icon: CheckCircle2,
    src: "/images/screenshots/Screenshot_20260910-091600.png",
  },
];

export default function ScreenshotCarousel() {
  return (
    <div className="space-y-4">
      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-2">
        <div>
          <h2 className="font-serif text-2xl font-bold text-ink">
            App Experience & Screenshots
          </h2>
          <p className="text-xs text-ink-muted mt-0.5">
            Real Android app screens showing habit creation, camera proof capture, and AI verification.
          </p>
        </div>
        <span className="text-[11px] font-mono text-ink-muted self-start sm:self-auto border border-border bg-bg px-2.5 py-1 rounded-[2px]">
          Swipe or scroll horizontally &rarr;
        </span>
      </div>

      <div className="w-full overflow-x-auto snap-x snap-mandatory flex gap-6 pb-4 pt-2 scrollbar-none">
        {REAL_SCREENSHOTS.map((item) => {
          const Icon = item.icon;
          return (
            <div key={item.id} className="snap-start shrink-0 space-y-3">
              {/* Phone Device Shell container matching exact 1080:2392 aspect ratio */}
              <div className="relative shrink-0 w-[240px] sm:w-[270px] aspect-[1080/2392] bg-ink rounded-[24px] p-2 border-2 border-border flex flex-col justify-between overflow-hidden shadow-none">
                {/* Device Camera Punchhole Notch */}
                <div className="absolute top-3 left-1/2 -translate-x-1/2 w-16 h-3 bg-surface/80 rounded-full z-20 flex items-center justify-center border border-border/30">
                  <div className="w-2 h-2 bg-ink rounded-full" />
                </div>

                {/* Exact Aspect Housing */}
                <div className="relative w-full h-full bg-[#121110] rounded-[18px] overflow-hidden border border-border/40 flex items-center justify-center">
                  <Image
                    src={item.src}
                    alt={item.title}
                    fill
                    className="object-contain"
                    sizes="(max-width: 640px) 240px, 270px"
                    priority={item.id <= 2}
                  />
                </div>
              </div>

              {/* Labeled Screen Description Card */}
              <div className="w-[240px] sm:w-[270px] bg-surface border border-border p-3.5 rounded-[4px] space-y-1.5">
                <div className="flex items-center gap-2">
                  <div className="w-6 h-6 rounded-[3px] bg-accent/10 border border-accent/20 flex items-center justify-center text-accent shrink-0">
                    <Icon size={14} />
                  </div>
                  <span className="text-xs font-bold text-ink truncate">
                    {item.title}
                  </span>
                </div>
                <p className="text-[11px] text-ink-muted leading-tight">
                  {item.description}
                </p>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
}
