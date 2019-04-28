plugins {
    java
    id("org.springframework.boot") version "2.1.4.RELEASE"              // https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/html/
    id("com.bmuschko.docker-spring-boot-application") version "4.8.0"   // https://bmuschko.github.io/gradle-docker-plugin for ":dockerBuildImage"
    id("com.github.ben-manes.versions") version "0.21.0"                // https://github.com/ben-manes/gradle-versions-plugin for ":dependencyUpdates"
}

apply(plugin = "io.spring.dependency-management")


repositories {
    mavenCentral()
}

dependencies {
    val lombokVersion = "1.18.6"

    // SpringBoot
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.springframework.boot:spring-boot-devtools")

    // Lombok
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    compileOnly("org.projectlombok:lombok:$lombokVersion")

    // Test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")
    testCompileOnly("org.projectlombok:lombok:$lombokVersion")
    testCompile("org.testcontainers:testcontainers:1.11.2")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

docker {
    springBootApplication {
        baseImage.set("openjdk:8-jdk-alpine")
        tag.set("de.lathspell.test/java_test_springboot_docker2:latest")
    }
}
