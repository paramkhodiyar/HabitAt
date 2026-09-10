import React from "react";
import Image from "next/image";
import DownloadButton from "./DownloadButton";
import { SITE_CONFIG } from "@/lib/constants";
import { ShieldCheck, HardDrive, Smartphone } from "lucide-react";

export default function AppListingHeader() {
  return (
    <div className="bg-surface border border-border p-6 md:p-10 rounded-[4px] space-y-8">
      <div className="flex flex-col md:flex-row items-start md:items-center justify-between gap-6">
        {/* App Branding & Info */}
        <div className="flex items-start gap-5">
          <div className="w-16 h-16 md:w-20 md:h-20 bg-bg border border-border rounded-[12px] p-2 shrink-0 flex items-center justify-center">
            <Image
              src="/images/icons/HabitAt_Icon_Simplified.svg"
              alt="HabbitAt App Icon"
              width={64}
              height={64}
              className="object-contain w-full h-full"
            />
          </div>

          <div className="space-y-1">
            <h1 className="font-serif text-3xl md:text-4xl font-bold tracking-tight text-ink">
              {SITE_CONFIG.name}
            </h1>
            <p className="text-base text-ink font-medium">
              {SITE_CONFIG.tagline}
            </p>
            <p className="text-xs text-ink-muted">
              By {SITE_CONFIG.developer} &bull; Sideload APK Edition
            </p>
          </div>
        </div>

        {/* Primary Download Action */}
        <div className="w-full md:w-auto flex flex-col items-stretch md:items-end gap-2">
          <DownloadButton size="lg" />
          <span className="text-[11px] font-mono text-ink-muted text-center md:text-right">
            Direct Download &bull; Safe & Signed APK
          </span>
        </div>
      </div>

      {/* App Spec Metadata Chips */}
      <div className="grid grid-cols-1 sm:grid-cols-3 gap-4 pt-4 border-t border-border">
        <div className="flex items-center gap-3 border border-border p-3 rounded-[4px] bg-bg">
          <div className="w-8 h-8 rounded-[4px] bg-accent/10 border border-accent/20 flex items-center justify-center text-accent shrink-0">
            <HardDrive size={16} />
          </div>
          <div>
            <span className="text-[10px] font-mono text-ink-muted uppercase block">
              Package Size
            </span>
            <span className="text-xs font-semibold text-ink">
              {SITE_CONFIG.apk.size}
            </span>
          </div>
        </div>

        <div className="flex items-center gap-3 border border-border p-3 rounded-[4px] bg-bg">
          <div className="w-8 h-8 rounded-[4px] bg-accent-2/10 border border-accent-2/20 flex items-center justify-center text-accent-2 shrink-0">
            <Smartphone size={16} />
          </div>
          <div>
            <span className="text-[10px] font-mono text-ink-muted uppercase block">
              Required OS
            </span>
            <span className="text-xs font-semibold text-ink">
              {SITE_CONFIG.apk.minAndroid}
            </span>
          </div>
        </div>

        <div className="flex items-center gap-3 border border-border p-3 rounded-[4px] bg-bg">
          <div className="w-8 h-8 rounded-[4px] bg-accent-3/15 border border-accent-3/30 flex items-center justify-center text-ink shrink-0">
            <ShieldCheck size={16} />
          </div>
          <div>
            <span className="text-[10px] font-mono text-ink-muted uppercase block">
              Verification
            </span>
            <span className="text-xs font-semibold text-ink">
              Signed & Verified APK
            </span>
          </div>
        </div>
      </div>
    </div>
  );
}
