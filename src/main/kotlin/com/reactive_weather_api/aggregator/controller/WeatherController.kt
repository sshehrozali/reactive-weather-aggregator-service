package com.reactive_weather_api.aggregator.controller

import com.reactive_weather_api.aggregator.model.GetWeatherReportResponseDTO
import com.reactive_weather_api.aggregator.service.WeatherService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RequestMapping("api/v1")
@RestController
class WeatherController(
    private val weatherService: WeatherService
) {

    @GetMapping("/weather/report")
    fun getWeatherReportV1(@RequestParam location: String): Mono<ResponseEntity<GetWeatherReportResponseDTO>> {
        return Mono.just(
            ResponseEntity.ok(GetWeatherReportResponseDTO(location, "34", "Sunny"))
        )
    }
}