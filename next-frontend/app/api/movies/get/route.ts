import {NextRequest} from "next/server";
import {cookies} from "next/headers";

export const GET = async (request: NextRequest) => {
  const {body, headers, ...rest} = request
  const jwt = cookies().get("ACCESS_TOKEN")
  request.headers.append("Authorization", `Bearer ${jwt?.value}`)
  return await fetch(`http://host.docker.internal:8080/movies/get?id=11836`, {headers: headers})
}