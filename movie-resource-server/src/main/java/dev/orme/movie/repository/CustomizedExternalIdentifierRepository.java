package dev.orme.movie.repository;

interface CustomizedExternalIdentifierRepository<T> {
    <S extends T> S save(S entity);
}
