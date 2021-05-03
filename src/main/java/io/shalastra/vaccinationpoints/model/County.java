package io.shalastra.vaccinationpoints.model;

import java.util.Objects;

public record County(String county, String terc) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        County county1 = (County) o;
        return Objects.equals(county, county1.county);
    }

    @Override
    public int hashCode() {
        return Objects.hash(county);
    }
}
