package dev.orme.movie.DTO;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.orme.movie.deserializer.LoginRequestDeserializer;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonDeserialize(using = LoginRequestDeserializer.class)
public record LoginRequest (String username, String password) {}
