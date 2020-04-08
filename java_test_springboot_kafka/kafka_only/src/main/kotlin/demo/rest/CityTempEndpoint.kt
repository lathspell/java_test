package demo.rest

import demo.service.CityTempService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CityTempEndpoint(private val cityTempService: CityTempService) {

    private val log = LoggerFactory.getLogger(CityTempEndpoint::class.java)

    @PutMapping("/api/city/{city}/temp/{temp}")
    fun updateCity(@PathVariable("city") city: String, @PathVariable("temp") temp: Int) {
        cityTempService.update(city, temp)
    }

    @GetMapping("/api/cities/temp")
    fun getAllCities(): Map<String, Int> {
        return cityTempService.cities
    }
}