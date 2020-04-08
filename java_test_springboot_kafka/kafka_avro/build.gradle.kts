import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	// Kotlin
	kotlin("jvm") version "1.3.61"
	// Spring
	kotlin("plugin.spring") version "1.3.61"
	id("org.springframework.boot") version "2.2.2.RELEASE"
	id("io.spring.dependency-management") version "1.0.8.RELEASE"
	// Avro
	// See settings.gradle.kts for plugin repos.
	// See https://github.com/davidmc24/gradle-avro-plugin for compatibility matrix with Avro library dependency!
	id("com.commercehub.gradle.plugin.avro") version "0.16.0"
}

group = "de.lathspell"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

val developmentOnly by configurations.creating
configurations {
	runtimeClasspath {
		extendsFrom(developmentOnly)
	}
}

repositories {
	jcenter()
	mavenCentral()
	maven("https://packages.confluent.io/maven")
}

dependencies {
	// Kotlin
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	// SpringBoot
	implementation("org.springframework.boot:spring-boot-starter-web")
	developmentOnly("org.springframework.boot:spring-boot-devtools")

	// Tests
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}

	// JSON
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	// Kafka
	implementation("org.springframework.kafka:spring-kafka")
	testImplementation("org.springframework.kafka:spring-kafka-test")

	// Avro (De-/Serialization specification in JSON)
	implementation("org.apache.avro:avro:1.8.2")
	implementation("io.confluent:kafka-avro-serializer:5.2.1")
	// implementation("io.confluent:kafka-streams-serde:5.3.0")
}

avro {
	// see https://github.com/davidmc24/gradle-avro-plugin
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}
