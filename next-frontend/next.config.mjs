
/** @type {import('next').NextConfig} */
const nextConfig = {
  compiler: {
    styledComponents: true
  },
  async headers() {
    return [{
      source: "/api/:path*",
      headers: [
        {key: "Access-Control-Allow-Origin", value: "true"}
      ]
    }
    ]
  },
  async rewrites() {
    return [
      {
        source: "/api/movies",
        destination: "http://host.docker.internal:8080/movies"
      }
    ]
  }
};

export default nextConfig;