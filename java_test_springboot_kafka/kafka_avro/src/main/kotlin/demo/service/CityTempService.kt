package demo.service

import demo.kafka.CityTempKafkaAdapter
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CityTempService(private val kafkaAdapter: CityTempKafkaAdapter) {

    val cities = mutableMapOf<String, Int>()

    private val log = LoggerFactory.getLogger(CityTempService::class.java)

    fun update(city: String, temp: Int) {
        log.info("update($city, $temp)")
        kafkaAdapter.updateCity(city, temp)
    }

}