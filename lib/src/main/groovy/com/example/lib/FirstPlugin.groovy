package com.example.lib

import org.gradle.api.Plugin
import org.gradle.api.Project

class FirstPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        System.out.println("init----")
    }
}