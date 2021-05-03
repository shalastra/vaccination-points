package io.shalastra.vaccinationpoints.source;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.shalastra.vaccinationpoints.model.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@Service
public class UrlSourceReader {

    private final ObjectMapper objectMapper;

    @Autowired
    public UrlSourceReader(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<Point> ingestData(String url) throws IOException {
        return deserialize(new URL(url));
    }

    private List<Point> deserialize(final URL sourceUrl) throws IOException {
        return objectMapper.readValue(sourceUrl, new TypeReference<List<Point>>() {
        });
    }
}
