package dev.orme.movie.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import dev.orme.movie.DTO.LoginRequest;

import java.io.IOException;

public class LoginRequestDeserializer extends StdDeserializer<LoginRequest> {

    public LoginRequestDeserializer(){
        this(null);
    }

    public LoginRequestDeserializer(Class<?> vc){
        super(vc);
    }
    @Override
    public LoginRequest deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        String username = node.get("username").asText();
        String password = node.get("password").asText();
        return new LoginRequest(username, password);
    }
}
