package demo.kafka

import demo.service.WeatherService
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class WeatherReportKafkaAdapter(private val weatherService: WeatherService) {

    private val log = LoggerFactory.getLogger(WeatherReportKafkaAdapter::class.java)

    @KafkaListener(beanRef = "cityWeatherKafkaConsumerTemplate", topics = ["weather"], groupId = "java_test_springboot_kafka")
    fun processMessage(record: ConsumerRecord<String, String>) {
        log.info("Got $record")

        val key = record.key()
        val value = record.value()
        weatherService.cities[key] = value.toInt()
    }

}