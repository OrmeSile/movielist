import {NextRequest, NextResponse} from "next/server";
import {cookies} from "next/headers";

const handler = async (req: NextRequest) => {
  const code = req.nextUrl.searchParams.get("code")
  console.log(code)
  const data = await fetch("http://keycloak:8080/realms/movies/protocol/openid-connect/token",
    {method: 'POST',
      headers: {"Content-Type": "application/x-www-form-urlencoded"},
      //TODO error: 'invalid_grant', error_description: 'Incorrect redirect_uri
      body: `charset=utf-8&grant_type=authorization_code&client_id=movies&redirect_uri=https://movies.orme.dev/api/api-testing&code=${code}`
    })
  const objectData = await data.json()
  const {id_token, refresh_token, access_token, expires_in, refresh_expires_in} = objectData;
  cookies().set({name: "ID_TOKEN", value: id_token,httpOnly: true, maxAge: expires_in, secure: true, sameSite: "strict"})
  cookies().set({name: "REFRESH_TOKEN",value: refresh_token, secure: true, httpOnly: true, maxAge: refresh_expires_in, sameSite: "strict"})
  cookies().set({name: "ACCESS_TOKEN", value: access_token, httpOnly: true, secure: true, sameSite: "strict"})
  return NextResponse.redirect("https://movies.orme.dev")
}

export {handler as GET, handler as POST}