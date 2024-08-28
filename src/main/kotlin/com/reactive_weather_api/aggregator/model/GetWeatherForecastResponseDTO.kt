package com.reactive_weather_api.aggregator.model

data class GetWeatherForecastResponseDTO(
    val hourly: List<HourlyForecastDTO>
)

