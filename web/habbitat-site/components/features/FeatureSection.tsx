"use client";

import React from "react";
import { motion, Variants } from "framer-motion";
import Container, { GridSplit, GridTriptych, GridFullBleed } from "@/components/layout/Container";

interface FeatureSectionProps {
  id?: string;
  rhythm?: "split" | "split-reverse" | "triptych" | "full-bleed";
  title: string;
  description: string;
  leftContent?: React.ReactNode;
  rightContent?: React.ReactNode;
  children?: React.ReactNode;
  className?: string;
}

export default function FeatureSection({
  id,
  rhythm = "split",
  title,
  description,
  leftContent,
  rightContent,
  children,
  className = "",
}: FeatureSectionProps) {
  // Motion animation variants for scroll reveal
  const sectionVariants: Variants = {
    hidden: { opacity: 0, y: 24 },
    visible: {
      opacity: 1,
      y: 0,
      transition: { duration: 0.5, ease: "easeOut" },
    },
  };

  if (rhythm === "full-bleed") {
    return (
      <section id={id} className={`w-full py-16 md:py-24 ${className}`}>
        <GridFullBleed>
          <motion.div
            initial="hidden"
            whileInView="visible"
            viewport={{ once: true, margin: "-100px" }}
            variants={sectionVariants}
            className="space-y-6"
          >
            <div className="max-w-2xl">
              <h2 className="font-serif text-3xl md:text-4xl lg:text-5xl font-bold tracking-tight text-ink leading-tight">
                {title}
              </h2>
              <p className="text-ink-muted text-base md:text-lg mt-3 leading-relaxed">
                {description}
              </p>
            </div>
            {children}
          </motion.div>
        </GridFullBleed>
      </section>
    );
  }

  return (
    <section id={id} className={`w-full py-16 md:py-24 border-b border-border ${className}`}>
      <Container>
        <motion.div
          initial="hidden"
          whileInView="visible"
          viewport={{ once: true, margin: "-80px" }}
          variants={sectionVariants}
        >
          {rhythm === "triptych" ? (
            <div className="space-y-12">
              <div className="max-w-2xl">
                <h2 className="font-serif text-3xl md:text-4xl font-bold tracking-tight text-ink">
                  {title}
                </h2>
                <p className="text-ink-muted text-base md:text-lg mt-3 leading-relaxed">
                  {description}
                </p>
              </div>
              <GridTriptych>{children}</GridTriptych>
            </div>
          ) : (
            <GridSplit
              reverseOnDesktop={rhythm === "split-reverse"}
              left={
                <div className="space-y-4">
                  <h2 className="font-serif text-3xl md:text-4xl font-bold tracking-tight text-ink leading-tight">
                    {title}
                  </h2>
                  <p className="text-ink-muted text-base md:text-lg leading-relaxed">
                    {description}
                  </p>
                  {leftContent}
                </div>
              }
              right={rightContent || children}
            />
          )}
        </motion.div>
      </Container>
    </section>
  );
}
