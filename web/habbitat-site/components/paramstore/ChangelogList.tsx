import React from "react";
import { CHANGELOG_DATA } from "@/lib/constants";
import { CheckCircle2 } from "lucide-react";

export default function ChangelogList() {
  return (
    <div className="space-y-6">
      <h2 className="font-serif text-2xl font-bold text-ink">
        Version Release History
      </h2>

      <div className="space-y-4">
        {CHANGELOG_DATA.map((item, index) => (
          <div
            key={item.version}
            className="bg-surface border border-border p-6 rounded-[4px] space-y-4"
          >
            <div className="flex items-center justify-between border-b border-border pb-3">
              <div className="flex items-center gap-3">
                <span className="font-serif font-bold text-lg text-ink">
                  Version {item.version}
                </span>
                {index === 0 && (
                  <span className="text-xs font-mono font-semibold text-accent-2 bg-accent-2/10 px-2.5 py-0.5 rounded-[4px] border border-accent-2/20">
                    Latest Release
                  </span>
                )}
              </div>
              <span className="text-xs font-mono text-ink-muted">
                Released {item.date}
              </span>
            </div>

            <ul className="space-y-2">
              {item.changes.map((change, i) => (
                <li key={i} className="flex items-start gap-2.5 text-sm text-ink-muted">
                  <CheckCircle2 size={16} className="text-accent shrink-0 mt-0.5" />
                  <span className="leading-relaxed">{change}</span>
                </li>
              ))}
            </ul>
          </div>
        ))}
      </div>
    </div>
  );
}
