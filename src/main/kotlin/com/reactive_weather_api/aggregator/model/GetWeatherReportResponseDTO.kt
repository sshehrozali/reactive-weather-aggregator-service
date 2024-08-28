package com.reactive_weather_api.aggregator.model

data class GetWeatherReportResponseDTO(
    val location: String,
    val temperature: String,
    val condition: String
)
