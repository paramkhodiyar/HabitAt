import React from "react";
import Container from "./Container";

interface GridSplitProps {
  left: React.ReactNode;
  right: React.ReactNode;
  reverseOnDesktop?: boolean;
  className?: string;
}

export function GridSplit({
  left,
  right,
  reverseOnDesktop = false,
  className = "",
}: GridSplitProps) {
  return (
    <div
      className={`grid grid-cols-1 md:grid-cols-12 gap-8 lg:gap-12 items-center ${className}`}
    >
      <div
        className={`md:col-span-7 ${
          reverseOnDesktop ? "md:order-2" : "md:order-1"
        }`}
      >
        {left}
      </div>
      <div
        className={`md:col-span-5 ${
          reverseOnDesktop ? "md:order-1" : "md:order-2"
        }`}
      >
        {right}
      </div>
    </div>
  );
}

interface GridTriptychProps {
  children: React.ReactNode;
  className?: string;
}

export function GridTriptych({ children, className = "" }: GridTriptychProps) {
  return (
    <div
      className={`grid grid-cols-1 md:grid-cols-3 gap-6 lg:gap-8 ${className}`}
    >
      {children}
    </div>
  );
}

interface GridFullBleedProps {
  children: React.ReactNode;
  className?: string;
  variant?: "surface" | "bg";
}

export function GridFullBleed({
  children,
  className = "",
  variant = "surface",
}: GridFullBleedProps) {
  const bgClass = variant === "surface" ? "bg-surface" : "bg-bg";
  return (
    <section className={`w-full border-y border-border ${bgClass} py-16 md:py-24 ${className}`}>
      <Container>{children}</Container>
    </section>
  );
}
