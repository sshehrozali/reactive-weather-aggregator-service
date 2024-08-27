package com.reactive_weather_api.aggregator.service

import com.reactive_weather_api.aggregator.OpenWeatherRestClient
import org.springframework.stereotype.Component

@Component
class AsyncWeatherDataFetcher(
    private val openWeatherRestClient: OpenWeatherRestClient
) {

    fun fetchFromOpenWeather(city: String) {

        // call Geocoding API to fetch lat, long
        // call WeatherAPI using lat, long to fetch weather data
        openWeatherRestClient.getDirectGeocodingByCityName(city)
    }
}