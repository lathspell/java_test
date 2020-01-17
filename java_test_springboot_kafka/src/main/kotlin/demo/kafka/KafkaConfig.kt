package demo.kafka

import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import io.confluent.kafka.serializers.KafkaAvroDeserializer;

/** Creates new topic if not already present. */
@Configuration
class KafkaConfig {

    private val log = LoggerFactory.getLogger(KafkaConfig::class.java)

    @Bean
    fun weatherTopic(): NewTopic {
        log.info("Creating new topic 'weather'")
        return NewTopic("weather", 3, 1)
    }

    @Bean
    fun cityWeatherKafkaConsumerTemplate(): KafkaTemplate<String, String> {
        return KafkaTemplate(DefaultKafkaProducerFactory(defaultConsumerConfig()))
    }

    private fun defaultConsumerConfig() = mapOf<String, Any>(
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest",
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to KafkaAvroDeserializer::class.java,
            ConsumerConfig.MAX_POLL_RECORDS_CONFIG to 500
            // Avro
            // KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG to true,
            // AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG to schemaRegistryUrl,
            // AbstractKafkaAvroSerDeConfig.AUTO_REGISTER_SCHEMAS to autoRegisterSchema
            )

}