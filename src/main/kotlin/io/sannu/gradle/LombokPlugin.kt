package io.sannu.gradle

import org.apache.commons.io.IOUtils.toByteArray
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File
import java.io.FileOutputStream

class LombokPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            // add lombok dependencies
            dependencies.add("compileOnly", "org.projectlombok:lombok")
            dependencies.add("annotationProcessor", "org.projectlombok:lombok")
            configurations.getByName("compileOnly").extendsFrom(configurations.getByName("annotationProcessor"))

            // copy lombok configuration file to project root
            val yourFile = File("${project.getRootDir()}/lombok.config");
            yourFile.getParentFile().mkdirs();
            yourFile.createNewFile();

            FileOutputStream(yourFile, false).use { writer ->
                writer.write(toByteArray(LombokPlugin::class.java.classLoader.getResourceAsStream("lombok.config")))
            }
        }
    }

}