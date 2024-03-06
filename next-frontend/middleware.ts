'use server'
import {NextRequest, NextResponse} from "next/server";
import {cookies} from "next/headers";

export async function middleware(req: NextRequest) {
  const idToken = cookies().get("ID_TOKEN")
  const refreshToken = cookies().get("REFRESH_TOKEN")
  const accessToken = cookies().get("ACCESS_TOKEN")
  if (!refreshToken) return NextResponse.redirect("https://movies.orme.dev")
  if(!idToken || !accessToken) {
    return NextResponse
      .redirect(`${process.env.NEXT_URL}/api/auth/refresh?callback_url=${process.env.NEXT_URL}${req.nextUrl.pathname}${req.nextUrl.search}`)
  }
  const res = NextResponse.next()
  res.headers.append("Authorization", `Bearer ${accessToken.value}`)
  return res
}

export const config = {
  matcher: ["/api/configuration/:path*", "/api/movies/:path*"]
}