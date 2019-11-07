package com.example.lib

import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.example.lib.data.HelloManData
import com.example.lib.extension.MyExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.internal.reflect.Instantiator
import org.gradle.invocation.DefaultGradle

class FirstPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        //注册transform
        def android = project.extensions.getByType(BaseExtension.class)
        android.registerTransform(new InjectTransform(project))

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
        project.afterEvaluate {
            addTasks(project, task)
        }
    }

    static void addTasks(Project project, WriteHelloManTask task) {
        MyExtension extension = project.extensions.getByType(MyExtension.class)
        task.mExtensionName = extension.extensionName
        task.mInnerExtension = extension.innerExtension
    }
}