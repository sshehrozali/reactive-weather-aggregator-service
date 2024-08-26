package com.reactive_weather_api.aggregator.service

import org.springframework.stereotype.Component

@Component
class AsyncWeatherDataFetcher {

    fun fetchFromOpenWeather(city: String) {

        // call Geocoding API to fetch lat, long
        // call WeatherAPI using lat, long to fetch data
    }
}