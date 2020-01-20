package demo.service

import demo.kafka.generated.CityRain
import demo.kafka.CityRainKafkaAdapter
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CityRainService(private val kafkaAdapter: CityRainKafkaAdapter) {

    val cities = mutableMapOf<String, CityRain>()

    private val log = LoggerFactory.getLogger(CityRainService::class.java)

    fun update(city: String, rain: Int) {
        log.info("update($city, $rain)")
        val dataObj = CityRain(city, rain)
        kafkaAdapter.updateCity(city, dataObj)
    }

}