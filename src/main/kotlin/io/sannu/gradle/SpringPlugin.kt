package io.sannu.gradle

import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension

class SpringPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            pluginManager.apply("idea")
            pluginManager.apply("java")
            extensions.configure<JavaPluginExtension>("java") {
                it.sourceCompatibility = JavaVersion.VERSION_11
                it.targetCompatibility = JavaVersion.VERSION_11
                it.withJavadocJar()
                it.withSourcesJar()
            }

            project.plugins.apply("org.springframework.boot")
            project.plugins.apply("io.spring.dependency-management")
            extensions.configure<DependencyManagementExtension>("dependencyManagement") {
                it.imports { import ->
                    import.mavenBom("org.springframework.boot:spring-boot-dependencies:2.5.7")
                    import.mavenBom("org.springframework.cloud:spring-cloud-dependencies:2020.0.4")
                }
            }
        }
    }

}