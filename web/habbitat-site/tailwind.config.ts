import type { Config } from "tailwindcss";

const config: Config = {
  content: [
    "./pages/**/*.{js,ts,jsx,tsx,mdx}",
    "./components/**/*.{js,ts,jsx,tsx,mdx}",
    "./app/**/*.{js,ts,jsx,tsx,mdx}",
  ],
  theme: {
    extend: {
      colors: {
        bg: "var(--color-bg)",
        surface: "var(--color-surface)",
        ink: "var(--color-ink)",
        "ink-muted": "var(--color-ink-muted)",
        border: "var(--color-border)",
        accent: "var(--color-accent)",
        "accent-2": "var(--color-accent-2)",
        "accent-3": "var(--color-accent-3)",
        danger: "var(--color-danger)",
      },
      fontFamily: {
        serif: ["var(--font-heading-serif)", "serif"],
        sans: ["var(--font-heading-sans)", "sans-serif"],
        body: ["var(--font-body)", "sans-serif"],
      },
    },
    // Explicitly disable default boxShadow and gradient utilities
    boxShadow: {},
    backgroundImage: {},
  },
  plugins: [],
};

export default config;
