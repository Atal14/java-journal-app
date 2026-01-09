package com.edigest.atal.journalApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class WeatherResponse {
    @Getter
    @Setter
    private Current current;

    @Getter
    @Setter
    public static class Current {
        private int temperature;
        @JsonProperty("weather_description")
        private List<String> weatherDescription;
        @JsonProperty("feelslike")
        private int feelsLike;
    }
}