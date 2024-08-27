package com.reactive_weather_api.aggregator.model

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

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
    val weather: List<Weather>,
    val pop: Double
)

data class WeatherResponse(
    val hourly: List<HourlyWeather>
)

