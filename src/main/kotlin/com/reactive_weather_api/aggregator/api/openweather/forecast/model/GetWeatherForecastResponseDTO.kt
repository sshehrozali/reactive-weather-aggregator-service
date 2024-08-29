package com.reactive_weather_api.aggregator.api.openweather.forecast.model

data class GetWeatherForecastResponseDTO(
    val hourly: List<HourlyForecastDTO>
)

