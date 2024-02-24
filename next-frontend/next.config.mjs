
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
};

export default nextConfig;