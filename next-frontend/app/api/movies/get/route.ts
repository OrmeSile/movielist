export const GET = async (request: Request) => {
  const {body, headers, ...rest} = request
  const id = headers.get('id')
  headers.delete('id')
  return await fetch(`http://backend:8080/movies/get?id=${id}`, {headers})
}