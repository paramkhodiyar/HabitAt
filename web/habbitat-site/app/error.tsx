"use client";

import React, { useEffect } from "react";
import { AlertTriangle, RefreshCw, Home } from "lucide-react";
import Container from "@/components/layout/Container";
import Button from "@/components/ui/Button";

export default function ErrorBoundary({
  error,
  reset,
}: {
  error: Error & { digest?: string };
  reset: () => void;
}) {
  useEffect(() => {
    // Log the error silently to diagnostic channel in production
    console.error("Application Error Boundary caught error:", error);
  }, [error]);

  return (
    <div className="py-20 md:py-32 flex items-center justify-center min-h-[70vh]">
      <Container className="max-w-2xl mx-auto text-center space-y-8">
        <div className="w-16 h-16 rounded-[4px] bg-accent/10 border border-accent/20 mx-auto flex items-center justify-center text-accent">
          <AlertTriangle size={32} />
        </div>

        <div className="space-y-3">
          <span className="font-mono text-xs font-semibold text-accent uppercase tracking-wider">
            Runtime Error
          </span>
          <h1 className="font-serif text-4xl sm:text-5xl font-bold text-ink">
            Something Went Wrong
          </h1>
          <p className="text-base text-ink-muted max-w-md mx-auto leading-relaxed">
            An unexpected error occurred while rendering this view. You can attempt to reload the component state or return home.
          </p>
        </div>

        <div className="pt-4 flex flex-col sm:flex-row items-center justify-center gap-4">
          <Button
            variant="primary"
            size="md"
            onClick={() => reset()}
          >
            <RefreshCw size={16} />
            <span>Try Again</span>
          </Button>
          <Button href="/" variant="secondary" size="md">
            <Home size={16} />
            <span>Go to Homepage</span>
          </Button>
        </div>
      </Container>
    </div>
  );
}
