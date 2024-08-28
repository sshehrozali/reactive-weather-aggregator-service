package com.reactive_weather_api.aggregator.model

data class WeatherDTO(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)
