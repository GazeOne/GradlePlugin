package com.example.lib

import org.gradle.api.Plugin
import org.gradle.api.Project

class RunPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.android.applicationVariants.all { variant ->
            if (variant.getInstallProvider()) {
                project.tasks.create(name: "run${variant.name.capitalize()}",
                        dependsOn: variant.getInstallProvider()) {}
            }
        }
    }
}