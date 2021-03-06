package com.example.lint;

import com.android.tools.lint.detector.api.JavaContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.uast.UClass;
import org.jetbrains.uast.UMethod;
import org.jetbrains.uast.visitor.AbstractUastVisitor;

import static com.example.lint.NameDetector.NAME_ISSUE;

class NameVisitor extends AbstractUastVisitor {
    JavaContext context;

    UClass uClass;

    public NameVisitor(JavaContext context, UClass node) {
        this.context = context;
        this.uClass = node;
    }

    @Override
    public boolean visitClass(@NotNull UClass node) {
        //获取当前类名
        char beginChar = node.getName().charAt(0);
        int code = beginChar;
        //如果类名不是大写字母，则触碰Issue，lint工具提示问题
        if (97 < code && code < 122) {
            context.report(NAME_ISSUE, context.getNameLocation(node),
                    "the  name of class must start with uppercase:" + node.getName());
            //返回true表示触碰规则，lint提示该问题；false则不触碰
            return true;
        }

        return super.visitClass(node);
    }

    @Override
    public boolean visitMethod(@NotNull UMethod node) {
        //当前方法不是构造方法
        if (!node.isConstructor()) {
            System.out.println("visitMethod = " + node.getName());
            char beginChar = node.getName().charAt(0);
            int code = beginChar;
            //当前方法首字母是大写字母，则报Issue
            if (65 < code && code < 90) {
                context.report(NAME_ISSUE, context.getLocation(node),
                        "the method must start with lowercase:" + node.getName());
                //返回true表示触碰规则，lint提示该问题；false则不触碰
                return true;
            }
        }
        return super.visitMethod(node);
    }
}
