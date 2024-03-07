export interface Movie {
  uuid: string,
  adult: boolean,
  backdropPath: string,
  movieCollection: {
    uuid: string,
    name: string,
    posterPath: string,
    backdropPath: string,
    tmdb_id: number,
  }
  budget: number,
  movieGenres: [
    {
      uuid: string,
      name: string,
      id: number
    }],
  title: string,
  originalTitle: string,
  tmdbId: number,
  homepage: string,
  imdbId: string,
  originalLanguage: string,
  overview: string,
  popularity: number,
  posterPath: string,
  productionCompanies: [
    {
      uuid: string,
      logoPath: string,
      name: string,
      originCountry: string,
      tmdb_id: number,
    }]
  releaseDate: string,
  revenue: number,
  runtime: number,
  spokenLanguages: [{uuid: string, englishName: string, isoIdentifier: string, name: string}],
  status: string,
  tagline: string,
  video: boolean,
  voteAverage: number,
  voteCount: number,
  updated: boolean,
  genres: [{uuid: string, name: string, id: number}]
}