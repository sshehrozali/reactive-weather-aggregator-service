package com.reactive_weather_api.aggregator.service

import com.reactive_weather_api.aggregator.model.WeatherReportDTO
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class WeatherService(
    private val asyncWeatherDataFetcher: AsyncWeatherDataFetcher,
    private val asyncWeatherDataAggregator: AsyncWeatherDataAggregator
) {

    fun buildWeatherReport(): Mono<WeatherReportDTO> {
//        asyncWeatherDataFetcher.fetchWeatherData()
//        asyncWeatherDataAggregator.aggregateWeatherData()

        return Mono.just(WeatherReportDTO("New York", "34", "Sunny"))
    }
}