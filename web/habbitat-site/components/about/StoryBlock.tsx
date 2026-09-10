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
          I&apos;ve always preferred staying away from the crowd and mindless social media noise. I never liked Snapchat or the idea of sending 3-second black screen photos back and forth just to keep a meaningless flame icon alive. Sending blank pixels back and forth for vanity numbers felt hollow. But the raw psychological mechanics of <strong className="text-accent font-semibold">streaks</strong>? That part actually fascinated me.
        </p>

        <p>
          My streak addiction started on GitHub watching green contribution tiles light up day after day. There was something undeniably satisfying about honest, visible proof of work: code written, bugs fixed, commits pushed. I wanted that exact same unyielding discipline applied to my personal life: waking up on time, reading technical books, working out, and building daily habits that actually stick long-term.
        </p>

        <p>
          The problem was simple: standard habit trackers rely on self-reported checkmarks — which are far too easy to tap when snooze-button temptation strikes. If you can cheat your own tracker without consequences, the streak is meaningless.
        </p>

        <p>
          So, sitting in the middle of a college lecture with <strong className="font-semibold text-ink">Antigravity IDE &amp; Gemini</strong> open right under my class slides, I decided to build my own solution. I wanted a system that demands real proof: timestamped camera capture, vision AI model evaluation, zero-based streak resets, and private Google Drive sync contracts.
        </p>

        <p>
          One week of intense Kotlin engineering, agentic pairing in Antigravity, and Android architecture hacking later, the initial production build of <strong className="font-semibold text-ink">HabbitAt</strong> was deployed and live on ParamStore.
        </p>

        <div className="p-4 bg-bg border border-border rounded-[4px] text-xs text-ink-muted space-y-2">
          <div className="flex items-center gap-2 font-mono font-semibold text-accent uppercase text-[11px]">
            <Sparkles size={14} /> The HabbitAt Rulebook
          </div>
          <p className="leading-relaxed">
            No artificial initial streak boosts. No unearned checkmarks. Just timestamped photo proof evaluated by vision AI before your streak advances. Starting strictly at zero.
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
