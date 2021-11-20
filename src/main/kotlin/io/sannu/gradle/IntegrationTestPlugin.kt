package io.sannu.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.plugins.ide.idea.model.IdeaModel

class IntegrationTestPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {

            project.extensions.configure<JavaPluginExtension>("java") { java ->
                java.sourceSets.create("integrationTest") { integrationTest ->
                    /*
                        configure integration tests to include main source and dependencies
                        don't want to add main source as a gradle dependency
                    */
                    integrationTest.compileClasspath += java.sourceSets.getByName("main").output;
                    integrationTest.runtimeClasspath += java.sourceSets.getByName("main").output;

                    project.configurations.getByName(integrationTest.implementationConfigurationName)
                        .extendsFrom(project.configurations.getByName("implementation"))
                    project.configurations.getByName(integrationTest.runtimeOnlyConfigurationName)
                        .extendsFrom(project.configurations.getByName("runtimeOnly"))

                    /*
                        Set integration test source set as a test source in idea
                     */
                    project.extensions.configure<IdeaModel>("idea") { idea ->
                        idea.module.testSourceDirs.addAll(integrationTest.java.srcDirs)
                        idea.module.testResourceDirs.addAll(integrationTest.resources.srcDirs)
                    }

                    /*
                        Create task to run integartion tests
                     */
                    project.tasks.register("integrationTest", Test::class.java) { task ->
                        task.description = "Runs integration tests."
                        task.group = "verification"
                        task.useJUnitPlatform()

                        task.testClassesDirs = integrationTest.output.classesDirs
                        task.classpath = integrationTest.runtimeClasspath
                    }

                    /*
                        Run integration tests together with check
                        Check task should run all tests, including integration tests
                     */
                    project.tasks.getByName("check") { check ->
                        check.dependsOn(project.tasks.getByName("integrationTest"))
                    }
                }

            }
        }
    }

}