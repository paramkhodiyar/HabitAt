import React from "react";
import Link from "next/link";
import { ArrowLeft } from "lucide-react";
import SiteHeader from "@/components/layout/SiteHeader";
import SiteFooter from "@/components/layout/SiteFooter";
import Container from "@/components/layout/Container";
import ProfileCard from "@/components/about/ProfileCard";
import StoryBlock from "@/components/about/StoryBlock";
import SocialLinks from "@/components/about/SocialLinks";
import { SITE_CONFIG } from "@/lib/constants";
import { constructMetadata } from "@/lib/metadata";

export const metadata = constructMetadata({
  title: `About & Developer Story`,
  description: `Learn about Param Khodiyar, creator of ${SITE_CONFIG.name}, and the engineering philosophy behind AI-verified habit enforcement.`,
  path: "/about",
});

export default function AboutPage() {
  return (
    <div className="min-h-screen flex flex-col bg-bg text-ink">
      <SiteHeader />

      <main className="flex-1 py-12 md:py-16">
        <Container className="max-w-4xl mx-auto space-y-8">
          {/* Back Navigation Button */}
          <Link
            href="/"
            className="inline-flex items-center gap-2 text-xs font-semibold text-ink-muted hover:text-accent transition-colors group"
          >
            <ArrowLeft size={14} className="group-hover:-translate-x-0.5 transition-transform" />
            <span>Back to Overview</span>
          </Link>

          {/* Page Heading */}
          <div className="space-y-3">
            <h1 className="font-serif text-4xl sm:text-5xl font-bold tracking-tight text-ink">
              About the Developer
            </h1>
            <p className="text-base sm:text-lg text-ink-muted leading-relaxed max-w-2xl">
              The story, engineering principles, and motivation behind habitAt&apos;s commitment enforcement system.
            </p>
          </div>

          {/* Editorial Single Column Flow */}
          <div className="space-y-8">
            <ProfileCard />
            <StoryBlock />
            <SocialLinks />
          </div>
        </Container>
      </main>

      <SiteFooter />
    </div>
  );
}
