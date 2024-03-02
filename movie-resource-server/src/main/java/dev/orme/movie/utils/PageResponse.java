package dev.orme.movie.utils;

public record PageResponse<T>(T content, long previous, long next, int size) {}
