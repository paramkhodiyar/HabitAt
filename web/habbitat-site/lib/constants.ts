export interface ChangelogItem {
  version: string;
  date: string;
  changes: string[];
}

export const SITE_CONFIG = {
  name: "habitAt",
  tagline: "AI-Verified Habit Enforcement",
  description: "An AI-powered habit enforcer for personal discipline with photographic proof verification and escalating reminders.",
  developer: "Param Khodiyar",
  contactEmail: "paramkhodiyar1008@gmail.com",
  links: {
    github: "https://github.com/paramkhodiyar",
    linkedin: "https://www.linkedin.com/in/paramkhodiyar",
    website: "https://www.paramkhodiyar.dev",
  },
  apk: {
    version: "1.0.2",
    releaseDate: "2026-09-10",
    size: "16.1 MB",
    filename: "HabitAt-v1.0.2.apk",
    staticPath: "/apk/HabitAt-v1.0.2.apk",
    minAndroid: "Android 8.0 (API 26)+",
  },
};

export const CHANGELOG_DATA: ChangelogItem[] = [
  {
    version: "1.0.2",
    date: "2026-09-10",
    changes: [
      "Added Google Drive sync manager integration for cloud backup of proof logs.",
      "Optimized vision AI verification confidence thresholds and camera capture pipeline.",
      "Refined monthly calendar proof tile animations and streak milestone level-up visuals.",
    ],
  },
  {
    version: "1.0.1",
    date: "2026-09-02",
    changes: [
      "Introduced exact-alarm scheduling and escalating notification engine.",
      "Implemented frequency-aware habit management (daily, weekly, monthly commitments).",
      "Added zero-based streak tracking with terracotta miss state feedback.",
    ],
  },
  {
    version: "1.0.0",
    date: "2026-08-20",
    changes: [
      "Initial release of habitAt Android habit-enforcement app.",
      "Core Room database schema and Jetpack Compose design system implementation.",
    ],
  },
];
