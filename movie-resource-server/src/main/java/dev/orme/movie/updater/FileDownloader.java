package dev.orme.movie.updater;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
  <a href="https://www.baeldung.com/webclient-stream-large-byte-array-to-file">reference</a>
  **/
public class FileDownloader {
    public static long fetch(WebClient client, String destination) throws IOException {
        Flux<DataBuffer> flux = client.get()
                .retrieve()
                .bodyToFlux(DataBuffer.class);
        Path path = Paths.get(destination);
        DataBufferUtils.write(flux, path).block();
        return Files.size(path);
    }
}
