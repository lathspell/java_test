package demo.kafka

import demo.service.CityTempService
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class CityTempKafkaListener(private val cityTempService: CityTempService) {

    private val log = LoggerFactory.getLogger(CityTempKafkaListener::class.java)

    @KafkaListener(containerFactory = "cityTempKafkaListenerContainerFactory", topics = ["city_temp"])
    fun processMessage(record: ConsumerRecord<String, String>) {
        log.info("Got record: $record")

        val key: String = record.key()
        val value: Int = record.value().toInt()
        cityTempService.cities[key] = value
    }

}