package com.example.lib.extension

import org.gradle.api.Action
import org.gradle.internal.reflect.Instantiator

class MyExtension {
    private String mExtensionName
    private InnerExtension mInnerExtension

    MyExtension(Instantiator instantiator) {
        mInnerExtension = instantiator.newInstance(InnerExtension.class);
    }

    String getExtensionName() {
        return mExtensionName
    }

    void setExtensionName(String extensionName) {
        this.mExtensionName = extensionName
    }

    InnerExtension getInnerExtension() {
        return mInnerExtension
    }

    void setInnerExtension(InnerExtension innerExtension) {
        mInnerExtension = innerExtension
    }

    void innerExtension(Action<InnerExtension> action) {
        //这里方法名写成setInnerExtension,gradle也是可以会调用的，
        //但是为了防止意外，还是写出我们想要的名字
        action.execute(mInnerExtension)
    }
}