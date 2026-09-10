import React from "react";
import { ArrowLeft, Compass } from "lucide-react";
import Container from "@/components/layout/Container";
import Button from "@/components/ui/Button";
import { constructMetadata } from "@/lib/metadata";

export const metadata = constructMetadata({
  title: "404 — Page Not Found",
  description: "The requested page does not exist on habitAt.",
  noIndex: true,
});

export default function NotFound() {
  return (
    <div className="py-20 md:py-32 flex items-center justify-center min-h-[70vh]">
      <Container className="max-w-2xl mx-auto text-center space-y-8">
        <div className="w-16 h-16 rounded-[4px] bg-accent/10 border border-accent/20 mx-auto flex items-center justify-center text-accent">
          <Compass size={32} />
        </div>

        <div className="space-y-3">
          <span className="font-mono text-xs font-semibold text-accent uppercase tracking-wider">
            Error 404
          </span>
          <h1 className="font-serif text-4xl sm:text-5xl font-bold text-ink">
            Page Not Found
          </h1>
          <p className="text-base text-ink-muted max-w-md mx-auto leading-relaxed">
            The page or route you are looking for has been moved, removed, or does not exist on the habitAt site.
          </p>
        </div>

        <div className="pt-4 flex flex-col sm:flex-row items-center justify-center gap-4">
          <Button href="/" variant="primary" size="md">
            <ArrowLeft size={16} />
            <span>Return to Home</span>
          </Button>
          <Button href="/paramstore" variant="secondary" size="md">
            <span>Explore ParamStore</span>
          </Button>
        </div>
      </Container>
    </div>
  );
}
