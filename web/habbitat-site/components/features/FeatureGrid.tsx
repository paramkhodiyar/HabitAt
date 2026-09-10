"use client";

import React from "react";
import { motion, Variants } from "framer-motion";
import { PlusCircle, Clock, Camera, Sparkles } from "lucide-react";
import IconBadge from "@/components/ui/IconBadge";

const STEPS = [
  {
    step: "01",
    icon: PlusCircle,
    title: "Create Habit",
    description:
      "Define your habit title, specific proof requirements, and commitment target.",
    variant: "default" as const,
  },
  {
    step: "02",
    icon: Clock,
    title: "Set Frequency",
    description:
      "Configure daily, weekly, or monthly schedules with exact reminder alarms.",
    variant: "jade" as const,
  },
  {
    step: "03",
    icon: Camera,
    title: "Submit Photo Proof",
    description:
      "Capture and submit authentic photo evidence before your deadline expires.",
    variant: "accent" as const,
  },
  {
    step: "04",
    icon: Sparkles,
    title: "AI Verification & Streak",
    description:
      "Hosted vision AI verifies proof context, unlocking your verified streak milestone.",
    variant: "gold" as const,
  },
];

export default function FeatureGrid() {
  const containerVariants: Variants = {
    hidden: {},
    visible: {
      transition: {
        staggerChildren: 0.15,
      },
    },
  };

  const itemVariants: Variants = {
    hidden: { opacity: 0, y: 20 },
    visible: {
      opacity: 1,
      y: 0,
      transition: { duration: 0.4, ease: "easeOut" },
    },
  };

  return (
    <motion.div
      initial="hidden"
      whileInView="visible"
      viewport={{ once: true, margin: "-60px" }}
      variants={containerVariants}
      className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6 relative"
    >
      {STEPS.map((item) => (
        <motion.div
          key={item.step}
          variants={itemVariants}
          className="bg-surface border border-border p-6 rounded-[4px] flex flex-col justify-between space-y-4 hover:border-ink/30 transition-colors"
        >
          <div className="space-y-4">
            <div className="flex items-center justify-between">
              <IconBadge icon={item.icon} variant={item.variant} size="md" />
              <span className="font-mono text-xs font-semibold text-ink-muted">
                STEP {item.step}
              </span>
            </div>
            <h3 className="font-serif font-bold text-xl text-ink leading-tight">
              {item.title}
            </h3>
            <p className="text-sm text-ink-muted leading-relaxed">
              {item.description}
            </p>
          </div>

          <div className="pt-2 border-t border-border/60 text-[11px] font-mono text-ink-muted">
            Phase Step &bull; {item.step} of 04
          </div>
        </motion.div>
      ))}
    </motion.div>
  );
}
