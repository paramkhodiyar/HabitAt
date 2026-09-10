"use client";

import React, { useState } from "react";
import SiteHeader from "@/components/layout/SiteHeader";
import SiteFooter from "@/components/layout/SiteFooter";
import Container from "@/components/layout/Container";
import HeroScrollController from "@/components/hero/HeroScrollController";
import FeatureSection from "@/components/features/FeatureSection";
import FeatureGrid from "@/components/features/FeatureGrid";
import Button from "@/components/ui/Button";
import Chip from "@/components/ui/Chip";
import IconBadge from "@/components/ui/IconBadge";
import {
  Camera,
  ShieldCheck,
  Flame,
  Calendar,
  Clock,
  LayoutList,
  LayoutGrid,
  CheckCircle2,
  XCircle,
  ArrowRight,
  ArrowDownToLine,
} from "lucide-react";

export default function HomePage() {
  const [activeFrequency, setActiveFrequency] = useState<"daily" | "weekly" | "monthly">("daily");
  const [activeViewMode, setActiveViewMode] = useState<"list" | "detailed" | "grid">("detailed");

  return (
    <div className="min-h-screen flex flex-col bg-bg text-ink">
      <SiteHeader />

      <main className="flex-1">
        {/* Phase 2 Hero Section */}
        <HeroScrollController />

        {/* ================================================================= */}
        {/* FEATURE 1: Photo-Proof + AI Verification (Split 7/5)             */}
        {/* ================================================================= */}
        <FeatureSection
          id="features"
          rhythm="split"
          title="Proof-Based Verification. Powered by Vision AI."
          description="No self-reported checkmarks or easy unearned credit. Submit a photo of your completed habit, and our vision model evaluates context, objects, and timestamps against your habit rules before approving."
          leftContent={
            <div className="space-y-3 pt-2">
              <div className="flex items-center gap-3">
                <IconBadge icon={ShieldCheck} variant="jade" size="sm" />
                <span className="text-sm font-medium text-ink">
                  Contextual AI vision check prevents photo spoofing
                </span>
              </div>
              <div className="flex items-center gap-3">
                <IconBadge icon={Camera} variant="accent" size="sm" />
                <span className="text-sm font-medium text-ink">
                  Timestamped camera capture directly inside the app
                </span>
              </div>
            </div>
          }
          rightContent={
            <div className="bg-surface border border-border p-6 rounded-[4px] space-y-4">
              <div className="flex items-center justify-between border-b border-border pb-3">
                <span className="text-xs font-mono font-semibold text-ink-muted uppercase">
                  AI Verification Panel
                </span>
                <span className="inline-flex items-center gap-1 text-xs font-semibold text-accent-2 bg-accent-2/10 px-2.5 py-1 rounded-[4px] border border-accent-2/20">
                  <CheckCircle2 size={14} /> 98.4% Confidence
                </span>
              </div>

              <div className="space-y-3">
                <div className="text-sm font-semibold text-ink">
                  Habit: Morning Workout &bull; 6:30 AM
                </div>

                <div className="w-full h-36 bg-bg border border-border rounded-[4px] flex flex-col items-center justify-center gap-2 p-4 text-center">
                  <Camera size={24} className="text-accent" />
                  <div className="text-xs font-mono text-ink">
                    [ Submitted Photo Evidence: Gym Equipment Recognized ]
                  </div>
                  <div className="text-[11px] text-ink-muted">
                    Analyzed by HabbitAt Vision AI Pipeline
                  </div>
                </div>

                <div className="grid grid-cols-2 gap-3 pt-1">
                  <div className="border border-border p-2.5 rounded-[4px] bg-bg text-xs">
                    <span className="text-ink-muted block text-[10px] font-mono">OBJECT MATCH</span>
                    <span className="font-semibold text-ink">Dumbbells & Mat</span>
                  </div>
                  <div className="border border-border p-2.5 rounded-[4px] bg-bg text-xs">
                    <span className="text-ink-muted block text-[10px] font-mono">TIMESTAMP</span>
                    <span className="font-semibold text-ink">Within 15 Min Window</span>
                  </div>
                </div>
              </div>
            </div>
          }
        />

        {/* ================================================================= */}
        {/* FEATURE 2: Honest Real Streak Tracking (Split-Reverse 5/7)        */}
        {/* ================================================================= */}
        <FeatureSection
          rhythm="split-reverse"
          title="Streaks that mean something. Starting honestly at zero."
          description="Most trackers start your streak with fake participation points. HabbitAt streaks start strictly at zero and advance only when verified proof is submitted on time. Miss a deadline without proof, and the streak resets without excuses."
          leftContent={
            <div className="space-y-3 pt-2">
              <div className="flex items-center gap-3">
                <IconBadge icon={Flame} variant="accent" size="sm" />
                <span className="text-sm font-medium text-ink">
                  Strict zero-base rule — no artificial initial streak boosts
                </span>
              </div>
              <div className="flex items-center gap-3">
                <IconBadge icon={XCircle} variant="accent" size="sm" />
                <span className="text-sm font-medium text-ink">
                  Terracotta miss status — clear feedback with no shame-red alarms
                </span>
              </div>
            </div>
          }
          rightContent={
            <div className="bg-surface border border-border p-6 rounded-[4px] space-y-6">
              <div className="flex items-center justify-between border-b border-border pb-3">
                <span className="text-xs font-mono font-semibold text-ink-muted uppercase">
                  Streak Progress Tracker
                </span>
                <span className="text-xs font-mono font-semibold text-accent">
                  ACTIVE STREAK: 19 DAYS
                </span>
              </div>

              <div className="grid grid-cols-4 gap-3">
                <div className="border border-border p-3 rounded-[4px] bg-bg text-center space-y-1">
                  <span className="text-[10px] font-mono text-ink-muted block">DAY 0</span>
                  <span className="font-serif font-bold text-lg text-ink">Start</span>
                </div>
                <div className="border border-border p-3 rounded-[4px] bg-bg text-center space-y-1">
                  <span className="text-[10px] font-mono text-ink-muted block">DAY 7</span>
                  <span className="font-serif font-bold text-lg text-accent-2">Verified</span>
                </div>
                <div className="border border-accent p-3 rounded-[4px] bg-accent/10 text-center space-y-1">
                  <span className="text-[10px] font-mono text-accent font-semibold block">DAY 19</span>
                  <span className="font-serif font-bold text-xl text-accent">Current</span>
                </div>
                <div className="border border-border p-3 rounded-[4px] bg-bg text-center space-y-1 opacity-60">
                  <span className="text-[10px] font-mono text-ink-muted block">DAY 30</span>
                  <span className="font-serif font-bold text-lg text-ink">Goal</span>
                </div>
              </div>

              <div className="p-3 bg-bg border border-border rounded-[4px] text-xs text-ink-muted leading-relaxed">
                &quot;Day 19 survives. Photographic evidence verified at 06:42 AM.&quot;
              </div>
            </div>
          }
        />

        {/* ================================================================= */}
        {/* FEATURE 3: Frequency-Aware Scheduling (Triptych 4/4/4)            */}
        {/* ================================================================= */}
        <FeatureSection
          rhythm="triptych"
          title="Daily, Weekly, or Monthly. Scheduling that fits real life."
          description="Not all discipline fits a daily cadence. Natively configure daily check-ins, weekly commitments, and monthly milestones with intelligent due-today indicators."
        >
          {/* Card 1: Daily */}
          <div
            onClick={() => setActiveFrequency("daily")}
            className={`border p-6 rounded-[4px] space-y-4 cursor-pointer transition-colors ${
              activeFrequency === "daily"
                ? "bg-surface border-accent"
                : "bg-surface border-border hover:border-ink/30"
            }`}
          >
            <div className="flex items-center justify-between">
              <IconBadge icon={Clock} variant="accent" size="md" />
              <Chip variant={activeFrequency === "daily" ? "accent" : "default"}>
                Daily
              </Chip>
            </div>
            <h3 className="font-serif font-bold text-xl text-ink">Daily Habits</h3>
            <p className="text-xs text-ink-muted leading-relaxed">
              Everyday commitments like morning runs, reading, or meditation with exact-alarm reminder triggers.
            </p>
          </div>

          {/* Card 2: Weekly */}
          <div
            onClick={() => setActiveFrequency("weekly")}
            className={`border p-6 rounded-[4px] space-y-4 cursor-pointer transition-colors ${
              activeFrequency === "weekly"
                ? "bg-surface border-accent-2"
                : "bg-surface border-border hover:border-ink/30"
            }`}
          >
            <div className="flex items-center justify-between">
              <IconBadge icon={Calendar} variant="jade" size="md" />
              <Chip variant={activeFrequency === "weekly" ? "jade" : "default"}>
                Weekly
              </Chip>
            </div>
            <h3 className="font-serif font-bold text-xl text-ink">Weekly Targets</h3>
            <p className="text-xs text-ink-muted leading-relaxed">
              Targeted cadence commitments (e.g. 3x weekly gym sessions) with days-until-next-due tracking.
            </p>
          </div>

          {/* Card 3: Monthly */}
          <div
            onClick={() => setActiveFrequency("monthly")}
            className={`border p-6 rounded-[4px] space-y-4 cursor-pointer transition-colors ${
              activeFrequency === "monthly"
                ? "bg-surface border-accent-3"
                : "bg-surface border-border hover:border-ink/30"
            }`}
          >
            <div className="flex items-center justify-between">
              <IconBadge icon={ShieldCheck} variant="gold" size="md" />
              <Chip variant={activeFrequency === "monthly" ? "gold" : "default"}>
                Monthly
              </Chip>
            </div>
            <h3 className="font-serif font-bold text-xl text-ink">Monthly Milestones</h3>
            <p className="text-xs text-ink-muted leading-relaxed">
              Deep focus achievements like monthly book completions or long-range health reviews.
            </p>
          </div>
        </FeatureSection>

        {/* ================================================================= */}
        {/* FEATURE 4: Photographic Ledger & Calendar Breakdown (Split 7/5)   */}
        {/* ================================================================= */}
        <FeatureSection
          rhythm="split"
          title="A Photographic Record of Discipline."
          description="Your calendar isn't just a list of green checkmarks — it's a permanent photographic record of your effort. Tap any day tile in your monthly grid to expand the full submitted proof."
          leftContent={
            <div className="space-y-3 pt-2">
              <div className="flex items-center gap-3">
                <IconBadge icon={CheckCircle2} variant="jade" size="sm" />
                <span className="text-sm font-medium text-ink">
                  Completed days display proof photo thumbnail with saffron corner accent
                </span>
              </div>
              <div className="flex items-center gap-3">
                <IconBadge icon={XCircle} variant="accent" size="sm" />
                <span className="text-sm font-medium text-ink">
                  Missed days show terracotta hairline border with no alarmist red
                </span>
              </div>
            </div>
          }
          rightContent={
            <div className="bg-surface border border-border p-6 rounded-[4px] space-y-4">
              <div className="flex items-center justify-between border-b border-border pb-3">
                <span className="text-xs font-mono font-semibold text-ink-muted uppercase">
                  September Monthly Grid Ledger
                </span>
                <span className="text-xs font-mono text-accent-2">
                  19 PROOF TILES LOGGED
                </span>
              </div>

              {/* Monthly Calendar Tile Breakdown */}
              <div className="grid grid-cols-7 gap-2 pt-2">
                {[...Array(28)].map((_, idx) => {
                  const isCompleted = idx < 19;
                  const isMissed = idx === 19;
                  const isToday = idx === 20;

                  return (
                    <div
                      key={idx}
                      className={`h-9 rounded-[4px] border flex items-center justify-center relative text-[10px] font-mono ${
                        isCompleted
                          ? "bg-accent-2/15 border-accent-2 text-accent-2 font-bold"
                          : isMissed
                          ? "bg-accent/10 border-accent text-accent font-semibold"
                          : isToday
                          ? "bg-surface border-accent border-2 text-ink font-bold animate-pulse"
                          : "bg-bg border-border text-ink-muted"
                      }`}
                    >
                      {idx + 1}
                      {isCompleted && (
                        <span className="absolute top-0.5 right-0.5 w-1.5 h-1.5 bg-accent rounded-full" />
                      )}
                    </div>
                  );
                })}
              </div>

              <div className="flex items-center justify-between text-xs text-ink-muted pt-2 border-t border-border">
                <span className="flex items-center gap-1.5">
                  <span className="w-2.5 h-2.5 rounded-full bg-accent-2 inline-block" />
                  Verified Photo
                </span>
                <span className="flex items-center gap-1.5">
                  <span className="w-2.5 h-2.5 rounded-full bg-accent inline-block" />
                  Missed
                </span>
                <span className="flex items-center gap-1.5">
                  <span className="w-2.5 h-2.5 rounded-full border border-accent inline-block" />
                  Today
                </span>
              </div>
            </div>
          }
        />

        {/* ================================================================= */}
        {/* FEATURE 5: Home Screen View Switcher (Split-Reverse 5/7)          */}
        {/* ================================================================= */}
        <FeatureSection
          rhythm="split-reverse"
          title="List, Detailed, or Grid. Tailored to your workflow."
          description="Switch seamlessly between compact list views for quick daily check-ins, detailed card views with full proof context, or compact calendar grids for historical tracking."
          leftContent={
            <div className="space-y-4 pt-2">
              <div className="flex items-center gap-2">
                <Button
                  variant={activeViewMode === "list" ? "primary" : "outline"}
                  size="sm"
                  onClick={() => setActiveViewMode("list")}
                >
                  <LayoutList size={14} />
                  <span>List View</span>
                </Button>
                <Button
                  variant={activeViewMode === "detailed" ? "primary" : "outline"}
                  size="sm"
                  onClick={() => setActiveViewMode("detailed")}
                >
                  <LayoutGrid size={14} />
                  <span>Detailed</span>
                </Button>
                <Button
                  variant={activeViewMode === "grid" ? "primary" : "outline"}
                  size="sm"
                  onClick={() => setActiveViewMode("grid")}
                >
                  <Calendar size={14} />
                  <span>Grid</span>
                </Button>
              </div>
            </div>
          }
          rightContent={
            <div className="bg-surface border border-border p-6 rounded-[4px] space-y-4">
              <div className="flex items-center justify-between border-b border-border pb-3">
                <span className="text-xs font-mono font-semibold text-ink-muted uppercase">
                  App View Switcher Callback
                </span>
                <span className="text-xs font-mono text-ink capitalize">
                  Mode: {activeViewMode}
                </span>
              </div>

              {activeViewMode === "list" && (
                <div className="space-y-2">
                  <div className="border border-border p-3 rounded-[4px] bg-bg flex items-center justify-between">
                    <span className="text-sm font-semibold text-ink">Morning Run</span>
                    <span className="text-xs font-mono text-accent-2 font-semibold">✓ Verified</span>
                  </div>
                  <div className="border border-border p-3 rounded-[4px] bg-bg flex items-center justify-between">
                    <span className="text-sm font-semibold text-ink">Read 30 Pages</span>
                    <span className="text-xs font-mono text-accent font-semibold inline-flex items-center gap-1">
                      <Flame size={13} className="text-accent" /> Due 9:00 PM
                    </span>
                  </div>
                </div>
              )}

              {activeViewMode === "detailed" && (
                <div className="border border-border p-4 rounded-[4px] bg-bg space-y-3">
                  <div className="flex items-center justify-between">
                    <h4 className="font-serif font-bold text-base text-ink">Morning 5km Run</h4>
                    <Chip variant="accent" icon={<Flame size={13} />}>19 Day Streak</Chip>
                  </div>
                  <p className="text-xs text-ink-muted">
                    Photo proof submitted at 06:30 AM &bull; AI Verification Confidence 98.4%
                  </p>
                  <div className="h-16 bg-surface border border-border rounded-[4px] flex items-center justify-center text-xs font-mono text-ink-muted">
                    [ Detailed Photo Proof Context Thumbnail ]
                  </div>
                </div>
              )}

              {activeViewMode === "grid" && (
                <div className="grid grid-cols-4 gap-2">
                  {[...Array(8)].map((_, i) => (
                    <div key={i} className="h-12 bg-bg border border-border rounded-[4px] flex items-center justify-center text-xs font-mono font-bold text-ink">
                      Day {i + 1}
                    </div>
                  ))}
                </div>
              )}
            </div>
          }
        />

        {/* ================================================================= */}
        {/* HOW IT WORKS SEQUENCE                                            */}
        {/* ================================================================= */}
        <section className="w-full py-16 md:py-24 border-b border-border bg-bg">
          <Container className="space-y-12">
            <div className="max-w-2xl">
              <h2 className="font-serif text-3xl md:text-4xl lg:text-5xl font-bold tracking-tight text-ink">
                How HabbitAt Enforces Discipline
              </h2>
              <p className="text-ink-muted text-base md:text-lg mt-3 leading-relaxed">
                A transparent 4-step execution loop designed for real behavioral consistency.
              </p>
            </div>
            <FeatureGrid />
          </Container>
        </section>

        {/* ================================================================= */}
        {/* CLOSING CTA BAND (Full-Bleed)                                     */}
        {/* ================================================================= */}
        <FeatureSection
          rhythm="full-bleed"
          title="Ready to build discipline that's actually verified?"
          description="Download HabbitAt directly via ParamStore — our sideload APK distribution channel."
        >
          <div className="flex flex-wrap items-center gap-4 pt-6">
            <Button href="/paramstore" variant="primary" size="lg">
              <ArrowDownToLine size={18} />
              <span>Get HabbitAt APK</span>
            </Button>
            <Button href="/paramstore" variant="outline" size="lg">
              <span>Explore ParamStore</span>
              <ArrowRight size={16} />
            </Button>
          </div>
        </FeatureSection>
      </main>

      <SiteFooter />
    </div>
  );
}
