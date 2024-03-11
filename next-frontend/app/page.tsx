import Link from "next/link";
import {Locale} from "@/components/test/Locale";

export default function Home() {

  return (
    <main>
      <Link href={"/auth"}><button>Login</button></Link>
      <Locale/>
    </main>
  );
}
