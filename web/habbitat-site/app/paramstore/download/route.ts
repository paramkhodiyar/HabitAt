import { NextResponse } from "next/server";
import { SITE_CONFIG } from "@/lib/constants";

export async function GET(request: Request) {
  const targetUrl =
    process.env.NEXT_PUBLIC_APK_DOWNLOAD_URL || SITE_CONFIG.apk.staticPath;

  // If environment variable is set as absolute URL, redirect directly
  if (targetUrl.startsWith("http://") || targetUrl.startsWith("https://")) {
    return NextResponse.redirect(targetUrl, 302);
  }

  // Otherwise resolve relative URL against incoming request origin
  const requestUrl = new URL(request.url);
  const redirectUrl = new URL(targetUrl, requestUrl.origin);

  return NextResponse.redirect(redirectUrl, 302);
}
