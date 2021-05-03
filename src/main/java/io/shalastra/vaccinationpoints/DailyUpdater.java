package io.shalastra.vaccinationpoints;

import io.shalastra.vaccinationpoints.file.CsvWriter;
import io.shalastra.vaccinationpoints.source.UrlSourceReader;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static io.shalastra.vaccinationpoints.aggregator.DataAggregator.*;

@Service
public class DailyUpdater {

    private final static String SOURCE_URL = "https://www.gov.pl/api/data/covid-vaccination-point";

    private final UrlSourceReader urlSourceReader;
    private final CsvWriter csvWriter;

    public DailyUpdater(UrlSourceReader urlSourceReader, CsvWriter csvWriter) {
        this.urlSourceReader = urlSourceReader;
        this.csvWriter = csvWriter;
    }

    @Scheduled(cron = "0 56 20 * * ?")
    public void getDailyUpdate() throws IOException {
        var points = urlSourceReader.ingestData(SOURCE_URL);

        var pointSumsByCounty = sumPointsByCounty(points);
        var countyAndTercPair = getCountyAndTerc(points);

        var result = aggregate(pointSumsByCounty, countyAndTercPair);

        csvWriter.write(result);
    }
}

