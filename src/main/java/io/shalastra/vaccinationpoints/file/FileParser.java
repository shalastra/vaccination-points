package io.shalastra.vaccinationpoints.file;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.shalastra.vaccinationpoints.model.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class FileParser {

    private static final String FILE_NAME = "file.json";

    private final ObjectMapper objectMapper;
    private final ResourceLoader resourceLoader;

    @Autowired
    public FileParser(ObjectMapper objectMapper, ResourceLoader resourceLoader) {
        this.objectMapper = objectMapper;
        this.resourceLoader = resourceLoader;
    }

    public List<Point> ingestData() {
        return Optional.of(read().andThen(deserialize()).apply(FILE_NAME)).get().orElseThrow();
    }

    private Function<String, Optional<String>> read() {
        return fileName -> {
            try {
                var resource = resourceLoader.getResource(fileName);
                return Optional.of(Files.readString(resource.getFile().toPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Optional.empty();
        };
    }

    private Function<Optional<String>, Optional<List<Point>>> deserialize() {
        return json -> {
            try {
                return Optional.of(objectMapper.readValue(json.orElseThrow(), new TypeReference<List<Point>>() {
                }));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return Optional.empty();
        };
    }
}
