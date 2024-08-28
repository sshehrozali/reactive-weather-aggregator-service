package com.reactive_weather_api.aggregator

import com.reactive_weather_api.aggregator.service.AsyncWeatherDataAggregator
import com.reactive_weather_api.aggregator.service.AsyncWeatherDataFetcher
import com.reactive_weather_api.aggregator.service.WeatherService
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import reactor.test.StepVerifier
import kotlin.test.assertEquals

@DisplayName("WeatherServiceTest")
@ExtendWith(MockKExtension::class)
internal class WeatherServiceTest {

    private val LOCATION = "New York"
    private val TEMPERATURE = "34"
    private val CONDITION = "Sunny"

    @MockK
    private lateinit var asyncWeatherDataFetcher: AsyncWeatherDataFetcher

    @MockK
    private lateinit var asyncWeatherDataAggregator: AsyncWeatherDataAggregator

    @InjectMockKs
    private lateinit var subject: WeatherService

    @Nested
    @DisplayName("buildWeatherReport")
    inner class BuildWeatherReport {

        @Nested
        @DisplayName("Call AsyncWeatherDataFetcher")
        inner class CallAsyncWeatherDataFetcherTest {

            @Nested
            @DisplayName("if data is fetched and received")
            inner class IfDataIsFetchedAndReceived {

                @Nested
                @DisplayName("then call aggregator service to sort data")
                inner class ThenCallAggregatorServiceToSortData {

                    @Nested
                    @DisplayName("if aggregation is successful")
                    inner class IfAggregationIsSuccessful {

                        @Test
                        fun `then return weather report`() {
                            val result = subject.buildWeatherReport()

                            StepVerifier.create(result)
                                .consumeNextWith { it ->
                                    assertEquals(LOCATION, it.location)
                                }
                                .verifyComplete()

//                            verify(exactly = 1) { asyncWeatherDataFetcher.fetchWeatherData() }
                            verify(exactly = 1) { asyncWeatherDataAggregator.aggregateWeatherData() }
                        }
                    }

                    @Nested
                    @DisplayName("if aggregation fails")
                    inner class IfAggregationFails {

                        @Test
                        fun `then throw exception`() {

                        }
                    }
                }
            }

            @Nested
            @DisplayName("if data failed to fetch")
            inner class IfDataFailedToFetch {

                @Test
                fun `then throw exception`() {

                }
            }
        }
    }
}