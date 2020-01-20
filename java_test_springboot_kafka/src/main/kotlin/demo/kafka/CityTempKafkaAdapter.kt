package demo.kafka

import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class CityTempKafkaAdapter(private val cityTempKafkaTemplate: KafkaTemplate<String, String>) {

    private val log = LoggerFactory.getLogger(CityTempKafkaAdapter::class.java)

    private val topic = "city_temp"

    init {
        log.info("init with kafkaTemplate=$cityTempKafkaTemplate")
    }

    fun updateCity(city: String, temp: Int) {
        log.info("updateCity($city, $temp)")
        cityTempKafkaTemplate.send(topic, city, temp.toString())
    }

}
