package demo.kafka

import com.fasterxml.jackson.databind.SerializationFeature
import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory


@Configuration
class CommonKafkaConfig {

    /** Es muss eine ConsumerFactory mit folgender Signatur geben, sonst kommt SpringBoot Autoconfig durcheinander. */
    @Bean
    fun commonKafkaConsumerFactory(): ConsumerFactory<Any, Any> {
        return DefaultKafkaConsumerFactory<Any, Any>(mapOf(
                CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG to listOf("localhost:9192"),
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest",
                ConsumerConfig.MAX_POLL_RECORDS_CONFIG to 500,
                ConsumerConfig.GROUP_ID_CONFIG to "java_test_springboot_kafka"
        ))
    }

}