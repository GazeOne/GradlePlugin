package com.example.lint;

import com.android.tools.lint.client.api.UElementHandler;
import com.android.tools.lint.detector.api.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.uast.UClass;
import org.jetbrains.uast.UElement;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

public class NameDetector extends Detector implements Detector.UastScanner {

    static final Issue NAME_ISSUE = Issue.create(
            "NameWarning",
            "命名规范警告",
            "使用驼峰命名法",
            Category.USABILITY,
            5,
            Severity.WARNING,
            new Implementation(NameDetector.class, EnumSet.of(Scope.CLASS_FILE))
    );

    //返回我们所有感兴趣的类，即返回的类都被会检查
    @Nullable
    @Override
    public List<Class<? extends UElement>> getApplicableUastTypes() {
        return Collections.<Class<? extends UElement>>singletonList(UClass.class);
    }

    //重写该方法，创建自己的处理器
    @Nullable
    @Override
    public UElementHandler createUastHandler(@NotNull final JavaContext context) {
        return new UElementHandler() {
            @Override
            public void visitClass(@NotNull UClass node) {
                node.accept(new NameVisitor(context, node));
            }
        };
    }
}
