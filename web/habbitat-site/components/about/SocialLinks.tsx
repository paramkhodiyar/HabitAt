import React from "react";
import { ExternalLink } from "lucide-react";

function GithubIcon({ className = "w-5 h-5" }: { className?: string }) {
  return (
    <svg className={className} viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
      <path d="M15 22v-4a4.8 4.8 0 0 0-1-3.5c3 0 6-2 6-5.5.08-1.25-.27-2.48-1-3.5.28-1.15.28-2.35 0-3.5 0 0-1 0-3 1.5-2.64-.5-5.36-.5-8 0C6 2 5 2 5 2c-.3 1.15-.3 2.35 0 3.5A5.403 5.403 0 0 0 4 9c0 3.5 3 5.5 6 5.5-.39.49-.68 1.05-.85 1.65-.17.6-.22 1.23-.15 1.85v4" />
      <path d="M9 18c-4.51 2-5-2-7-2" />
    </svg>
  );
}

function LinkedinIcon({ className = "w-5 h-5" }: { className?: string }) {
  return (
    <svg className={className} viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
      <path d="M16 8a6 6 0 0 1 6 6v7h-4v-7a2 2 0 0 0-2-2 2 2 0 0 0-2 2v7h-4v-7a6 6 0 0 1 6-6z" />
      <rect width="4" height="12" x="2" y="9" />
      <circle cx="4" cy="4" r="2" />
    </svg>
  );
}

export default function SocialLinks() {
  const links = [
    {
      name: "GitHub",
      description: "Code repositories & open source projects",
      icon: GithubIcon,
      href: "#",
      note: "URL pending from Param",
    },
    {
      name: "LinkedIn",
      description: "Professional background & experience",
      icon: LinkedinIcon,
      href: "#",
      note: "URL pending from Param",
    },
  ];

  return (
    <div className="bg-surface border border-border p-6 md:p-10 rounded-[4px] space-y-6">
      <div>
        <h2 className="font-serif text-2xl font-bold text-ink">
          Connect & Links
        </h2>
        <p className="text-xs text-ink-muted mt-1">
          Explore developer profiles and source code repositories.
        </p>
      </div>

      <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
        {links.map((link) => {
          const Icon = link.icon;
          return (
            <a
              key={link.name}
              href={link.href}
              className="group border border-border bg-bg p-5 rounded-[4px] hover:border-accent transition-colors flex flex-col justify-between gap-4"
            >
              <div className="flex items-start justify-between gap-3">
                <div className="w-10 h-10 rounded-[4px] bg-surface border border-border flex items-center justify-center text-ink group-hover:text-accent transition-colors">
                  <Icon className="w-5 h-5" />
                </div>
                <ExternalLink size={14} className="text-ink-muted group-hover:text-accent transition-colors" />
              </div>

              <div>
                <div className="flex items-center justify-between">
                  <span className="text-base font-semibold text-ink group-hover:text-accent transition-colors">
                    {link.name}
                  </span>
                  <span className="text-[10px] font-mono text-ink-muted">
                    {link.note}
                  </span>
                </div>
                <p className="text-xs text-ink-muted mt-1">
                  {link.description}
                </p>
              </div>
            </a>
          );
        })}
      </div>
    </div>
  );
}
