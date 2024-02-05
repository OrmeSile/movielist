'use client'
import {redirect} from "next/navigation";
import {useEffect, useState} from "react";
import {cookies} from "next/headers";

export default function Home() {

  const [isLoggedIn, setIsLoggedIn] = useState<boolean>(false)

  const login = () => {
    window.location.href = "http://localhost:8083/auth/realms/custom/protocol/openid-connect/auth?response_type=code&scope=openid%20write%20read&client_id=newClient&redirect_uri=http://localhost:3000/"
  }
  const fetchData = async () => {
    const data = await fetch("http://localhost:8080/movies/all", {headers:
        {Authorization: "Bearer eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI2MGI1Y2YzNy01YmE3LTQzYjAtYTJiNC0xYTA0YzAxYTk0OWEifQ.eyJleHAiOjE3MDcxNzkzMzUsImlhdCI6MTcwNzE0MzMzNSwianRpIjoiODNlYTVjYjItNGFjNy00MTE2LWI1MGQtOGNlMzljNzBmMWEwIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgzL2F1dGgvcmVhbG1zL21hc3RlciIsInN1YiI6IjkzM2YzMDVmLWZhZWItNDQwMS04YTMxLTJhN2EyYWJhMDkxZSIsInR5cCI6IlNlcmlhbGl6ZWQtSUQiLCJzZXNzaW9uX3N0YXRlIjoiMGUyNzY2NTAtNDUzMi00ZDVkLWE0YzEtNzY2NTA4MDk4NDEzIiwic2lkIjoiMGUyNzY2NTAtNDUzMi00ZDVkLWE0YzEtNzY2NTA4MDk4NDEzIiwic3RhdGVfY2hlY2tlciI6IlBiYXc4aVZMS1FoS2szZW82LTdseklGdmlZQU4zaTU2MGwwMUdXa0JLdnMifQ.OZrnz4kF1o4ph6xdCL-GHqncepTPYA7kfRguMN73wRE",}})
    console.log(data)
  }
  const logout = () => {

  }
  return (
    <main>
      {!isLoggedIn ? <button onClick={login}>Login</button>: <button onClick={logout}>Logout</button> }
      <button onClick={fetchData}>fetch</button>
    </main>
  );
}
