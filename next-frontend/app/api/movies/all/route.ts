export const GET = async (request: Request) => {
  const {url, headers, ...rest} = request
  return await fetch("http://backend:8080/movies/all", {headers})
}