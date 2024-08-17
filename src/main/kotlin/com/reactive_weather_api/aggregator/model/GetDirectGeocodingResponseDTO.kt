package com.reactive_weather_api.aggregator.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class GetDirectGeocodingResponseDTO(
    val lat: Double,
    val lon: Double
)
