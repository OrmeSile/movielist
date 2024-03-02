/** @type {import('next').NextConfig} */
const nextConfig = {
  compiler: {
    styledComponents: true
  },
  async headers() {
    return [
      {
        source: "/api/:path*",
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
          destination: "http://host.docker.internal:8080/movies/:path*"
        }
      ]
    }
  }
};

export default nextConfig;