import React from "react";
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
    <div className="py-12 md:py-20 space-y-12">
      <Container className="max-w-4xl mx-auto space-y-10">
        {/* Page Heading - No decorative pill eyebrow */}
        <div className="space-y-3 text-center sm:text-left">
          <h1 className="font-serif text-4xl sm:text-5xl font-bold tracking-tight text-ink">
            About the Developer
          </h1>
          <p className="text-base sm:text-lg text-ink-muted leading-relaxed max-w-2xl">
            The story, engineering principles, and motivation behind HabbitAt&apos;s commitment enforcement system.
          </p>
        </div>

        {/* Editorial Single Column Flow */}
        <div className="space-y-8">
          <ProfileCard />
          <StoryBlock />
          <SocialLinks />
        </div>
      </Container>
    </div>
  );
}
