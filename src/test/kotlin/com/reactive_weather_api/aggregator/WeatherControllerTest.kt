package com.reactive_weather_api.aggregator

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

@DisplayName("WeatherControllerTest")
class WeatherControllerTest {

    private val weatherService =
    private val subject = WeatherController()

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

                    @Nested
                    @DisplayName("then return 200 OK with weather report in response body")
                    inner class ThenReturn200OkWithWeatherReportInResponseBody {

                    }
                }

                @Nested
                @DisplayName("if service method throws exception")
                inner class IfServiceThrowsException {

                    @Nested
                    @DisplayName("then catch exception and return 500 Internal Server with error message")
                    inner class ThenCatchExceptionAndReturn500InternalServerErrorWithErrorMessage {

                    }
                }
            }
        }

        @Nested
        @DisplayName("when given invalid location")
        inner class WhenGivenInvalidLocation {

            @Nested
            @DisplayName("then return 400 Bad Request with error message in response body")
            inner class ThenReturn400BadRequestWithErrorMessageInResponseBody {

            }
        }
    }
}