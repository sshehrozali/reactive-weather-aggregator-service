package com.reactive_weather_api.aggregator.model

data class OpenWeatherForecastReport(
    val weatherDescription: String,
    val temp: Double,
    val pressure: Int,
    val humidity: Int,
    val clouds: Int,
)
