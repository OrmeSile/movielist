const Auth = () => {
  return (
    <>
      <a
        href={`${process.env.AUTH_URL}/realms/movies/protocol/openid-connect/auth?response_type=code&scope=openid%20write%20read&client_id=movies&redirect_uri=${process.env.NEXT_URL}/api/auth/redirect`}>
        <button>Keycloak</button>
      </a>
    </>
  )
}

export default Auth