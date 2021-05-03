package io.shalastra.vaccinationpoints;

import io.shalastra.vaccinationpoints.model.County;
import io.shalastra.vaccinationpoints.model.Point;
import io.shalastra.vaccinationpoints.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.shalastra.vaccinationpoints.DataAggregator.*;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@Service
public class Runner implements CommandLineRunner {

    private final FileParser fileParser;
    private final CsvWriter csvWriter;

    @Autowired
    public Runner(FileParser fileParser, CsvWriter csvWriter) {
        this.fileParser = fileParser;
        this.csvWriter = csvWriter;
    }

    @Override
    public void run(String... args) throws Exception {
        var points = fileParser.ingestData();

        var pointsByVoivodeshipAndCounty = sumPointsByCounty(points);
        var counties = pairCountyAndTerc(points);

        var finalResult = aggregate(pointsByVoivodeshipAndCounty, counties);

        csvWriter.write(finalResult);
    }
}

