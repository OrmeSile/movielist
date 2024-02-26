import NextAuth, {Awaitable} from "next-auth"
import KeycloakProvider from "next-auth/providers/keycloak"
import {JWT, JWTDecodeParams} from "next-auth/jwt";

//TODO pass cookie to resource server. add cookie support on spring
export const handler = NextAuth({
  providers: [
    KeycloakProvider({
      clientId: process.env.KEYCLOAK_ID!,
      clientSecret: process.env.KEYCLOAK_SECRET!,
      issuer: "https://auth.orme.dev/realms/movies",
      wellKnown: undefined,
      jwks_endpoint: "http://keycloak:8080/realms/movies/protocol/openid-connect/certs",
      authorization: {
        params: {
          scope: "openid email profile"
        },
        url: `${process.env.KEYCLOAK_ISSUER}/protocol/openid-connect/auth`
      },
      token: `http://keycloak:8080/realms/movies/protocol/openid-connect/token`,
      userinfo: `http://keycloak:8080/realms/movies/protocol/openid-connect/userinfo`,
  }),],
})

export {handler as GET , handler as POST }