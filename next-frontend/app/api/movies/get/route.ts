import {getToken} from "next-auth/jwt";
import {NextRequest} from "next/server";

export const GET = async (request: NextRequest) => {
  const {body, headers, ...rest} = request
  const token = await getToken({req: request, raw: true})
  headers.append("Authorization", `Bearer ${token}`)
  return await fetch(`http://host.docker.internal:8080/movies/get?id=11836`, {headers: headers})
}