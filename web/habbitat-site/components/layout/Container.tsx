import React from "react";

interface ContainerProps extends React.HTMLAttributes<HTMLDivElement> {
  children: React.ReactNode;
  className?: string;
}

export default function Container({
  children,
  className = "",
  ...props
}: ContainerProps) {
  return (
    <div
      className={`w-full max-w-[1440px] mx-auto px-4 sm:px-6 md:px-8 lg:px-12 ${className}`}
      {...props}
    >
      {children}
    </div>
  );
}

export { GridSplit, GridTriptych, GridFullBleed } from "./GridRhythm";
