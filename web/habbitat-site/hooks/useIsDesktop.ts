"use client";

import { useSyncExternalStore } from "react";

export function useIsDesktop(breakpointPx: number = 1024): boolean {
  return useSyncExternalStore(
    (callback) => {
      if (typeof window === "undefined") return () => {};
      const mediaQuery = window.matchMedia(`(min-width: ${breakpointPx}px)`);
      mediaQuery.addEventListener("change", callback);
      return () => mediaQuery.removeEventListener("change", callback);
    },
    () => {
      if (typeof window === "undefined") return false;
      return window.matchMedia(`(min-width: ${breakpointPx}px)`).matches;
    },
    () => false // Server snapshot for SSR
  );
}
