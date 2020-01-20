package demo.rest

import demo.service.CityRainService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CityRainEndpoint(private val cityRainService: CityRainService) {

    private val log = LoggerFactory.getLogger(CityRainEndpoint::class.java)

    @PutMapping("/api/city/{city}/rain/{rain}")
    fun updateCity(@PathVariable("city") city: String, @PathVariable("rain") rain: Int) {
        cityRainService.update(city, rain)
    }

    @GetMapping("/api/cities/rain")
    fun getAllCities(): Map<String, Int> {
        return cityRainService.cities.mapValues { it.value.getRain() }
    }
}