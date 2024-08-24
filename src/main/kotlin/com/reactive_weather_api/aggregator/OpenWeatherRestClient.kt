package com.reactive_weather_api.aggregator

import com.reactive_weather_api.aggregator.exception.OpenWeatherRestClientException
import com.reactive_weather_api.aggregator.model.DirectGeocodingData
import com.reactive_weather_api.aggregator.model.GetDirectGeocodingResponseDTO
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatusCode
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

    private val log = LoggerFactory.getLogger(OpenWeatherRestClient::class.java)

    fun getDirectGeocodingByCityName(city: String): Mono<DirectGeocodingData> {
        return webClient.get()
            .uri(directGeocodingApiUrl.format(city, apiKey))
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError) { response ->
                response.bodyToMono<String>().flatMap { it ->
                    log.error("Error HTTP 4xx while calling GeocodingAPI: $it")
                    Mono.error(OpenWeatherRestClientException())
                }
            }
            .onStatus(HttpStatusCode::is5xxServerError) { response ->
                response.bodyToMono<String>().flatMap {
                    log.error("Error HTTP 5xx while calling GeocodingAPI: $it")
                    Mono.error(OpenWeatherRestClientException())
                }
            }
            .bodyToMono<List<GetDirectGeocodingResponseDTO>>()
            .flatMap { response ->
                Mono.just(DirectGeocodingData(response.first().lat, response.first().lon))
            }

    }

    fun getWeatherData(latitude: String, longitude: String): String {
        TODO()
    }
}