package com.reactive_weather_api.aggregator

import com.reactive_weather_api.aggregator.api.openweather.forecast.model.GetWeatherForecastResponseDTO
import com.reactive_weather_api.aggregator.api.openweather.forecast.model.HourlyForecastDTO
import com.reactive_weather_api.aggregator.api.openweather.forecast.model.WeatherDTO
import com.reactive_weather_api.aggregator.api.openweather.geocoding.model.GetDirectGeocodingResponseDTO
import com.reactive_weather_api.aggregator.service.AsyncWeatherDataFetcher
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import reactor.core.publisher.Mono

@ExtendWith(MockKExtension::class)
@DisplayName("AsyncWeatherDataFetcherTest")
internal class AsyncWeatherDataFetcherTest {

    @MockK
    private lateinit var openWeatherRestClient: OpenWeatherRestClient

    @InjectMockKs
    private lateinit var subject: AsyncWeatherDataFetcher

    private val CITY = "London"
    private val mockGetDirectGeocodingResponseDTO = GetDirectGeocodingResponseDTO(51.5073219, -0.1276474)
    private val mockGetWeatherForecastResponseDTO = GetWeatherForecastResponseDTO(
        listOf(
            HourlyForecastDTO(
                23112,
                34.5,
                34.4,
                5,
                6,
                45.5,
                4.4,
                6,
                4,
                343.2,
                34,
                343.4,
                listOf(
                    WeatherDTO(1, "Sunny", "Sunny", "231&asd")
                ), 23.2
            )
        )
    )

    @Nested
    @DisplayName("fetchFromOpenWeather")
    inner class FetchFromOpenWeather {

        @Nested
        @DisplayName("retrieve geo coordinates based on city")
        inner class RetrieveGeoCoordinatesBasedOnCity {

            @Nested
            @DisplayName("if fetched successfully")
            inner class IfFetchedSuccessfully {

                @Nested
                @DisplayName("then retrieve weather data")
                inner class ThenRetrieveWeatherData {

                    @Nested
                    @DisplayName("if fetched successfully")
                    inner class IfFetchedSuccessfully {

                        @Test
                        fun `then build data and return it`() {
                            every { openWeatherRestClient.getDirectGeocodingByCityName(CITY) } returns Mono.just(
                                listOf(
                                    mockGetDirectGeocodingResponseDTO
                                )
                            )
                            every {
                                openWeatherRestClient.getWeatherData(
                                    mockGetDirectGeocodingResponseDTO.lat,
                                    mockGetDirectGeocodingResponseDTO.lon
                                )
                            } returns Mono.just(mockGetWeatherForecastResponseDTO)

                            subject.fetchFromOpenWeather(CITY)

                            verify(exactly = 1) { openWeatherRestClient.getDirectGeocodingByCityName(CITY) }
                            verify(exactly = 1) {
                                openWeatherRestClient.getWeatherData(
                                    mockGetDirectGeocodingResponseDTO.lat,
                                    mockGetDirectGeocodingResponseDTO.lon
                                )
                            }
                        }
                    }

                    @Nested
                    @DisplayName("if exception is thrown")
                    inner class IfExceptionIsThrown {

                        @Test
                        fun `then catch exception and return empty data`() {
                            every { openWeatherRestClient.getDirectGeocodingByCityName(CITY) } returns Mono.empty()

                            subject.fetchFromOpenWeather(CITY)

                            verify(exactly = 1) { openWeatherRestClient.getDirectGeocodingByCityName(CITY) }
                        }
                    }
                }
            }

            @Nested
            @DisplayName("if exception is thrown")
            inner class IfExceptionIsThrown {

                @Test
                fun `then catch exception and return empty data`() {

                }
            }
        }
    }
}