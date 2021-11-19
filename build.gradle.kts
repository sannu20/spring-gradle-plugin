import java.io.ByteArrayOutputStream

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.6.0"
    id("java-gradle-plugin")
    id("maven-publish")
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

ext {
    val byteOut = ByteArrayOutputStream();
    project.exec {
        commandLine = "git rev-parse --short HEAD".split(" ");
        standardOutput = byteOut
    }
    ext.set("commitHash", String(byteOut.toByteArray()).trim())
}

gradlePlugin {
    plugins {
        create("simple-plugin") {
            id = "io.sannu.gradle.simple-plugin"
            implementationClass = "io.sannu.gradle.SimplePlugin"
        }
        create("spring-plugin") {
            id = "io.sannu.gradle.spring-plugin"
            implementationClass = "io.sannu.gradle.SpringPlugin"
        }
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-gradle-plugin:2.6.0")
    implementation("io.spring.gradle:dependency-management-plugin:1.0.11.RELEASE")
    implementation("org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:3.3")
}

publishing {
    publications {
        version = "${project.version}-${project.ext.get("commitHash")}"
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/sannu20/spring-gradle-plugin")

            credentials {
                username = System.getenv("PUBLISH_USER")
                password = System.getenv("PUBLISH_TOKEN")
            }
        }
    }
}