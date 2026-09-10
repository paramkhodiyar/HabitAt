"use client";

import React, { useState } from "react";
import Link from "next/link";
import { usePathname } from "next/navigation";
import Image from "next/image";
import { Menu, X, ArrowDownToLine } from "lucide-react";
import Container from "./Container";
import Button from "@/components/ui/Button";

const NAV_ITEMS = [
  { label: "Overview", href: "/" },
  { label: "ParamStore", href: "/paramstore" },
  { label: "About", href: "/about" },
];

export default function SiteHeader() {
  const pathname = usePathname();
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);

  return (
    <header className="sticky top-0 z-50 w-full bg-bg/95 backdrop-blur-md border-b border-border">
      <Container className="flex items-center justify-between h-16 md:h-20">
        {/* Brand Logo */}
        <Link
          href="/"
          className="flex items-center gap-3 text-ink focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-accent rounded-sm"
        >
          <div className="w-10 h-10 md:w-12 md:h-12 relative flex items-center justify-center overflow-hidden shrink-0">
            <Image
              src="/images/icons/HabitAt_Icon_Simplified.svg"
              alt="HabbitAt logo icon"
              width={48}
              height={48}
              className="object-contain w-full h-full"
            />
          </div>
          <div className="flex flex-col">
            <span className="font-serif font-bold text-lg md:text-xl leading-none text-ink tracking-tight">
              HabbitAt
            </span>
            <span className="text-[10px] text-ink-muted uppercase tracking-wider font-semibold leading-none mt-0.5">
              AI Habit Enforcer
            </span>
          </div>
        </Link>

        {/* Desktop Navigation */}
        <nav className="hidden md:flex items-center gap-8" aria-label="Main Navigation">
          {NAV_ITEMS.map((item) => {
            const isActive = pathname === item.href;
            return (
              <Link
                key={item.href}
                href={item.href}
                className={`text-sm font-medium transition-colors py-1 relative ${
                  isActive
                    ? "text-accent font-semibold"
                    : "text-ink-muted hover:text-ink"
                }`}
              >
                {item.label}
                {isActive && (
                  <span className="absolute bottom-0 left-0 right-0 h-0.5 bg-accent rounded-full" />
                )}
              </Link>
            );
          })}
        </nav>

        {/* Desktop CTA */}
        <div className="hidden md:flex items-center gap-3">
          <Button href="/paramstore" variant="primary" size="sm">
            <ArrowDownToLine size={16} />
            <span>Get App</span>
          </Button>
        </div>

        {/* Mobile Menu Button */}
        <button
          type="button"
          className="md:hidden p-2 text-ink hover:bg-surface border border-border rounded-[4px] focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-accent"
          onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
          aria-expanded={mobileMenuOpen}
          aria-label="Toggle navigation menu"
        >
          {mobileMenuOpen ? <X size={20} /> : <Menu size={20} />}
        </button>
      </Container>

      {/* Mobile Navigation Panel */}
      {mobileMenuOpen && (
        <div className="md:hidden w-full bg-surface border-b border-border py-4 px-4 sm:px-6 animate-in slide-in-from-top-2 duration-200">
          <nav className="flex flex-col gap-3">
            {NAV_ITEMS.map((item) => {
              const isActive = pathname === item.href;
              return (
                <Link
                  key={item.href}
                  href={item.href}
                  onClick={() => setMobileMenuOpen(false)}
                  className={`px-3 py-2 text-base font-medium rounded-[4px] transition-colors ${
                    isActive
                      ? "bg-accent/10 text-accent font-semibold border border-accent/20"
                      : "text-ink hover:bg-bg"
                  }`}
                >
                  {item.label}
                </Link>
              );
            })}
            <div className="pt-2 border-t border-border mt-1">
              <Button
                href="/paramstore"
                variant="primary"
                size="md"
                className="w-full"
                onClick={() => setMobileMenuOpen(false)}
              >
                <ArrowDownToLine size={18} />
                <span>Get HabbitAt APK</span>
              </Button>
            </div>
          </nav>
        </div>
      )}
    </header>
  );
}
