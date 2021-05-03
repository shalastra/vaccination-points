package io.shalastra.vaccinationpoints.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Point(@JsonProperty("id") int id,
             @JsonProperty("ordinalNumber") String ordinalNumber,
             @JsonProperty("facilityName") String facilityName,
             @JsonProperty("terc") String terc,
             @JsonProperty("address") String address,
             @JsonProperty("zipCode") String zipCode,
             @JsonProperty("voivodeship") String voivodeship,
             @JsonProperty("county") String county,
             @JsonProperty("community") String community,
             @JsonProperty("place") String place,
             @JsonProperty("lon") String lon,
             @JsonProperty("lat") String lat,
             @JsonProperty("facilityType") int facilityType) {
}
