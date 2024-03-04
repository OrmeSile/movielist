package dev.orme.movie.updater;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
  <a href="https://www.baeldung.com/webclient-stream-large-byte-array-to-file">reference</a>
  **/
public class FileDownloader {
    public static long fetch(RestClient client, String destination) throws IOException {
        DataBuffer buffer = client.get()
                .retrieve()
                .body(DataBuffer.class);
        Path path = Paths.get(destination);
        return Files.size(path);
    }
}
