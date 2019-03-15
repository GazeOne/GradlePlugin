package com.example.lib.data

import org.gradle.api.tasks.Input

class HelloManData {
    private String name
    private int age

    HelloManData(String name, int age) {
        this.name = name
        this.age = age
    }

    @Input
    String getName() {
        return name
    }

    @Input
    int getAge() {
        return age
    }

    @Override
    String toString() {
        return "Hello " + age + " years old " + name + " !"
    }
}