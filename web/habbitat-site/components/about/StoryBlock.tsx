import React from "react";
import { FileText, Sparkles } from "lucide-react";

export default function StoryBlock() {
  return (
    <div className="bg-surface border border-border p-6 md:p-10 rounded-[4px] space-y-6">
      <div className="flex items-center justify-between border-b border-border pb-4">
        <div className="flex items-center gap-3">
          <div className="w-8 h-8 rounded-[4px] bg-accent/10 border border-accent/20 flex items-center justify-center text-accent">
            <FileText size={16} />
          </div>
          <h2 className="font-serif text-2xl font-bold text-ink">
            Developer Story & Vision
          </h2>
        </div>
        <span className="text-[11px] font-mono text-ink-muted uppercase border border-border bg-bg px-2.5 py-1 rounded-[2px]">
          Editorial Section
        </span>
      </div>

      {/* Allowed Placeholder Box */}
      <div className="border-2 border-dashed border-border bg-bg p-8 md:p-12 rounded-[4px] text-center space-y-4">
        <div className="w-12 h-12 rounded-[4px] bg-accent-2/10 border border-accent-2/20 mx-auto flex items-center justify-center text-accent-2">
          <Sparkles size={20} />
        </div>
        <div className="space-y-2 max-w-md mx-auto">
          <p className="font-mono text-xs font-semibold text-accent uppercase tracking-wider">
            TODO: story copy pending from Param
          </p>
          <p className="text-xs text-ink-muted leading-relaxed">
            This space is reserved for Param Khodiyar&apos;s personal narrative on building HabbitAt, the philosophy behind AI photo verification, and the journey of crafting a privacy-first habit system.
          </p>
        </div>
      </div>
    </div>
  );
}
