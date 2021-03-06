import com.bmuschko.gradle.docker.DockerSpringBootApplication
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    java

    id("org.springframework.boot") version "2.1.4.RELEASE"              // https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/html/
    id("com.gorylenko.gradle-git-properties") version "2.0.0"           // https://github.com/n0mer/gradle-git-properties
    id("com.bmuschko.docker-spring-boot-application") version "4.8.0"   // https://bmuschko.github.io/gradle-docker-plugin for ":dockerBuildImage"

    id("com.github.ben-manes.versions") version "0.21.0"                // https://github.com/ben-manes/gradle-versions-plugin for ":dependencyUpdates"
 }

apply(plugin = "io.spring.dependency-management")

repositories {
    jcenter()
}

dependencies {
    val lombokVersion = "1.18.8"

    // Lombok
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    compileOnly("org.projectlombok:lombok:$lombokVersion")

    // SpringBoot
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    compileOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // Database
    implementation("com.h2database:h2:1.4.199")

    // Tracing
    implementation("io.opentracing.contrib:opentracing-spring-jaeger-web-starter:1.0.3")

}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.test {
    maxParallelForks = 4
    testLogging.minGranularity = 3
    testLogging.events = setOf(TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED)
}

docker {
    springBootApplication {
        baseImage.set("openjdk:8-jdk-alpine")
        tag.set("de.lathspell.test/app1:latest")
    }
}
