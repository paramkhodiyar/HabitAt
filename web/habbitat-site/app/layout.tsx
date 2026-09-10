import type { Metadata } from "next";
import { Fraunces, Plus_Jakarta_Sans, Inter } from "next/font/google";
import "./globals.css";
import { constructMetadata } from "@/lib/metadata";
import CookieBanner from "@/components/ui/CookieBanner";

const headingSerif = Fraunces({
  subsets: ["latin"],
  variable: "--font-heading-serif",
  display: "swap",
});

const headingSans = Plus_Jakarta_Sans({
  subsets: ["latin"],
  variable: "--font-heading-sans",
  display: "swap",
});

const fontBody = Inter({
  subsets: ["latin"],
  variable: "--font-body",
  display: "swap",
});

export const metadata: Metadata = constructMetadata();

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html
      lang="en"
      className={`${headingSerif.variable} ${headingSans.variable} ${fontBody.variable}`}
    >
      <body className="bg-bg text-ink min-h-screen font-body antialiased">
        {children}
        <CookieBanner />
      </body>
    </html>
  );
}
