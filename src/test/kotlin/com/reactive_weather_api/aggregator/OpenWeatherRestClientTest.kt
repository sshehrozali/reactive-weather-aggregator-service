package com.reactive_weather_api.aggregator

import com.reactive_weather_api.aggregator.exception.OpenWeatherRestClientException
import com.reactive_weather_api.aggregator.model.GetDirectGeocodingResponseDTO
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@ExtendWith(MockKExtension::class)
@DisplayName("OpenWeatherRestClientTest")
internal class OpenWeatherRestClientTest {

    private val CITY = "London"
    private val DIRECT_GEOCODING_API_URL = "http://api.openweathermap.org/geo/1.0/direct?q=%S&appid=%S"
    private val API_KEY = "API_KEY"
    private val CITY_LATITUDE = 51.5073219
    private val CITY_LONGITUDE = -0.1276474

    private val webClient = mockk<WebClient>()
    private val responseSpec = mockk<ResponseSpec>()

    private val subject = OpenWeatherRestClient(webClient, API_KEY, DIRECT_GEOCODING_API_URL)

    @Nested
    @DisplayName("getDirectGeocodingByCityName")
    inner class GetDirectGeocodingByCityName {

        @Nested
        @DisplayName("call GeocodingAPI to fetch city name")
        inner class CallGeocodingAPIToFetchCityName {

            @Nested
            @DisplayName("if API call is 200 OK")
            inner class IfAPICallIs200OK {

                @Test
                fun `then transform response into DirectGeocodingData and return it`() {
                    val mockResponse = "[{\"lat\":51.5074,\"lon\":-0.1278}]"

                    val mockRequestSpec = mockk<WebClient.RequestHeadersUriSpec<*>>()
                    val mockResponseSpec = mockk<WebClient.ResponseSpec>()

                    every { webClient.get() } returns mockRequestSpec
                    every {
                        mockRequestSpec.uri(
                            DIRECT_GEOCODING_API_URL.format(
                                CITY,
                                API_KEY
                            )
                        )
                    } returns mockRequestSpec
                    every { mockRequestSpec.accept(MediaType.APPLICATION_JSON) } returns mockRequestSpec
                    every { mockRequestSpec.retrieve() } returns mockResponseSpec
                    every { mockResponseSpec.onStatus(any(), any()) } returns mockResponseSpec
                    every { mockResponseSpec.bodyToMono<String>() } returns Mono.just(mockResponse)

                    val result = subject.getDirectGeocodingByCityName(CITY).block()

                    assertNotNull(result)
                    assertEquals(51.5074, result?.latitude)
                    assertEquals(-0.1278, result?.longitude)

                    verify { mockResponseSpec.bodyToMono<String>() }
                }
            }

            @Nested
            @DisplayName("if API calls is not 200 OK")
            inner class IfAPICallIsNot200OK {

                @Test
                fun `then log error message and return empty Mono`() {
                    val mockClientResponse = mockk<ClientResponse>()
                    val mockBodyMono = Mono.error<String>(OpenWeatherRestClientException())

                    every {
                        mockClientResponse.statusCode()
                    } returns HttpStatus.BAD_REQUEST // any HTTP status code except 200 OK

                    every {
                        mockClientResponse.bodyToMono<String>()
                    } returns mockBodyMono

                    every {
                        webClient.get()
                            .uri(DIRECT_GEOCODING_API_URL.format(CITY, API_KEY))
                            .accept(MediaType.APPLICATION_JSON)
                            .retrieve()
                            .onStatus(HttpStatusCode::is4xxClientError, any())
                            .bodyToMono<List<GetDirectGeocodingResponseDTO>>()
                    } returns Mono.error(OpenWeatherRestClientException())

                    val result = subject.getDirectGeocodingByCityName(CITY)

                    StepVerifier.create(result)
                        .expectError(OpenWeatherRestClientException::class.java)
                        .verify()
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