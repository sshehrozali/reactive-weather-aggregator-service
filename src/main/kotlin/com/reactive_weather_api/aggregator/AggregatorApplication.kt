package com.reactive_weather_api.aggregator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.reactive.config.EnableWebFlux

@EnableWebFlux
@SpringBootApplication
class AggregatorApplication

fun main(args: Array<String>) {
	runApplication<AggregatorApplication>(*args)
}
