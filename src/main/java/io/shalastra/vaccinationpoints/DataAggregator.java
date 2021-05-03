package io.shalastra.vaccinationpoints;

import io.shalastra.vaccinationpoints.model.County;
import io.shalastra.vaccinationpoints.model.Point;
import io.shalastra.vaccinationpoints.model.Result;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class DataAggregator {

    public static Map<String, Map<String, Long>> sumPointsByCounty(List<Point> points) {
        return points
                .stream()
                .collect(groupingBy(Point::voivodeship, groupingBy(Point::county, counting())));
    }

    public static Map<String, String> pairCountyAndTerc(List<Point> points) {
        return points
                .stream()
                .map(point -> new County(point.county(), point.terc().substring(0, 4) + "000"))
                .distinct()
                .collect(Collectors.toMap(County::county, County::terc));
    }

    public static List<Result> aggregate(Map<String, Map<String, Long>> pointsByVoivodeshipAndCounty, Map<String,
            String> counties) {
        return pointsByVoivodeshipAndCounty.entrySet().stream().map(entry -> getResults(counties, entry))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private static List<Result> getResults(Map<String, String> counties, Map.Entry<String, Map<String, Long>> entry) {
        String voivodeship = entry.getKey();
        return entry
                .getValue()
                .entrySet()
                .stream()
                .map(innerEntry -> new Result(voivodeship, innerEntry.getKey(), counties.get(innerEntry.getKey()),
                        innerEntry.getValue()))
                .collect(Collectors.toList());
    }
}
