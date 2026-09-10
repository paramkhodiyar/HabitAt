"use client";

import React from "react";
import Image from "next/image";
import { motion } from "framer-motion";
import { ArrowDownToLine, ArrowRight, ShieldCheck, Camera, Flame } from "lucide-react";
import Container from "@/components/layout/Container";
import Button from "@/components/ui/Button";

export default function HeroMobile() {
  return (
    <section className="w-full py-10 px-4 sm:px-6 bg-bg border-b border-border space-y-8 overflow-hidden">
      <Container className="space-y-8">
        {/* Copy Stack */}
        <div className="space-y-4 text-left">
          <h1 className="font-serif text-3xl sm:text-4xl font-bold tracking-tight text-ink leading-[1.15]">
            Habits verified by proof, not promises.
          </h1>
          <p className="text-sm text-ink-muted leading-relaxed font-normal">
            HabbitAt enforces your habits with timestamped photo proof and vision AI verification. No unearned checkmarks.
          </p>

          {/* CTA Buttons */}
          <div className="flex flex-col sm:flex-row items-stretch sm:items-center gap-3 pt-2">
            <Button href="/paramstore" variant="primary" size="md" className="min-h-[44px]">
              <ArrowDownToLine size={18} />
              <span>Get HabbitAt APK</span>
            </Button>
            <Button href="#features" variant="outline" size="md" className="min-h-[44px]">
              <span>Explore Mechanics</span>
              <ArrowRight size={16} />
            </Button>
          </div>
        </div>

        {/* Mobile Device Visual Showcase */}
        <motion.div
          initial={{ opacity: 0, y: 15 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.5, ease: "easeOut" }}
          className="bg-surface border border-border p-5 rounded-[6px] space-y-4"
        >
          {/* App Header Bar */}
          <div className="flex items-center justify-between border-b border-border pb-3">
            <div className="flex items-center gap-2.5">
              <div className="w-7 h-7 bg-bg border border-border rounded-[4px] p-1 flex items-center justify-center">
                <Image
                  src="/images/icons/HabitAt_Icon_Simplified.svg"
                  alt="App Icon"
                  width={24}
                  height={24}
                  className="object-contain w-full h-full"
                />
              </div>
              <span className="font-serif font-bold text-sm text-ink">
                HabbitAt Mobile
              </span>
            </div>
            <span className="inline-flex items-center gap-1 text-[11px] font-mono font-semibold text-accent-2 bg-accent-2/10 px-2 py-0.5 rounded-[2px] border border-accent-2/20">
              <ShieldCheck size={12} /> AI Active
            </span>
          </div>

          {/* Habit Item Card */}
          <div className="bg-bg border border-border p-4 rounded-[4px] space-y-3">
            <div className="flex items-center justify-between">
              <div>
                <span className="text-[10px] font-mono text-ink-muted uppercase block">
                  ACTIVE HABIT
                </span>
                <span className="font-semibold text-sm text-ink">
                  Morning Run 5km
                </span>
              </div>
              <span className="inline-flex items-center gap-1 text-xs font-semibold text-accent border border-accent/20 bg-accent/10 px-2 py-1 rounded-[2px]">
                <Flame size={12} /> 19 Days
              </span>
            </div>

            {/* Photo Capture Frame */}
            <div className="h-28 bg-surface border border-dashed border-border rounded-[4px] flex flex-col items-center justify-center p-3 text-center space-y-1">
              <Camera size={20} className="text-accent" />
              <span className="text-[11px] font-mono text-ink font-semibold">
                Photo Proof Verification
              </span>
              <span className="text-[10px] text-ink-muted">
                Camera capture timestamped &amp; verified locally
              </span>
            </div>
          </div>
        </motion.div>
      </Container>
    </section>
  );
}
