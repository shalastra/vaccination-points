package io.shalastra.vaccinationpoints.runner;

import io.shalastra.vaccinationpoints.file.CsvWriter;
import io.shalastra.vaccinationpoints.file.FileParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import static io.shalastra.vaccinationpoints.aggregator.DataAggregator.*;

@Service
public class SimpleRunner implements CommandLineRunner {

    private final FileParser fileParser;
    private final CsvWriter csvWriter;

    @Autowired
    public SimpleRunner(FileParser fileParser, CsvWriter csvWriter) {
        this.fileParser = fileParser;
        this.csvWriter = csvWriter;
    }

    @Override
    public void run(String... args) {
        var points = fileParser.ingestData();

        var pointSumsByCounty = sumPointsByCounty(points);
        var countyAndTercPair = getCountyAndTerc(points);

        var result = aggregate(pointSumsByCounty, countyAndTercPair);

        csvWriter.write(result);
    }
}

