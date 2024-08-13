package com.reactive_weather_api.aggregator

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RequestMapping("api/v1")
@RestController
class WeatherController {

    @GetMapping("/weather/report")
    fun getWeatherReportV1(@RequestParam location: String): ResponseEntity<Mono<GetWeatherReportV1ResponseDTO>> {
        return ResponseEntity.ok(Mono.just(GetWeatherReportV1ResponseDTO(location, "34", "")))
    }
}