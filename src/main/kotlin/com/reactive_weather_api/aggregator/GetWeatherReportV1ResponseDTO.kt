package com.reactive_weather_api.aggregator

data class GetWeatherReportV1ResponseDTO(
    val location: String,
    val temperature: String,
    val condition: String
)
