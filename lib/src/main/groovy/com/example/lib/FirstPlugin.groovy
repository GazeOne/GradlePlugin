package com.example.lib

import com.example.lib.data.HelloManData
import com.example.lib.extension.MyExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.internal.reflect.Instantiator
import org.gradle.invocation.DefaultGradle

class FirstPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        File folder = new File("D:/workspace")
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdirs()
        }
        WriteHelloManTask task = project.getTasks().create("writeHello", WriteHelloManTask.class)
        task.setFileName("HelloWorld.txt")
        task.setHelloMan(new HelloManData("Jim", 19))
        task.setTargetDirectory(folder)
        task.setGroup("hello")
        Instantiator instantiator = ((DefaultGradle) project.getGradle()).getServices().get(
                Instantiator.class)
        //create方法的第三个参数是MyExtension的构造参数
        MyExtension mMyExtension = project.getExtensions().create("myExtension", MyExtension.class,
                instantiator)

//        MyExtension mMyExtension = project.getExtensions().create("myExtension", MyExtension.class)
    }
}