plugins {
    kotlin("jvm") version "1.5.20"
    `java-gradle-plugin`
    `maven-publish`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

gradlePlugin {
    plugins {
        create("simple-plugin") {
            id = "io.sannu.gradle.simple-plugin"
            implementationClass = "io.sannu.gradle.SimplePlugin"
        }
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-gradle-plugin:2.5.2")
    implementation("io.spring.gradle:dependency-management-plugin:1.0.11.RELEASE")
    implementation("org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:3.3")
}

publishing {

}