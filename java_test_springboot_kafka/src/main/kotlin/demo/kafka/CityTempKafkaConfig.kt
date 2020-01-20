package demo.kafka

import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.IntegerDeserializer
import org.apache.kafka.common.serialization.IntegerSerializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer


/**
 * Kafka Configuration für "City Temperature".
 *
 * Man könnte den Wert auch mit einem IntegerSerializer kodieren aber dann kann man die Werte weder mit
 * KafkaHQ oder kafkacat ansehen noch später Filter dazwischen hängen. Fremde Clients haben dann auch eher
 * keine Chance zu erkennen, was für Daten man benutzt.
 */
@Configuration
class CityTempKafkaConfig {

    private val log = LoggerFactory.getLogger(CityTempKafkaConfig::class.java)

    /** Creates new topic if not already present. */
    @Bean
    fun cityTempTopic(): NewTopic {
        log.info("Creating new topic 'city_temp'")
        return NewTopic("city_temp", 3, 1)
    }

    @Bean
    fun cityTempKafkaTemplate(): KafkaTemplate<String, String> {
        return KafkaTemplate(DefaultKafkaProducerFactory(mapOf(
                CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG to listOf("localhost:9192"),
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java
        )))
    }

    @Bean
    fun cityTempKafkaConsumerFactory(): ConsumerFactory<String, String> {
        return DefaultKafkaConsumerFactory<String, String>(mapOf(
                CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG to listOf("localhost:9192"),
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "latest",
                ConsumerConfig.MAX_POLL_RECORDS_CONFIG to 500,
                ConsumerConfig.GROUP_ID_CONFIG to "java_test_springboot_kafka",
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java
        ))
    }

    @Bean
    fun cityTempKafkaListenerContainerFactory(): KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, String>()
        factory.consumerFactory = cityTempKafkaConsumerFactory()
        return factory
    }

}