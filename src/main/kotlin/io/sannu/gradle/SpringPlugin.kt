package io.sannu.gradle

import io.spring.gradle.dependencymanagement.DependencyManagementPlugin
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.springframework.boot.gradle.plugin.SpringBootPlugin

class SpringPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {

            pluginManager.apply {
                SpringBootPlugin()
                DependencyManagementPlugin()
            }
            extensions.configure<DependencyManagementExtension>("springBoot") {
                it.imports { import ->
                    import.mavenBom("org.springframework.boot:spring-boot-dependencies:2.5.2")
                    import.mavenBom("org.springframework.cloud:spring-cloud-dependencies:Hoxton.SR12")
                }
            }
        }
    }

}