import React from "react";
import { Sparkles, Terminal } from "lucide-react";

export default function StoryBlock() {
  return (
    <div className="bg-surface border border-border p-6 md:p-10 rounded-[4px] space-y-8">
      {/* Editorial Header */}
      <div className="flex flex-col sm:flex-row sm:items-center justify-between border-b border-border pb-5 gap-3">
        <div className="flex items-center gap-3">
          <div className="w-8 h-8 rounded-[4px] bg-accent/10 border border-accent/20 flex items-center justify-center text-accent shrink-0">
            <Terminal size={16} />
          </div>
          <div>
            <h2 className="font-serif text-2xl font-bold text-ink">
              The Origin Story
            </h2>
            <p className="text-xs text-ink-muted">
              Why HabbitAt was created &amp; how a lecture room hack became a production app.
            </p>
          </div>
        </div>
        <span className="text-[11px] font-mono text-ink-muted uppercase border border-border bg-bg px-2.5 py-1 rounded-[2px] self-start sm:self-auto">
          Developer Essay
        </span>
      </div>

      {/* Opening Sanskrit / Hindi Invocation */}
      <div className="text-center py-4 border-y border-border/60 bg-bg/50 rounded-[4px] space-y-1">
        <span className="font-serif text-xl sm:text-2xl font-bold text-accent tracking-widest block">
          ॥ श्री राम ॥
        </span>
        <span className="font-serif text-xs text-ink-muted italic block">
          ॥ कर्मण्येवाधिकारस्ते मा फलेषु कदाचन ॥
        </span>
        <span className="text-[10px] font-mono text-ink-muted uppercase block tracking-wider pt-1">
          (Focus strictly on action &amp; discipline — unearned credit belongs nowhere)
        </span>
      </div>

      {/* Story Narrative Prose */}
      <div className="space-y-6 text-sm text-ink leading-relaxed">
        <p>
          I&apos;ve always preferred staying away from the crowd and meaningless social media noise. I never liked Snapchat or the idea of sending 3-second black screen photos back and forth just to keep a meaningless flame icon alive. But the raw psychological mechanics of <strong className="text-accent font-semibold">streaks</strong>? That part actually fascinated me.
        </p>

        <p>
          My streak addiction started on GitHub — watching green contribution tiles light up day after day. There was something undeniably satisfying about honest, visible proof of work. I wanted that exact same unyielding discipline applied to my personal life: waking up on time, reading, working out, and building habits that actually stick.
        </p>

        <p>
          The problem? Standard habit trackers rely on self-reported checkmarks — which are far too easy to tap when snooze-button temptation strikes. So, sitting in the middle of a university lecture with VS Code open under my class notes, I decided to build my own solution. One week of intense Kotlin, vision AI, and Android architecture hacking later, the first working build of <strong className="font-semibold text-ink">HabbitAt</strong> was deployed.
        </p>

        <div className="p-4 bg-bg border border-border rounded-[4px] text-xs text-ink-muted space-y-2">
          <div className="flex items-center gap-2 font-mono font-semibold text-accent uppercase text-[11px]">
            <Sparkles size={14} /> The HabbitAt Rulebook
          </div>
          <p className="leading-relaxed">
            No artificial streak boosts. No fake praise. Just timestamped photo proof evaluated by vision AI before your streak advances. Starting strictly at zero.
          </p>
        </div>
      </div>

      {/* Closing Hindi / Sanskrit Signature */}
      <div className="pt-6 border-t border-border flex flex-col sm:flex-row items-center justify-between gap-4 text-xs">
        <div className="font-serif text-sm font-semibold text-accent tracking-wider">
          ॥ सिद्धिर्भवतु कर्मसु ॥
        </div>
        <span className="font-mono text-ink-muted text-[11px]">
          Written &amp; Shipped by Param Khodiyar
        </span>
      </div>
    </div>
  );
}
