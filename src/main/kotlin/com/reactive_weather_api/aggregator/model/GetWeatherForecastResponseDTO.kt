package com.reactive_weather_api.aggregator.model

data class HourlyWeather(
    val dt: Long,
    val temp: Double,
    val feels_like: Double,
    val pressure: Int,
    val humidity: Int,
    val dew_point: Double,
    val uvi: Double,
    val clouds: Int,
    val visibility: Int,
    val wind_speed: Double,
    val wind_deg: Int,
    val wind_gust: Double,
    val weather: List<WeatherDTO>,
    val pop: Double
)

data class WeatherResponse(
    val hourly: List<HourlyWeather>
)

