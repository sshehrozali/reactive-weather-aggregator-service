package com.reactive_weather_api.aggregator.api.openweather.geocoding.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class GetDirectGeocodingResponseDTO(
    val lat: Double,
    val lon: Double
)