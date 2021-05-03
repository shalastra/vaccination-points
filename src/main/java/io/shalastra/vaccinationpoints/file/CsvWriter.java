package io.shalastra.vaccinationpoints.file;

import io.shalastra.vaccinationpoints.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CsvWriter {

    public void write(List<Result> finalResult) throws IOException {
        String timestamp = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        log.info("Writing a new CSV file: " + timestamp + ".csv");

        Files.createDirectories(Paths.get("/home/szymon/Documents/Projects/vaccination-points/tmp"));

        File newFile = new File("tmp/" + timestamp + ".csv");
        try (PrintWriter pw = new PrintWriter(newFile)) {
            pw.println("województwo,powiat,TERYT,Liczba Punktów");

            finalResult
                    .stream()
                    .map(result -> String.format("%s,%s,%s,%s", result.voivodeship(), result.county(), result.terc(),
                            result.count()))
                    .collect(Collectors.toList())
                    .forEach(pw::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
