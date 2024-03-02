create table if not exists public.country_movies
(
    country_id varchar(255) not null,
    movies_id  varchar(255) not null,
    primary key (country_id, movies_id)
);

alter table public.country_movies
    owner to postgres;

create table if not exists public.genre_movies
(
    genre_id  varchar(255) not null,
    movies_id varchar(255) not null,
    primary key (genre_id, movies_id)
);

alter table public.genre_movies
    owner to postgres;

create table if not exists public.language_movies
(
    language_id varchar(255) not null,
    movies_id   varchar(255) not null,
    primary key (language_id, movies_id)
);

alter table public.language_movies
    owner to postgres;

create table if not exists public.country
(
    iso_identifier varchar(255)
        unique,
    name           varchar(255),
    uuid           uuid not null
        primary key default gen_random_uuid()
);

alter table public.country
    owner to postgres;

create table if not exists public.movieGenre
(
    tmdb_id integer
        unique,
    name    varchar(255),
    uuid    uuid not null
        primary key default gen_random_uuid()
);

alter table public.movieGenre
    owner to postgres;

create table if not exists public.language
(
    english_name   varchar(255),
    iso_identifier varchar(255)
        unique,
    name           varchar(255),
    uuid           uuid not null
        primary key default gen_random_uuid()
);

alter table public.language
    owner to postgres;

create table if not exists public.movie_collection
(
    tmdb_id       integer      not null,
    backdrop_path varchar(255),
    name          varchar(255),
    poster_path   varchar(255),
    uuid          uuid not null
        primary key default gen_random_uuid()
);

alter table public.movie_collection
    owner to postgres;

create table if not exists public.movie
(
    adult                 boolean      not null,
    budget                integer      not null,
    popularity            real,
    revenue               integer      not null,
    runtime               integer      not null,
    tmdb_id               integer
        unique,
    video                 boolean      not null,
    vote_average          real,
    vote_count            integer      not null,
    backdrop_path         varchar(255),
    homepage              varchar(255),
    imdb_id               varchar(255),
    movie_collection_uuid varchar(255)
        constraint fk3m0pw1ogx7kjw1pjfdo1xukpi
            references public.movie_collection,
    original_language     varchar(255),
    original_title        varchar(255),
    overview              text,
    poster_path           varchar(255),
    release_date          varchar(255),
    status                varchar(255),
    tagline               varchar(255),
    title                 varchar(255),
    uuid                  varchar(255) not null
        primary key default gen_random_uuid()
);

alter table public.movie
    owner to postgres;

create table if not exists public.movie_genres
(
    genres_uuid varchar(255) not null
        constraint fkqcn0xwymf35jp4v1g48x7lx89
            references public.movieGenre,
    movies_uuid varchar(255) not null
        constraint fkpyhc5q9eb3f1g3vug3r4tbrfn
            references public.movie,
    primary key (genres_uuid, movies_uuid)
);

alter table public.movie_genres
    owner to postgres;

create table if not exists public.movie_production_countries
(
    movies_uuid               varchar(255) not null
        constraint fke20ca4nboa5iwugutysq4l4l4
            references public.movie,
    production_countries_uuid varchar(255) not null
        constraint fkg8x55k8g7l5bogw1d8w2xej2u
            references public.country,
    primary key (movies_uuid, production_countries_uuid)
);

alter table public.movie_production_countries
    owner to postgres;

create table if not exists public.movie_spoken_languages
(
    movies_uuid           varchar(255) not null
        constraint fk4gx1261aprmdeoripu4rkunhd
            references public.movie,
    spoken_languages_uuid varchar(255) not null
        constraint fkgh2prgtncnyg45x7lraq3oapw
            references public.language,
    primary key (movies_uuid, spoken_languages_uuid)
);

alter table public.movie_spoken_languages
    owner to postgres;

create table if not exists public.production_company
(
    tmdb_id             integer
        unique,
    logo_path           varchar(255),
    name                varchar(255),
    origin_country_uuid varchar(255)
        constraint fkpxs5wtl9d1daommhd1fo221um
            references public.country,
    uuid                uuid not null
        primary key default gen_random_uuid()
);

alter table public.production_company
    owner to postgres;

create table if not exists public.movie_production_companies
(
    movies_uuid               varchar(255) not null
        constraint fkh7by3flb7sy9p9ktkbo6fs0l6
            references public.movie,
    production_companies_uuid varchar(255) not null
        constraint fkpqey7ouq3g5uhn5lx00p41ur1
            references public.production_company,
    primary key (movies_uuid, production_companies_uuid)
);

alter table public.movie_production_companies
    owner to postgres;

