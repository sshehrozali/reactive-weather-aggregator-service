package com.reactive_weather_api.aggregator.service

import com.reactive_weather_api.aggregator.OpenWeatherRestClient
import com.reactive_weather_api.aggregator.exception.OpenWeatherRestClientException
import com.reactive_weather_api.aggregator.model.OpenWeatherForecastReport
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class AsyncWeatherDataFetcher(
    private val openWeatherRestClient: OpenWeatherRestClient
) {

    fun fetchFromOpenWeather(city: String): Mono<OpenWeatherForecastReport> {
        return openWeatherRestClient
            .getDirectGeocodingByCityName(city)
            .flatMap { it ->
                if (it.isEmpty()) {
                    Mono.empty<OpenWeatherForecastReport>()
                } else {
                    val city = it.first()
                    openWeatherRestClient.getWeatherData(city.lat, city.lon)
                        .flatMap { weatherData ->
                            Mono.just(
                                OpenWeatherForecastReport(
                                    weatherData.hourly.first().weather.first().description,
                                    weatherData.hourly.first().temp,
                                    weatherData.hourly.first().pressure,
                                    weatherData.hourly.first().humidity,
                                    weatherData.hourly.first().humidity
                                )
                            )
                        }
                }
            }
    }
}