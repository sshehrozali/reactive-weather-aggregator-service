package com.reactive_weather_api.aggregator.api.openweather.forecast.model

data class WeatherDTO(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)
