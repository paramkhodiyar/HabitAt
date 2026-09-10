import { Metadata } from "next";
import { SITE_CONFIG } from "./constants";

export interface MetadataProps {
  title?: string;
  description?: string;
  image?: string;
  noIndex?: boolean;
  path?: string;
}

export function constructMetadata({
  title,
  description = SITE_CONFIG.description,
  image = "/images/icons/HabitAt_AppIcon_512.png",
  noIndex = false,
  path = "",
}: MetadataProps = {}): Metadata {
  const siteUrl = process.env.NEXT_PUBLIC_SITE_URL || "https://habbitat.app";
  const fullTitle = title ? `${title} | ${SITE_CONFIG.name}` : `${SITE_CONFIG.name} — AI Habit Enforcer`;
  const canonicalUrl = `${siteUrl}${path}`;

  return {
    title: fullTitle,
    description,
    keywords: [
      "HabbitAt",
      "AI Habit Enforcer",
      "Habit Tracker",
      "Photo Proof Habit Verification",
      "Android Habit App",
      "ParamStore",
      "Param Khodiyar",
    ],
    authors: [{ name: SITE_CONFIG.developer }],
    creator: SITE_CONFIG.developer,
    metadataBase: new URL(siteUrl),
    alternates: {
      canonical: canonicalUrl,
    },
    openGraph: {
      title: fullTitle,
      description,
      url: canonicalUrl,
      siteName: SITE_CONFIG.name,
      images: [
        {
          url: image,
          width: 1200,
          height: 630,
          alt: `${SITE_CONFIG.name} — ${SITE_CONFIG.tagline}`,
        },
      ],
      locale: "en_US",
      type: "website",
    },
    twitter: {
      card: "summary_large_image",
      title: fullTitle,
      description,
      images: [image],
      creator: "@paramkhodiyar",
    },
    icons: {
      icon: [
        { url: "/images/icons/HabitAt_Icon_Simplified.svg", type: "image/svg+xml" },
        { url: "/images/icons/HabitAt_AppIcon_512.png", type: "image/png", sizes: "512x512" },
        { url: "/favicon.ico", sizes: "any" },
      ],
      shortcut: "/images/icons/HabitAt_Icon_Simplified.svg",
      apple: [
        { url: "/images/icons/HabitAt_AppIcon_512.png", sizes: "180x180", type: "image/png" },
      ],
    },
    robots: noIndex
      ? {
          index: false,
          follow: false,
        }
      : {
          index: true,
          follow: true,
          googleBot: {
            index: true,
            follow: true,
            "max-video-preview": -1,
            "max-image-preview": "large",
            "max-snippet": -1,
          },
        },
  };
}
