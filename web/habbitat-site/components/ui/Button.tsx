import React from "react";
import Link from "next/link";

export interface ButtonProps
  extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: "primary" | "secondary" | "outline" | "ghost";
  size?: "sm" | "md" | "lg";
  href?: string;
  external?: boolean;
  children: React.ReactNode;
  className?: string;
}

export default function Button({
  variant = "primary",
  size = "md",
  href,
  external = false,
  children,
  className = "",
  disabled = false,
  ...props
}: ButtonProps) {
  const baseClasses =
    "inline-flex items-center justify-center font-semibold transition-colors focus-visible:outline-none rounded-[4px] cursor-pointer disabled:opacity-50 disabled:pointer-events-none select-none";

  const variantClasses = {
    primary:
      "btn-primary bg-accent text-white border border-accent hover:bg-[#A04323]",
    secondary:
      "bg-surface text-ink border border-border hover:bg-bg hover:border-ink/20 font-medium",
    outline:
      "bg-transparent text-ink border border-border hover:border-ink hover:bg-surface font-medium",
    ghost:
      "bg-transparent text-accent border border-transparent hover:underline font-medium",
  };

  const sizeClasses = {
    sm: "px-4 py-2 text-xs gap-1.5",
    md: "px-5 py-2.5 text-sm gap-2",
    lg: "px-7 py-3.5 text-base gap-2.5",
  };

  const combinedClasses = `${baseClasses} ${variantClasses[variant]} ${sizeClasses[size]} ${className}`;

  if (href) {
    if (external) {
      return (
        <a
          href={href}
          target="_blank"
          rel="noopener noreferrer"
          className={combinedClasses}
        >
          {children}
        </a>
      );
    }
    return (
      <Link href={href} className={combinedClasses}>
        {children}
      </Link>
    );
  }

  return (
    <button className={combinedClasses} disabled={disabled} {...props}>
      {children}
    </button>
  );
}
