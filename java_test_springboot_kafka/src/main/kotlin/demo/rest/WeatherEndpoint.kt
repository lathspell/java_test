package demo.rest

import demo.kafka.WeatherGeneratorKafkaAdapter
import demo.service.WeatherService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class WeatherEndpoint(private val kafkaAdapter: WeatherGeneratorKafkaAdapter, private val weatherService: WeatherService) {

    private val log = LoggerFactory.getLogger(WeatherEndpoint::class.java)

    @PutMapping("/api/city/{city}/temp/{temp}")
    fun updateCity(@PathVariable("city") city: String, @PathVariable("temp") temp: Int) {
        log.info("updateCity($city, $temp)")
        kafkaAdapter.updateCity(city, temp)
    }

    @GetMapping("/api/cities")
    fun getAllCities(): Map<String, Int> {
        log.info("Getting weather reports for all cities")
        return weatherService.cities
    }
}