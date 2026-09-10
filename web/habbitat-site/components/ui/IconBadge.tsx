import React from "react";
import { LucideIcon } from "lucide-react";

export interface IconBadgeProps extends React.HTMLAttributes<HTMLDivElement> {
  icon: LucideIcon;
  variant?: "default" | "accent" | "jade" | "gold";
  size?: "sm" | "md" | "lg";
  className?: string;
}

export default function IconBadge({
  icon: Icon,
  variant = "default",
  size = "md",
  className = "",
  ...props
}: IconBadgeProps) {
  const baseClasses =
    "inline-flex items-center justify-center rounded-[4px] border transition-colors";

  const variantClasses = {
    default: "bg-surface text-ink border-border",
    accent: "bg-accent/10 text-accent border-accent/20",
    jade: "bg-accent-2/10 text-accent-2 border-accent-2/20",
    gold: "bg-accent-3/15 text-ink border-accent-3/30",
  };

  const sizeClasses = {
    sm: "w-8 h-8 p-1.5",
    md: "w-11 h-11 p-2.5",
    lg: "w-14 h-14 p-3.5",
  };

  const iconSizes = {
    sm: 16,
    md: 22,
    lg: 28,
  };

  return (
    <div
      className={`${baseClasses} ${variantClasses[variant]} ${sizeClasses[size]} ${className}`}
      {...props}
    >
      <Icon size={iconSizes[size]} strokeWidth={1.75} />
    </div>
  );
}
