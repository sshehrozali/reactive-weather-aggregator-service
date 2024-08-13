package com.reactive_weather_api.aggregator

data class WeatherReport(
    val location: String,
    val temperature: String,
    val condition: String
)
