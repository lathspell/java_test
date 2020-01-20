package demo.kafka

import demo.kafka.generated.CityRain
import demo.service.CityRainService
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class CityRainKafkaListener(private val rainService: CityRainService) {

    private val log = LoggerFactory.getLogger(CityRainKafkaListener::class.java)

    @KafkaListener(containerFactory = "cityRainKafkaListenerContainerFactory", topics = ["city_rain"])
    fun processMessage(record: ConsumerRecord<String, CityRain>) {
        log.info("Got record: $record")

        val key: String = record.key()
        val value: CityRain = record.value()
        rainService.cities[key] = value
    }

}