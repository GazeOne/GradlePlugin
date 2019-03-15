package com.example.lib

import com.example.lib.data.HelloManData
import com.example.lib.extension.InnerExtension
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.Nested
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

class WriteHelloManTask extends DefaultTask {
    private HelloManData helloMan
    private File targetDirectory
    private String fileName
    String mExtensionName
    InnerExtension mInnerExtension

    @Nested
    HelloManData getHelloMan() {
        return helloMan
    }

    @OutputFile
    File getTargetFile() {
        return new File(targetDirectory, fileName)
    }

    @Input
    String getFileName() {
        return fileName
    }

    @InputDirectory
    File getTargetDirectory() {
        return targetDirectory
    }

    @TaskAction
    void writeObject() {
        File targetFile = new File(targetDirectory, fileName)
        System.out.println(mExtensionName + "------" + mInnerExtension.extensionName)
        try {
            FileOutputStream fos = new FileOutputStream(targetFile)
            byte[] bytes = helloMan.toString().getBytes()
            fos.write(bytes)
            fos.flush()
            fos.close()
        } catch (FileNotFoundException e) {
            e.printStackTrace()
        } catch (IOException e) {
            e.printStackTrace()
        }
    }

    void setHelloMan(HelloManData helloMan) {
        this.helloMan = helloMan
    }

    void setTargetDirectory(File targetDirectory) {
        this.targetDirectory = targetDirectory
    }

    void setFileName(String fileName) {
        this.fileName = fileName
    }
}