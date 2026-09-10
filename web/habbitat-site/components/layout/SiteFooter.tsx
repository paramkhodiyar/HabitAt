import React from "react";
import Link from "next/link";
import Image from "next/image";
import { Mail, ArrowUpRight } from "lucide-react";
import Container from "./Container";
import { SITE_CONFIG } from "@/lib/constants";

function GithubIcon({ size = 16, className = "" }: { size?: number; className?: string }) {
  return (
    <svg
      width={size}
      height={size}
      viewBox="0 0 24 24"
      fill="none"
      stroke="currentColor"
      strokeWidth="2"
      strokeLinecap="round"
      strokeLinejoin="round"
      className={className}
    >
      <path d="M15 22v-4a4.8 4.8 0 0 0-1-3.5c3 0 6-2 6-5.5.08-1.25-.27-2.48-1-3.5.28-1.15.28-2.35 0-3.5 0 0-1 0-3 1.5-2.64-.5-5.36-.5-8 0C6 2 5 2 5 2c-.3 1.15-.3 2.35 0 3.5A5.403 5.403 0 0 0 4 9c0 3.5 3 5.5 6 5.5-.39.49-.68 1.05-.85 1.65-.17.6-.22 1.23-.15 1.85v4" />
      <path d="M9 18c-4.51 2-5-2-7-2" />
    </svg>
  );
}

function LinkedinIcon({ size = 16, className = "" }: { size?: number; className?: string }) {
  return (
    <svg
      width={size}
      height={size}
      viewBox="0 0 24 24"
      fill="none"
      stroke="currentColor"
      strokeWidth="2"
      strokeLinecap="round"
      strokeLinejoin="round"
      className={className}
    >
      <path d="M16 8a6 6 0 0 1 6 6v7h-4v-7a2 2 0 0 0-2-2 2 2 0 0 0-2 2v7h-4v-7a6 6 0 0 1 6-6z" />
      <rect width="4" height="12" x="2" y="9" />
      <circle cx="4" cy="4" r="2" />
    </svg>
  );
}

export default function SiteFooter() {
  return (
    <footer className="w-full bg-surface border-t border-border py-12 md:py-16 text-ink">
      <Container>
        <div className="grid grid-cols-1 md:grid-cols-12 gap-8 lg:gap-12 pb-12 border-b border-border">
          {/* Brand Col */}
          <div className="md:col-span-5 flex flex-col gap-4">
            <Link href="/" className="flex items-center gap-3 w-fit">
              <div className="w-10 h-10 relative flex items-center justify-center overflow-hidden shrink-0">
                <Image
                  src="/images/icons/HabitAt_Icon_Simplified.svg"
                  alt="habitAt logo icon"
                  width={40}
                  height={40}
                  className="object-contain w-full h-full"
                />
              </div>
              <span className="font-serif font-bold text-xl text-ink">
                {SITE_CONFIG.name}
              </span>
            </Link>
            <p className="text-sm text-ink-muted leading-relaxed max-w-sm">
              {SITE_CONFIG.description}
            </p>
            <div className="text-xs text-ink-muted font-mono mt-1">
              Version {SITE_CONFIG.apk.version} &bull; Sideload APK Edition
            </div>
          </div>

          {/* Quick Links */}
          <div className="md:col-span-3 flex flex-col gap-3">
            <h4 className="text-xs font-semibold text-ink-muted uppercase tracking-wider">
              Navigation
            </h4>
            <ul className="flex flex-col gap-2 text-sm">
              <li>
                <Link href="/" className="text-ink hover:text-accent transition-colors">
                  Overview / Landing
                </Link>
              </li>
              <li>
                <Link
                  href="/paramstore"
                  className="text-ink hover:text-accent transition-colors"
                >
                  ParamStore (APK Download)
                </Link>
              </li>
              <li>
                <Link
                  href="/about"
                  className="text-ink hover:text-accent transition-colors"
                >
                  About the Developer
                </Link>
              </li>
            </ul>
          </div>

          {/* Connect & Contact */}
          <div className="md:col-span-4 flex flex-col gap-3">
            <h4 className="text-xs font-semibold text-ink-muted uppercase tracking-wider">
              Contact & Connect
            </h4>
            <ul className="flex flex-col gap-2.5 text-sm">
              <li>
                <a
                  href={`mailto:${SITE_CONFIG.contactEmail}`}
                  className="inline-flex items-center gap-2 text-ink hover:text-accent transition-colors"
                >
                  <Mail size={16} className="text-accent" />
                  <span>{SITE_CONFIG.contactEmail}</span>
                </a>
              </li>
              <li>
                <a
                  href={SITE_CONFIG.links.github}
                  target="_blank"
                  rel="noopener noreferrer"
                  className="inline-flex items-center gap-2 text-ink hover:text-accent transition-colors"
                >
                  <GithubIcon size={16} />
                  <span>GitHub (@paramkhodiyar)</span>
                  <ArrowUpRight size={14} className="text-ink-muted" />
                </a>
              </li>
              <li>
                <a
                  href={SITE_CONFIG.links.linkedin}
                  target="_blank"
                  rel="noopener noreferrer"
                  className="inline-flex items-center gap-2 text-ink hover:text-accent transition-colors"
                >
                  <LinkedinIcon size={16} />
                  <span>LinkedIn (in/paramkhodiyar)</span>
                  <ArrowUpRight size={14} className="text-ink-muted" />
                </a>
              </li>
              <li>
                <a
                  href={SITE_CONFIG.links.website}
                  target="_blank"
                  rel="noopener noreferrer"
                  className="inline-flex items-center gap-2 text-ink hover:text-accent transition-colors"
                >
                  <Mail size={16} className="text-accent" />
                  <span>paramkhodiyar.dev</span>
                  <ArrowUpRight size={14} className="text-ink-muted" />
                </a>
              </li>
            </ul>
          </div>
        </div>

        {/* Bottom Bar */}
        <div className="pt-8 flex flex-col sm:flex-row items-center justify-between gap-4 text-xs text-ink-muted">
          <div>
            &copy; {new Date().getFullYear()} {SITE_CONFIG.developer}. All rights reserved.
          </div>
          <div className="flex items-center gap-4 font-mono">
            <span>Made with discipline & consistency</span>
            <span>&bull;</span>
            <span>Param Khodiyar</span>
          </div>
        </div>
      </Container>
    </footer>
  );
}
