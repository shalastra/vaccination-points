package io.shalastra.vaccinationpoints;

import io.shalastra.vaccinationpoints.model.Result;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CsvWriter {

    public void write(List<Result> finalResult) {
        File newFile = new File("result.csv");
        try (PrintWriter pw = new PrintWriter(newFile)) {
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
