plugins {
    java
    application
    id("com.github.ben-manes.versions") version "0.21.0"                // https://github.com/ben-manes/gradle-versions-plugin for ":dependencyUpdates"
}

repositories {
    mavenCentral()
}

dependencies {
    val kotlinVersion = "1.3.31"
    val lombokVersion = "1.18.6"

    // Lombok
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    compileOnly("org.projectlombok:lombok:$lombokVersion")

    // Kotlin Stdlib
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")

    // Tests
    testImplementation("junit:junit:4.12")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

application {
    mainClassName = "de.lathspell.test.App"
}
