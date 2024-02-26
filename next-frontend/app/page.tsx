'use client'
import {useEffect, useState} from "react";
import {useSearchParams} from "next/dist/client/components/navigation";
import {Button} from "@/components/styled/Button";
import Link from "next/link";

export default function Home() {

  const [isLoggedIn, setIsLoggedIn] = useState<boolean>(false)
  const [localCode, setCode] = useState<string>()
  const [token, setToken] = useState<string>()
  const [movies, setMovies] = useState<any[]>([])
  const [movieId, setMovieId] = useState<string>("")

  useEffect(() => {
    if (!code) return
    setCode(code)
  }, [])

  const code = useSearchParams().get("code")
  const login = async () => {
    window.location.assign('https://auth.orme.dev/realms/movies/protocol/openid-connect/auth?response_type=code&scope=openid%20write%20read&client_id=movies&redirect_uri=https://movies.orme.dev/api/api-testing');
    // await router.push("/api/api-auth")
  }


  const getToken = async () => {
    if (!localCode) return
    const data = await fetch("https://auth.orme.dev/realms/movies/protocol/openid-connect/token",
      {
        method: 'POST',
        headers: {"Content-Type": "application/x-www-form-urlencoded"},
        body: `charset=utf-8&grant_type=authorization_code&client_id=movies&redirect_uri=https://movies.orme.dev/&code=${localCode}`
      })
    const {access_token} = await data.json();
    setToken(access_token)
  }
  const fetchData = async () => {
    if (!token) return
    const data = await fetch(`${window.location.origin}/api/movies/all`, {
      headers:
        {Authorization: `Bearer ${token}`,}
    })
    setMovies(await data?.json())
  }

  const getMovie = async (e: React.MouseEvent) => {
    e.preventDefault()
    if (!token) return
    const data = await fetch(`${window.location.origin}/api/movies/get`, {
      headers: {
        Authorization: `Bearer ${token}`,
        id: movieId
      }
    })
    if (data.ok) setMovies([...movies, await data?.json()]);
  }
  const logout = () => {

  }
  return (
    <main>
      {!isLoggedIn ? <Button onClick={login}>Login</Button> :
        <Button onClick={logout}>Logout</Button>}
      <button onClick={getToken}>get Token</button>
      {token && <button onClick={fetchData}>fetch all movies</button>}
      {token && (
        <form>
          <input type={"number"} name={"movieId"} value={movieId}
                 onChange={(e) => setMovieId(e.target.value)}/>
          <input type={"submit"} value={"submit"} onClick={getMovie}/>
        </form>)}
      {localCode && <h1>{localCode}</h1>}
      {token && <h1 style={{color: '#fc5'}}>{token}</h1>}
      {movies && movies.length > 0 && (
        <ul>
          {movies.map(movie => {
            return <p key={movie.id}>{JSON.stringify(movie, null, 2)}</p>
          })}
        </ul>)}
    </main>
  );
}
