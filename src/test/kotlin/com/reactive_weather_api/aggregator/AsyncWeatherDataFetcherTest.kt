package com.reactive_weather_api.aggregator

import com.reactive_weather_api.aggregator.model.GetDirectGeocodingResponseDTO
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
    private val mockGetDirectGeocodingResponseDto = GetDirectGeocodingResponseDTO(51.5073219, -0.1276474)

    @Nested
    @DisplayName("fetchFromOpenWeather")
    inner class FetchFromOpenWeather {

        // use OpenWeatherRestClient to fetch location coordinates based on city
        // if fetched successfully
        // then use OpenWeatherRestClient to fetch weather data based on location coordinates
        // if fetched successfully
        // then build data and return it
        // if exception is thrown
        // catch exception, return empty data
        // if exception is thrown
        // catch exception, return empty data


        @Nested
        @DisplayName("use OpenWeatherRestClient to fetch location coordinates based on city")
        inner class UseOpenWeatherRestClientToFetchLocationCoordinatesBasedOnCity {

            @Nested
            @DisplayName("if fetched successfully")
            inner class IfFetchedSuccessfully {

                @Nested
                @DisplayName("then use OpenWeatherRestClient to fetch weather data based on location coordinates")
                inner class ThenUseOpenWeatherRestClientToFetchWeatherDataBasedOnLocationCoordinates {

                    @Nested
                    @DisplayName("if fetched successfully")
                    inner class IfFetchedSuccessfully {

                        @Test
                        fun `then build data and return it`() {
                            every { openWeatherRestClient.getDirectGeocodingByCityName(CITY) } returns Mono.just(
                                listOf(
                                    mockGetDirectGeocodingResponseDto
                                )
                            )

                            subject.fetchFromOpenWeather(CITY)

                            verify(exactly = 1) { openWeatherRestClient.getDirectGeocodingByCityName(CITY) }
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