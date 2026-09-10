import React from "react";
import { Mail, GraduationCap, Code2 } from "lucide-react";
import Button from "@/components/ui/Button";

export default function ProfileCard() {
  return (
    <div className="bg-surface border border-border p-6 md:p-10 rounded-[4px] space-y-6">
      <div className="flex flex-col sm:flex-row items-start sm:items-center gap-6">
        {/* Profile Avatar / Photo Frame */}
        <div className="w-24 h-24 sm:w-28 sm:h-28 bg-bg border border-border rounded-[4px] p-2 shrink-0 flex items-center justify-center relative overflow-hidden">
          <div className="w-full h-full bg-accent/10 border border-accent/20 rounded-[2px] flex items-center justify-center font-serif text-3xl font-bold text-accent">
            PK
          </div>
        </div>

        {/* Info & Bio */}
        <div className="space-y-3 flex-1">
          <div>
            <h1 className="font-serif text-3xl md:text-4xl font-bold tracking-tight text-ink">
              Param Khodiyar
            </h1>
            <p className="text-base text-accent font-medium mt-1">
              Creator & Lead Engineer, HabbitAt
            </p>
          </div>

          <div className="flex flex-wrap items-center gap-y-2 gap-x-4 text-xs text-ink-muted">
            <span className="flex items-center gap-1.5">
              <GraduationCap size={14} className="text-ink" />
              CS & Data Science Undergraduate
            </span>
            <span className="flex items-center gap-1.5">
              <Code2 size={14} className="text-ink" />
              Android & macOS Developer
            </span>
          </div>

          <p className="text-sm text-ink-muted leading-relaxed">
            Building HabbitAt as an AI-verified habit enforcement app to solve real commitment problems with photo proof, local ML, and cross-device sync.
          </p>
        </div>
      </div>

      {/* Action / Contact Line */}
      <div className="pt-6 border-t border-border flex flex-col sm:flex-row sm:items-center justify-between gap-4">
        <div className="flex items-center gap-2 text-xs font-mono text-ink-muted">
          <Mail size={14} className="text-accent" />
          <span>Direct Contact:</span>
          <a
            href="mailto:paramkhodiyar1008@gmail.com"
            className="text-ink font-semibold hover:text-accent underline underline-offset-4 transition-colors"
          >
            paramkhodiyar1008@gmail.com
          </a>
        </div>

        <Button
          variant="secondary"
          size="sm"
          href="mailto:paramkhodiyar1008@gmail.com"
        >
          <Mail size={14} />
          Send Email
        </Button>
      </div>
    </div>
  );
}
