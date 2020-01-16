package demo.kafka

import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit.SECONDS

@Component
class WeatherGeneratorKafkaAdapter(private val kafkaTemplate: KafkaTemplate<String, String>) {

    private val log = LoggerFactory.getLogger(WeatherGeneratorKafkaAdapter::class.java)

    private val topic = "weather"

    init {
        log.info("init with kafkaTemplate=$kafkaTemplate")
    }

    fun updateCity(city: String, temp: Int) {
        log.info("updateCity($city, $temp)")
        kafkaTemplate.send(topic, city, temp.toString())
    }

}
