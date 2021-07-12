package io.sannu.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class SimplePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.tasks.create("SampleTask") {
            println("Hello there!")
        }
    }

}