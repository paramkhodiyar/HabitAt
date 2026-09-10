"use client";

import React, { useState, useEffect } from "react";
import Button from "@/components/ui/Button";

export default function CookieBanner() {
  const analyticsId = process.env.NEXT_PUBLIC_ANALYTICS_ID;
  const [visible, setVisible] = useState(false);

  useEffect(() => {
    if (!analyticsId) return;

    const timer = setTimeout(() => {
      const consent = localStorage.getItem("habitAt_cookie_consent");
      if (!consent) {
        setVisible(true);
      }
    }, 0);

    return () => clearTimeout(timer);
  }, [analyticsId]);

  if (!analyticsId || !visible) {
    return null;
  }

  const handleAccept = () => {
    localStorage.setItem("habitAt_cookie_consent", "accepted");
    setVisible(false);
  };

  const handleDecline = () => {
    localStorage.setItem("habitAt_cookie_consent", "declined");
    setVisible(false);
  };

  return (
    <div className="fixed bottom-4 left-4 right-4 md:left-auto md:right-6 md:max-w-md z-50 bg-surface border border-border p-5 rounded-[4px] space-y-3">
      <div className="space-y-1">
        <h4 className="font-serif font-bold text-sm text-ink">
          Privacy & Analytics Notice
        </h4>
        <p className="text-xs text-ink-muted leading-relaxed">
          We use anonymized analytics to measure app download traffic and improve performance. No personal habit data is ever tracked or uploaded.
        </p>
      </div>

      <div className="flex items-center gap-3 pt-1">
        <Button variant="primary" size="sm" onClick={handleAccept}>
          Accept Minimal Analytics
        </Button>
        <Button variant="outline" size="sm" onClick={handleDecline}>
          Decline
        </Button>
      </div>
    </div>
  );
}
