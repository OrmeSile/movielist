import {NextRequest, NextResponse} from "next/server";
import {cookies} from "next/headers";

export const GET = async (request: NextRequest) => {
  const callbackUrl = request.nextUrl.searchParams.get("callback_url")
  if(!callbackUrl) return NextResponse.json({error: "missing callback URL"})
  const refreshToken = cookies().get("REFRESH_TOKEN")
  if(!refreshToken) return NextResponse.json({error: "missing refresh token"})
  const response = await fetch("https://auth.orme.dev/realms/movies/protocol/openid-connect/token",
    {method: 'POST',
      headers: {"Content-Type": "application/x-www-form-urlencoded"},
      body: `charset=utf-8&grant_type=refresh_token&client_id=movies&refresh_token=${refreshToken.value}`
    })
  if(!response.ok) return NextResponse.redirect("https://movies.orme.dev")
  const {id_token, expires_in, refresh_token, refresh_expires_in, access_token} = await response.json();
  cookies().set({name: "ID_TOKEN", value: id_token,httpOnly: true, maxAge: expires_in, secure: true, sameSite: "strict"})
  cookies().set({name: "REFRESH_TOKEN",value: refresh_token, secure: true, httpOnly: true, maxAge: refresh_expires_in,sameSite: "strict"})
  cookies().set({name: "ACCESS_TOKEN", value: access_token, httpOnly: true, secure: true,sameSite: "strict"})
  return NextResponse.redirect(callbackUrl)
}