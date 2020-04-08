import org.apache.avro.compiler.specific.SpecificCompiler.DateTimeLogicalTypeImplementation.JSR310
import org.apache.avro.compiler.specific.SpecificCompiler.FieldVisibility.PRIVATE
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	// Kotlin
	kotlin("jvm") version "1.3.71"
	// Spring
	kotlin("plugin.spring") version "1.3.71"
	id("org.springframework.boot") version "2.2.6.RELEASE"
	id("io.spring.dependency-management") version "1.0.8.RELEASE"
	// Avro
	// See settings.gradle.kts for plugin repos.
	// See https://github.com/davidmc24/gradle-avro-plugin for compatibility matrix with Avro library dependency!
	id("com.commercehub.gradle.plugin.avro") // version "0.19.1" <- see top-level build.gradle.kts for version!
}

group = "de.lathspell"

val developmentOnly: Configuration by configurations.creating
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
	implementation("org.springframework.boot:spring-boot-starter")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}

	// JSON
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	// Avro (De-/Serialization specification in JSON)
	implementation("org.apache.avro:avro:1.9.2")
}

avro {
	// see https://github.com/davidmc24/gradle-avro-plugin
	setFieldVisibility(PRIVATE) // getter/setters or public attributes?
	setCreateOptionalGetters(true) // additional `fun getOptionalFoo(): Optional<String>` methods
	setDateTimeLogicalType(JSR310) // default
	setEnableDecimalLogicalType(true) // default
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
