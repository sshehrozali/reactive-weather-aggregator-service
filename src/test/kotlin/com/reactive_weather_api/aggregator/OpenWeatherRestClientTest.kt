package com.reactive_weather_api.aggregator

import com.reactive_weather_api.aggregator.model.GetDirectGeocodingResponseDTO
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@ExtendWith(MockKExtension::class)
@DisplayName("OpenWeatherRestClientTest")
internal class OpenWeatherRestClientTest {

    private val CITY = "London"
    private val DIRECT_GEOCODING_API_URL = "http://api.openweathermap.org/geo/1.0/direct?q=%S&appid=%S"
    private val WEATHER_FORECAST_API_URL =
        "https://api.openweathermap.org/data/3.0/onecall?lat=%S&lon=%S&exclude=%S&appid=%S"
    private val API_KEY = "API_KEY"
    private val CITY_LATITUDE = 51.5073219
    private val CITY_LONGITUDE = -0.1276474

    private val webClient = mockk<WebClient>()

    private val subject = OpenWeatherRestClient(webClient, API_KEY, DIRECT_GEOCODING_API_URL, WEATHER_FORECAST_API_URL)

    @Nested
    @DisplayName("getDirectGeocodingByCityName")
    inner class GetDirectGeocodingByCityName {

        @Nested
        @DisplayName("call GeocodingAPI to fetch city name")
        inner class CallGeocodingApiToFetchCityName {

            @Nested
            @DisplayName("when response is retrieved")
            inner class WhenResponseIsRetrieved {

                @Test
                fun `then transform response into DirectGeocodingData and return it`() {
                    val mockResponse = listOf(GetDirectGeocodingResponseDTO(CITY_LATITUDE, CITY_LONGITUDE))

                    val mockRequestSpec = mockk<WebClient.RequestHeadersUriSpec<*>>()
                    val mockResponseSpec = mockk<WebClient.ResponseSpec>()

                    every { webClient.get() } returns mockRequestSpec
                    every { mockRequestSpec.uri(any<String>()) } returns mockRequestSpec
                    every { mockRequestSpec.accept(any()) } returns mockRequestSpec
                    every { mockRequestSpec.retrieve() } returns mockResponseSpec

                    every { mockResponseSpec.onStatus(any(), any()) } returns mockResponseSpec

                    every { mockResponseSpec.bodyToMono<List<GetDirectGeocodingResponseDTO>>() } returns Mono.just(
                        mockResponse
                    )

                    val result = subject.getDirectGeocodingByCityName(CITY)

                    StepVerifier.create(result)
                        .expectNextMatches { it.first().lat == CITY_LATITUDE && it.first().lon == CITY_LONGITUDE }
                        .verifyComplete()

                    verify(exactly = 1) { webClient.get() }
                }
            }
        }
    }

    @Nested
    @DisplayName("getWeatherData")
    inner class GetWeatherData {

        @Nested
        @DisplayName("call WeatherAPI to fetch weather data based on city geo coordinates")
        inner class CallWeatherApiToFetchWeatherDataBasedOnCity {

            @Nested
            @DisplayName("when response is retrieved")
            inner class WhenResponseIsRetrieved {

                @Test
                fun `then transform response into DirectGeocodingData and return it`() {
                    val mockResponse = listOf(GetDirectGeocodingResponseDTO(CITY_LATITUDE, CITY_LONGITUDE))

                    val mockRequestSpec = mockk<WebClient.RequestHeadersUriSpec<*>>()
                    val mockResponseSpec = mockk<WebClient.ResponseSpec>()

                    every { webClient.get() } returns mockRequestSpec
                    every { mockRequestSpec.uri(any<String>()) } returns mockRequestSpec
                    every { mockRequestSpec.accept(any()) } returns mockRequestSpec
                    every { mockRequestSpec.retrieve() } returns mockResponseSpec

                    every { mockResponseSpec.onStatus(any(), any()) } returns mockResponseSpec

                    every { mockResponseSpec.bodyToMono<List<GetDirectGeocodingResponseDTO>>() } returns Mono.just(
                        mockResponse
                    )

                    val result = subject.getDirectGeocodingByCityName(CITY)

                    StepVerifier.create(result)
                        .expectNextMatches { it.first().lat == CITY_LATITUDE && it.first().lon == CITY_LONGITUDE }
                        .verifyComplete()

                    verify(exactly = 1) { webClient.get() }
                }
            }
        }
    }
}