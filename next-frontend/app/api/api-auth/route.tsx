import {NextResponse} from "next/server";

export const GET = async () => {
  const response = NextResponse.redirect("https://auth.orme.dev/realms/movies/protocol/openid-connect/auth?response_type=code&scope=openid%20write%20read&client_id=movies&redirect_uri=https://movies.orme.dev/api/api-testing")

}