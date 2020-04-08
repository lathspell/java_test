package demo.kafka

import demo.kafka.generated.CityRain
import io.confluent.kafka.serializers.*
import org.apache.avro.specific.SpecificDatumReader
import org.apache.avro.specific.SpecificDatumWriter
import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
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

@Configuration
class CityRainKafkaConfig {

    private val log = LoggerFactory.getLogger(CityRainKafkaConfig::class.java)

    /** Creates new topic if not already present. */
    @Bean
    fun cityRainTopic(): NewTopic {
        log.info("Creating new topic 'city_rain'")
        return NewTopic("city_rain", 3, 1)
    }

    @Bean
    fun cityRainKafkaTemplate(): KafkaTemplate<String, CityRain> {
        return KafkaTemplate(DefaultKafkaProducerFactory(mapOf(
                AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG to "http://localhost:8085",
                CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG to listOf("localhost:9192"),
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to KafkaAvroSerializer::class.java
        )))
    }

    @Bean
    fun cityRainKafkaConsumerFactory(): ConsumerFactory<String, CityRain> {
        return DefaultKafkaConsumerFactory<String, CityRain>(mapOf(
                AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG to "http://localhost:8085",
                KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG to true,
                CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG to listOf("localhost:9192"),
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "latest",
                ConsumerConfig.MAX_POLL_RECORDS_CONFIG to 500,
                ConsumerConfig.GROUP_ID_CONFIG to "java_test_springboot_kafka",
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to KafkaAvroDeserializer::class.java
        ))
    }

    @Bean
    fun cityRainKafkaListenerContainerFactory(): KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, CityRain>> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, CityRain>()
        factory.consumerFactory = cityRainKafkaConsumerFactory()
        return factory
    }

}