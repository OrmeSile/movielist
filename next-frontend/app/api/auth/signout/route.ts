import {NextRequest, NextResponse} from "next/server";
import {cookies} from "next/headers";

export const GET = (_req: NextRequest) => {
  cookies().delete("ACCESS_TOKEN")
  cookies().delete("ID_TOKEN")
  cookies().delete("REFRESH_TOKEN")
  sessionStorage.clear()
  return NextResponse.redirect("https://movies.orme.dev")
}