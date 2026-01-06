package com.edigest.atal.journalApp.service;

import com.edigest.atal.journalApp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {
    //Should be enviroment variables.S
    private static final String apiKey = "1";

    private static final String fullUrl = "http://api.weatherstack.com/current?access_key=YOUR_ACCESS_KEY&query=CITY";

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city) {
        String finalApi = fullUrl.replace("YOUR_ACCESS_KEY", apiKey).replace("CITY", city);
        ResponseEntity<WeatherResponse> response = this.restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);
        return response.getBody();
    }
}
