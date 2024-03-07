import {cookies} from "next/headers";
import {Poster} from "@/components/movie/Poster";
import movieStyles from "@/styles/movie.module.css"
import {Movie} from "@/types/object/Movie";
import {ImageSizeDto} from "@/types/dto/ImageSizeDto";
import {BaseImageUrlDto} from "@/types/dto/BaseImageUrlDto";

const MovieView = async ({params}: { params: { id: number } }) => {

  const appCookies = cookies().toString();
  const imageSizesResponse = await fetch(`${process.env.API_URL_INTERNAL}/api/configuration/images`, {
    headers: {Cookie: appCookies},
    cache: "force-cache"
  })
  const baseUrlsResponse = await fetch(`${process.env.API_URL_INTERNAL}/api/configuration/url`, {
    headers: {Cookie: appCookies},
    cache: "force-cache"
  })
  const movieDataResponse = await fetch(`${process.env.API_URL_INTERNAL}/api/movies/${params.id}`, {
    headers: {Cookie: appCookies},
    cache: "force-cache"
  })
  const imageSizes: ImageSizeDto = await imageSizesResponse.json()
  const baseUrls: BaseImageUrlDto = await baseUrlsResponse.json()
  const movie: Movie = await movieDataResponse.json()
  const secureUrl = baseUrls.reduce((base, curr) => curr.key === 'image_url' ? curr : base)

  const maxPosterSize = imageSizes
    .reduce((base, curr,) => curr.value === "poster" && curr.size >= base.size ? curr : base);
  const maxBackgroundSize = imageSizes
    .reduce((base, curr) => curr.key === "backdrop" && curr.size >= base.size ? curr : base)
  const posterPath = `${secureUrl.value}${maxPosterSize.value}${movie.posterPath}`;
  const backdropPath = `${secureUrl.value}${maxBackgroundSize.value}${movie.backdropPath}`

  return (<MovieContainer movie={movie}
                          posterPath={posterPath}
                          backdropPath={backdropPath}
  />)
}

const MovieContainer = ({movie, posterPath, backdropPath}: {
  movie: Movie,
  posterPath: string,
  backdropPath: string
}) => {
  return (
    <div className={movieStyles.container}>
      <div className={movieStyles.header}>
        <Poster posterPath={posterPath} alt={`poster of ${movie.title}`}
                backdropPath={backdropPath}/>
        <div className={movieStyles['title-container']}>
          <h1>{movie.title}</h1>
          <h2>{movie.originalTitle}</h2>
        </div>
      </div>
      <p>{movie.overview}</p>
      <ul>{movie.genres.map(x => {
        return (<li key={x.uuid}>{x.name}</li>)
      })}</ul>
    </div>
  )
}

export default MovieView