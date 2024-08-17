package com.reactive_weather_api.aggregator

import com.reactive_weather_api.aggregator.model.GetDirectGeocodingResponseDTO
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@ExtendWith(MockKExtension::class)
@DisplayName("OpenWeatherRestClientTest")
internal class OpenWeatherRestClientTest {

    private val CITY = "London"
    private val DIRECT_GEOCODING_API_URL = "http://api.openweathermap.org/geo/1.0/direct?q=%S&appid=API_KEY"
    private val CITY_LATITUDE = 51.5073219
    private val CITY_LONGITUDE = -0.1276474

    @MockK
    private lateinit var webClient: WebClient

    @InjectMockKs
    private lateinit var subject: OpenWeatherRestClient

    @Nested
    @DisplayName("getDirectGeocodingByCityName")
    inner class GetDirectGeocodingByCityName {

        // call Geocoding API to fetch city name
        // if API call is 200 OK
        // then extract fields from response, populate Mono<T> and return it
        // if API call is not 200 OK
        // then log error message and return empty Mono<T>

        @Nested
        @DisplayName("call GeocodingAPI to fetch city name")
        inner class CallGeocodingAPIToFetchCityName {

            @Nested
            @DisplayName("if API call is 200 OK")
            inner class IfAPICallIs200OK {

                @Test
                fun `then extract fields from response, populate Mono and return it`() {
                    val mockGetDirectGeocodingResponseData =
                        Mono.just(listOf(GetDirectGeocodingResponseDTO(CITY_LATITUDE, CITY_LONGITUDE)))

                    every {
                        webClient.get()
                            .uri(DIRECT_GEOCODING_API_URL.format(CITY))
                            .accept(MediaType.APPLICATION_JSON)
                            .retrieve()
                            .bodyToMono<List<GetDirectGeocodingResponseDTO>>()
                    } returns mockGetDirectGeocodingResponseData

                    val result = subject.getDirectGeocodingByCityName(CITY)

                    StepVerifier.create(result)
                        .consumeNextWith { it ->
                            assertEquals(CITY_LATITUDE, it.latitude)
                            assertEquals(CITY_LONGITUDE, it.longitude)
                        }
                        .verifyComplete()

                    verify(exactly = 1) {
                        webClient.get()
                            .uri(DIRECT_GEOCODING_API_URL.format(CITY))
                            .accept(MediaType.APPLICATION_JSON)
                            .retrieve()
                            .bodyToMono<List<GetDirectGeocodingResponseDTO>>()
                    }
                }
            }

            @Nested
            @DisplayName("if API calls is not 200 OK")
            inner class IfAPICallIsNot200OK {

                @Nested
                @DisplayName("then log error message and return empty Mono<T>")
                inner class ThenLogErrorMessageAndReturnEmptyMono {

                }
            }
        }
    }

    @Nested
    @DisplayName("getWeatherData")
    inner class GetWeatherData {

        // call WeatherAPI to fetch data
        // if API call is 200 OK
        // then extract fields from response, populate Mono<T> and return it
        // if API call is not 200 OK
        // then log error message and return empty Mono<T>
    }
}