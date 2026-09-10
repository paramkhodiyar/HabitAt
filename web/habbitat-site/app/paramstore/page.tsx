import React from "react";
import SiteHeader from "@/components/layout/SiteHeader";
import SiteFooter from "@/components/layout/SiteFooter";
import Container, { GridSplit } from "@/components/layout/Container";
import AppListingHeader from "@/components/paramstore/AppListingHeader";
import ScreenshotCarousel from "@/components/paramstore/ScreenshotCarousel";
import ChangelogList from "@/components/paramstore/ChangelogList";
import DownloadButton from "@/components/paramstore/DownloadButton";
import { Download, ShieldCheck, Settings, CheckCircle2 } from "lucide-react";
import IconBadge from "@/components/ui/IconBadge";
import { constructMetadata } from "@/lib/metadata";

export const metadata = constructMetadata({
  title: "ParamStore — Direct APK Download & Sideload Center",
  description: "Download verified signed Android APK release builds of habitAt directly via ParamStore.",
  path: "/paramstore",
});

export default function ParamStorePage() {
  return (
    <div className="min-h-screen flex flex-col bg-bg text-ink">
      <SiteHeader />

      <main className="flex-1 py-12 md:py-16">
        <Container className="space-y-16">
          {/* Main App Store Listing Header */}
          <AppListingHeader />

          {/* Screenshot Carousel */}
          <ScreenshotCarousel />

          {/* Sideload Installation Guide */}
          <section className="bg-surface border border-border p-6 md:p-10 rounded-[4px] space-y-6">
            <div className="max-w-2xl">
              <h2 className="font-serif text-2xl md:text-3xl font-bold text-ink">
                Direct Sideload Installation Guide
              </h2>
              <p className="text-sm text-ink-muted mt-2 leading-relaxed">
                habitAt is distributed directly via ParamStore as a signed Android APK. Follow these simple steps to install the app on your device.
              </p>
            </div>

            <div className="grid grid-cols-1 md:grid-cols-3 gap-6 pt-2">
              {/* Step 1 */}
              <div className="border border-border p-5 rounded-[4px] bg-bg space-y-3">
                <IconBadge icon={Download} variant="accent" size="md" />
                <h3 className="font-bold text-base text-ink">1. Download APK</h3>
                <p className="text-xs text-ink-muted leading-relaxed">
                  Tap the download button to save the signed <code className="text-accent font-mono text-[11px]">HabitAt-v1.0.2.apk</code> file to your device.
                </p>
              </div>

              {/* Step 2 */}
              <div className="border border-border p-5 rounded-[4px] bg-bg space-y-3">
                <IconBadge icon={Settings} variant="jade" size="md" />
                <h3 className="font-bold text-base text-ink">2. Enable Sideloading</h3>
                <p className="text-xs text-ink-muted leading-relaxed">
                  Open the file. If prompted by Android, tap Settings and toggle <span className="font-semibold text-ink">&quot;Allow from this source&quot;</span>.
                </p>
              </div>

              {/* Step 3 */}
              <div className="border border-border p-5 rounded-[4px] bg-bg space-y-3">
                <IconBadge icon={CheckCircle2} variant="gold" size="md" />
                <h3 className="font-bold text-base text-ink">3. Install & Launch</h3>
                <p className="text-xs text-ink-muted leading-relaxed">
                  Tap Install to complete setup. Open habitAt to create your first habit and start your verified streak.
                </p>
              </div>
            </div>
          </section>

          {/* Changelog & Feature Bullets Split */}
          <GridSplit
            left={<ChangelogList />}
            right={
              <div className="bg-surface border border-border p-6 rounded-[4px] space-y-6 self-start">
                <h3 className="font-serif text-xl font-bold text-ink">
                  Package Security & Integrity
                </h3>
                <ul className="space-y-3 text-xs text-ink-muted">
                  <li className="flex items-start gap-2.5">
                    <ShieldCheck size={16} className="text-accent shrink-0 mt-0.5" />
                    <span>Signed release build with cryptographic SHA-256 package verification.</span>
                  </li>
                  <li className="flex items-start gap-2.5">
                    <ShieldCheck size={16} className="text-accent-2 shrink-0 mt-0.5" />
                    <span>Zero third-party tracking scripts or invasive ad telemetry SDKs.</span>
                  </li>
                  <li className="flex items-start gap-2.5">
                    <ShieldCheck size={16} className="text-accent-3 shrink-0 mt-0.5" />
                    <span>Google Drive sync contract enabled for private user-owned cloud backups.</span>
                  </li>
                </ul>

                <div className="pt-4 border-t border-border">
                  <DownloadButton size="md" className="w-full" />
                </div>
              </div>
            }
          />
        </Container>
      </main>

      <SiteFooter />
    </div>
  );
}
