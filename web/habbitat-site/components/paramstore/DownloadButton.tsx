import React from "react";
import Button from "@/components/ui/Button";
import { ArrowDownToLine } from "lucide-react";
import { SITE_CONFIG } from "@/lib/constants";

interface DownloadButtonProps {
  size?: "sm" | "md" | "lg";
  className?: string;
}

export default function DownloadButton({
  size = "lg",
  className = "",
}: DownloadButtonProps) {
  return (
    <Button
      href="/paramstore/download"
      variant="primary"
      size={size}
      className={className}
    >
      <ArrowDownToLine size={size === "lg" ? 20 : 16} />
      <span>Download HabbitAt v{SITE_CONFIG.apk.version} ({SITE_CONFIG.apk.size})</span>
    </Button>
  );
}
