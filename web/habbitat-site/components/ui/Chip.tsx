import React from "react";

export interface ChipProps extends React.HTMLAttributes<HTMLDivElement> {
  variant?: "default" | "accent" | "jade" | "gold";
  size?: "sm" | "md";
  children: React.ReactNode;
  icon?: React.ReactNode;
  className?: string;
}

export default function Chip({
  variant = "default",
  size = "md",
  children,
  icon,
  className = "",
  ...props
}: ChipProps) {
  const baseClasses =
    "inline-flex items-center rounded-full font-medium transition-colors border";

  const variantClasses = {
    default: "bg-surface text-ink border-border",
    accent: "bg-accent/10 text-accent border-accent/20",
    jade: "bg-accent-2/10 text-accent-2 border-accent-2/20",
    gold: "bg-accent-3/15 text-ink border-accent-3/30",
  };

  const sizeClasses = {
    sm: "px-2.5 py-0.5 text-xs gap-1",
    md: "px-3.5 py-1 text-xs sm:text-sm gap-1.5",
  };

  return (
    <div
      className={`${baseClasses} ${variantClasses[variant]} ${sizeClasses[size]} ${className}`}
      {...props}
    >
      {icon && <span className="inline-flex items-center">{icon}</span>}
      <span>{children}</span>
    </div>
  );
}
