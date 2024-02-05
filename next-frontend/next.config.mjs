
/** @type {import('next').NextConfig} */
const nextConfig = {
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
        source: '/api/:path',
        destination: 'http://localhost:8080/:path'
      }
    ]
  }
};

export default nextConfig;
