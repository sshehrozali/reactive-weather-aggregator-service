package com.reactive_weather_api.aggregator

import com.fasterxml.jackson.databind.ObjectMapper
import com.reactive_weather_api.aggregator.model.GetWeatherReportResponseDTO
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@DisplayName("GET v1/weather/report API test")
class GetWeatherReportV1APITest(
    @Autowired private val webTestClient: WebTestClient,
    @Autowired private val objectMapper: ObjectMapper
) {

    private val location = "New York"
    private val temperature = "34"

    @Test
    fun `should return weather report for a city requested by user`() {
        val response = webTestClient
            .get()
            .uri("/api/v1/weather/report?location=$location")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        val successResponseDTO = objectMapper.readValue(response, GetWeatherReportResponseDTO::class.java)

        assertNotNull(successResponseDTO)
        assertEquals(location, successResponseDTO.location)
        assertEquals(temperature, successResponseDTO.temperature)
    }

}