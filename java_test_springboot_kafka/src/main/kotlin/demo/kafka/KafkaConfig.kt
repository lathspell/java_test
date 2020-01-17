package demo.kafka

import org.apache.kafka.clients.admin.NewTopic
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/** Creates new topic if not already present. */
@Configuration
class KafkaConfig {

    private val log = LoggerFactory.getLogger(KafkaConfig::class.java)

    @Bean
    fun weatherTopic(): NewTopic
    {
        log.info("Creating new topic 'weather'")
        return NewTopic("weather", 3, 1)
    }

}