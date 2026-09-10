"use client";

import React from "react";
import { Camera, Calendar, ShieldCheck, Flame } from "lucide-react";

const SCREENSHOT_PLACEHOLDERS = [
  {
    id: 1,
    title: "Home Habit List & Due Today Counter",
    description: "Daily commitments with exact reminder alarms",
    icon: Flame,
  },
  {
    id: 2,
    title: "Camera Photo-Proof Capture Flow",
    description: "Timestamped photo capture directly in app",
    icon: Camera,
  },
  {
    id: 3,
    title: "Vision AI Verification Result",
    description: "Contextual AI confidence rating & accept/reject beat",
    icon: ShieldCheck,
  },
  {
    id: 4,
    title: "Photographic Ledger & Monthly Grid",
    description: "Month-by-month proof photo history calendar",
    icon: Calendar,
  },
];

export default function ScreenshotCarousel() {
  return (
    <div className="space-y-4">
      <div className="flex items-center justify-between">
        <h2 className="font-serif text-2xl font-bold text-ink">
          App Preview & Screenshots
        </h2>
        <span className="text-xs font-mono text-ink-muted">
          Scroll horizontally to inspect screens
        </span>
      </div>

      <div className="w-full overflow-x-auto snap-x snap-mandatory flex gap-6 pb-4 scrollbar-none">
        {SCREENSHOT_PLACEHOLDERS.map((item) => {
          const Icon = item.icon;
          return (
            <div
              key={item.id}
              className="snap-start shrink-0 w-[260px] sm:w-[300px] h-[520px] bg-surface border border-border rounded-[12px] p-4 flex flex-col justify-between space-y-4"
            >
              {/* Phone Status Bar Frame Representation */}
              <div className="w-full flex items-center justify-between border-b border-border pb-2 text-[10px] font-mono text-ink-muted">
                <span>06:30 AM</span>
                <span>HabbitAt v1.0.2</span>
              </div>

              {/* Placeholder Content Block */}
              <div className="flex-1 bg-bg border border-dashed border-border rounded-[8px] p-6 flex flex-col items-center justify-center text-center gap-3">
                <div className="w-12 h-12 rounded-[8px] bg-surface border border-border flex items-center justify-center text-accent">
                  <Icon size={24} />
                </div>
                <div className="space-y-1">
                  <span className="text-xs font-bold text-ink block">
                    {item.title}
                  </span>
                  <span className="text-[11px] text-ink-muted block leading-relaxed">
                    {item.description}
                  </span>
                </div>
                <span className="text-[10px] font-mono text-ink-muted bg-surface px-2.5 py-1 rounded-[4px] border border-border mt-2">
                  [ Screenshot Pending ]
                </span>
              </div>

              <div className="text-[11px] font-mono text-ink-muted text-center">
                Screen {item.id} of {SCREENSHOT_PLACEHOLDERS.length}
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
}
