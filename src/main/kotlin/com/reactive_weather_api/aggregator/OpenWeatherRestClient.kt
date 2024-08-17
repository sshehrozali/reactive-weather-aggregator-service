package com.reactive_weather_api.aggregator

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.reactive_weather_api.aggregator.model.DirectGeocodingData
import com.reactive_weather_api.aggregator.model.GetDirectGeocodingResponseDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono

@Component
class OpenWeatherRestClient(
    private val webClient: WebClient,
    @Value("\${external.open-weather.api-key}") private val apiKey: String,
    @Value("\${external.open-weather.direct-geocoding-api-url}") private val directGeocodingApiUrl: String
) {

    fun getDirectGeocodingByCityName(city: String): Mono<DirectGeocodingData> {
        return webClient.get()
            .uri(directGeocodingApiUrl.format(city, apiKey))
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono<GetDirectGeocodingResponseDTO>()
            .flatMap { response ->
                Mono.just(DirectGeocodingData(response.lat, response.lon))
            }

    }

    fun getWeatherData(latitude: String, longitude: String): String {
        TODO()
    }
}