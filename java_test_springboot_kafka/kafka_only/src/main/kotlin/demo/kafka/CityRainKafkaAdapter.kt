package demo.kafka

import demo.kafka.generated.CityRain
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class CityRainKafkaAdapter(private val cityRainKafkaTemplate: KafkaTemplate<String, CityRain>) {

    private val log = LoggerFactory.getLogger(CityRainKafkaAdapter::class.java)

    private val topic = "city_rain"

    init {
        log.info("init with kafkaTemplate=$cityRainKafkaTemplate")
    }

    fun updateCity(city: String, rain: CityRain) {
        log.info("updateCity($city, $rain)")
        cityRainKafkaTemplate.send(topic, city, rain)
    }

}
