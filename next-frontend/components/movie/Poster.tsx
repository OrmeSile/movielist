'use client'
import Image from "next/image";
import posterStyles from "@/styles/poster.module.css"

export const Poster = ({posterPath, backdropPath, alt}: {
  backdropPath: string,
  posterPath: string,
  alt: string
}) => {

  return (
    <div className={posterStyles.container}>
      <div className={posterStyles["backdrop-container"]}>
        <Image src={backdropPath} alt={alt} className={posterStyles.backdrop}
               fill={true}/>
      </div>
      <div className={posterStyles["poster-container"]}>
        <Image src={posterPath} alt={alt} className={posterStyles.poster}
               fill={true}/>
      </div>
    </div>
  )
}