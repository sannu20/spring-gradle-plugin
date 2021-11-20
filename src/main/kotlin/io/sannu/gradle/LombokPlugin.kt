package io.sannu.gradle

import org.apache.commons.io.IOUtils
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

            val outStream = FileOutputStream(yourFile, false);
            outStream.write(IOUtils.toByteArray(this.javaClass.getClassLoader().getResourceAsStream("lombok.config")));
        }
    }

}