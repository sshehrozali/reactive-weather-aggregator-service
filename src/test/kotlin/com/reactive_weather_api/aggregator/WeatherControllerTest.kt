package com.reactive_weather_api.aggregator

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.HttpStatus
import reactor.test.StepVerifier

@DisplayName("WeatherControllerTest")
@ExtendWith(MockKExtension::class)
internal class WeatherControllerTest {

    private val LOCATION = "New York"
    private val TEMPERATURE = "34"
    private val CONDITION = "Sunny"

    @MockK
    private lateinit var weatherService: WeatherService

    @InjectMockKs
    private lateinit var subject: WeatherController

    @Nested
    @DisplayName("getWeatherReportV1")
    inner class GetWeatherReportV1 {

        @Nested
        @DisplayName("when given valid location")
        inner class WhenGivenValidLocation {

            @Nested
            @DisplayName("then call service method to aggregate weather data")
            inner class ThenCallServiceToAggregateWeatherData {

                @Nested
                @DisplayName("if service method returns data successfully")
                inner class IfServiceReturnsDataSuccessfully {

                    @Test
                    fun `then return 200 OK with weather report in response body`() {
                        every { weatherService.buildWeatherReport() } returns WeatherReport(
                            LOCATION,
                            TEMPERATURE,
                            CONDITION
                        )

                        val result = subject.getWeatherReportV1(LOCATION)

                        StepVerifier.create(result)
                            .consumeNextWith { it ->
                                assertEquals(HttpStatus.OK.value(), it.statusCode.value())
                            }
                            .verifyComplete()

                        verify(exactly = 1) { weatherService.buildWeatherReport() }
                    }
                }

                @Nested
                @DisplayName("if service method throws exception")
                inner class IfServiceThrowsException {

                    @Test
                    fun `then catch exception and return 500 Internal Server with error message`() {
                        every { weatherService.buildWeatherReport() } throws ErrorGeneratingWeatherReport()

                        val result = subject.getWeatherReportV1(LOCATION)

                        StepVerifier.create(result)
                            .consumeNextWith { it ->
                                assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), it.statusCode.value())
                            }
                            .verifyComplete()

                        verify(exactly = 1) { weatherService.buildWeatherReport() }
                    }
                }
            }
        }

        @Nested
        @DisplayName("when given invalid location")
        inner class WhenGivenInvalidLocation {

            @Test
            fun `then return 400 Bad Request with error message in response body`() {
                val result = subject.getWeatherReportV1("abc")

                StepVerifier.create(result)
                    .consumeNextWith { it ->
                        assertEquals(HttpStatus.BAD_REQUEST.value(), it.statusCode.value())
                    }
                    .verifyComplete()

                verify(exactly = 0) { weatherService.buildWeatherReport() }
            }
        }
    }
}