package com.reactive_weather_api.aggregator

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.kotlin.core.publisher.toMono
import reactor.kotlin.test.test
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@DisplayName("GET v1/weather/report API test")
class GetWeatherReportV1APITest(
    @Autowired private val webTestClient: WebTestClient
) {

    private val location = "New York"

    @Test
    fun `should return weather report for a city requested by user`() {
        val response = webTestClient
            .get()
            .uri("v1/weather/report?location=$location")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBody(GetWeatherReportV1ResponseDTO::class.java)
            .returnResult()
            .responseBody
            .toMono()

        response.test()
            .consumeNextWith { dto ->
                assertEquals(location, dto.location)
            }

    }

}