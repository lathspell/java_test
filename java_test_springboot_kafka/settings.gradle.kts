rootProject.name = "java_test_springboot_kafka"

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://dl.bintray.com/gradle/gradle-plugins") // für Avro
    }
}

include("kafka_only")
include("avro_only")
include("kafka_avro")
