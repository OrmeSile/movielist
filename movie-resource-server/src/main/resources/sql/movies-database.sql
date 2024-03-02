create table country
(
    uuid           uuid default gen_random_uuid() not null
        primary key,
    iso_identifier varchar(255)
        constraint uk_mqkbwgwj7io4ydhyxooqbjndx
            unique,
    name           varchar(255)
);

alter table country
    owner to postgres;

create table language
(
    uuid           uuid default gen_random_uuid() not null
        primary key,
    english_name   varchar(255),
    iso_identifier varchar(255)
        constraint uk_axkhdj6vkkt9c943jahknsdgn
            unique,
    name           varchar(255)
);

alter table language
    owner to postgres;

create table movie_collection
(
    uuid          uuid default gen_random_uuid() not null
        primary key,
    backdrop_path varchar(255),
    name          varchar(255),
    poster_path   varchar(255),
    tmdb_id       integer                        not null
);

alter table movie_collection
    owner to postgres;

create table movie
(
    uuid                  uuid    default gen_random_uuid() not null
        primary key,
    vote_count            integer                           not null,
    adult                 boolean                           not null,
    backdrop_path         varchar(255),
    budget                integer                           not null,
    homepage              varchar(255),
    imdb_id               varchar(255),
    original_language     varchar(255),
    original_title        varchar(255),
    overview              text,
    popularity            real,
    poster_path           varchar(255),
    release_date          date,
    revenue               integer                           not null,
    runtime               integer                           not null,
    status                varchar(255),
    tagline               varchar(255),
    title                 varchar(255),
    tmdb_id               integer
        constraint uk_bgtoou3ammfcnlke2mv6uo522
            unique,
    video                 boolean                           not null,
    vote_average          real,
    movie_collection_uuid uuid
        constraint fk3m0pw1ogx7kjw1pjfdo1xukpi
            references movie_collection,
    is_updated            boolean default false             not null
);

alter table movie
    owner to postgres;

create index idx5sxepoqqvi8gd622jn06civb
    on movie (popularity desc);

create index idxpvd3vvh0466tx90k7xwxk1b46
    on movie (vote_average desc);

create index idx58pdasc8dgbjkj7alodmk43eh
    on movie (original_title desc);

create table movie_spoken_languages
(
    movies_uuid           uuid not null
        constraint fk4gx1261aprmdeoripu4rkunhd
            references movie,
    spoken_languages_uuid uuid not null
        constraint fkgh2prgtncnyg45x7lraq3oapw
            references language,
    primary key (movies_uuid, spoken_languages_uuid)
);

alter table movie_spoken_languages
    owner to postgres;

create table movie_genre
(
    uuid    uuid default gen_random_uuid() not null
        primary key,
    name    varchar(255),
    tmdb_id integer
        constraint uk_k73u204hsmnpd0o2vd4sjeb6i
            unique
);

alter table movie_genre
    owner to postgres;

create table movie_movie_genres
(
    movies_uuid       uuid not null
        constraint fk7sj0thcxjaxlbferoiyex71f3
            references movie,
    movie_genres_uuid uuid not null
        constraint fk6yiywq1rvervsn31u2fe42xlm
            references movie_genre,
    primary key (movies_uuid, movie_genres_uuid)
);

alter table movie_movie_genres
    owner to postgres;

create table production_company
(
    uuid           uuid default gen_random_uuid() not null
        primary key,
    logo_path      varchar(255),
    name           varchar(255),
    origin_country varchar(255),
    tmdb_id        integer
        constraint uk_dd7s8qnnusg8chrun8vs9iuet
            unique
);

alter table production_company
    owner to postgres;

create table movie_production_companies
(
    movies_uuid               uuid not null
        constraint fkh7by3flb7sy9p9ktkbo6fs0l6
            references movie,
    production_companies_uuid uuid not null
        constraint fkpqey7ouq3g5uhn5lx00p41ur1
            references production_company,
    primary key (movies_uuid, production_companies_uuid)
);

alter table movie_production_companies
    owner to postgres;

create table tv_genre
(
    uuid    uuid default gen_random_uuid() not null
        primary key,
    name    varchar(255),
    tmdb_id integer
        constraint uk_ak77yef4ahn9pf1llivpr8btl
            unique
);

alter table tv_genre
    owner to postgres;

create table movie_production_countries
(
    movies_uuid               uuid not null
        constraint fke20ca4nboa5iwugutysq4l4l4
            references movie,
    production_countries_uuid uuid not null
        constraint fkg8x55k8g7l5bogw1d8w2xej2u
            references country,
    primary key (movies_uuid, production_countries_uuid)
);

alter table movie_production_countries
    owner to postgres;