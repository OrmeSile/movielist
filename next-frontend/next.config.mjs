/** @type {import('next').NextConfig} */
const nextConfig = {
  compiler: {
    styledComponents: true
  },
  images: {
    remotePatterns: [{
      protocol: "https",
      hostname: "image.tmdb.org/**",
    }]
  },
  async headers() {
    return [
      {
        source: "/api/movies/:path*",
        headers: [
          {key: "Access-Control-Allow-Origin", value: "true"}
        ]
      },
      {
        source: "/api/configuration/:path*",
        headers: [
          {key: "Access-Control-Allow-Origin", value: "true"}
        ]
      },
    ]
  },
  //TODO adapt for full docker use
  async rewrites() {
    return {
      beforeFiles: [
        {
          source: "/api/movies/:path*",
          destination: "http://host.docker.internal:8080/api/movies/:path*"
        },
        {
          source: "/api/configuration/:path",
          destination: "http://host.docker.internal:8080/api/configuration/:path*"
        }
      ]
    }
  },
};

export default nextConfig;