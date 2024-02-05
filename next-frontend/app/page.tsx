'use client'
import {useEffect, useState} from "react";
import {useSearchParams} from "next/dist/client/components/navigation";

export default function Home() {

  const [isLoggedIn, setIsLoggedIn] = useState<boolean>(false)
  const [localCode, setCode] = useState<string>()
  const [token, setToken] = useState<string>()
  const [movies, setMovies] = useState<any[]>()
  useEffect(()=>{
    if(!code) return
    setCode(code)
  }, [])
  const code = useSearchParams().get("code")
  const login = async () => {
    window.location.assign('http://localhost:8083/auth/realms/custom/protocol/openid-connect/auth?response_type=code&scope=openid%20write%20read&client_id=newClient&redirect_uri=http://localhost:3000');
  }

  const getToken = async () => {
    if(!localCode) return
    const data = await fetch("http://localhost:8083/auth/realms/custom/protocol/openid-connect/token",
      {method: 'POST',
        headers: {"Content-Type": "application/x-www-form-urlencoded"},
        body: `grant_type=authorization_code&client_id=newClient&redirect_uri=http://localhost:3000&code=${localCode}`
      })
    const {access_token} = await data.json();
    setToken(access_token)
  }
  const fetchData = async () => {
    if(!token) return
    const data = await fetch("http://localhost:8080/movies/all", {headers:
        {Authorization: `Bearer ${token}`,}})
    setMovies(await data.json())
  }
  const logout = () => {

  }
  return (
    <main>
      {!isLoggedIn ? <button onClick={login}>Login</button>: <button onClick={logout}>Logout</button> }
      <button onClick={fetchData}>fetch</button>
      <button onClick={getToken}>get Token</button>
      {localCode && <h1>{localCode}</h1>}
      {token && <h1 style={{color: '#fc5'}}>{token}</h1>}
      {movies &&(
        <ul>
          {movies.map(movie => {
            return <p key={movie.id}>{movie.title}</p>
          })}
      </ul>)}
    </main>
  );
}
