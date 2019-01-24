package com.data;

import com.squareup.javawriter.JavaWriter;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import javax.tools.*;
import java.io.IOException;
import java.io.Writer;
import java.util.*;


/**
 * @Author: fangmingxing
 * @Date: 2019-01-21 22:57
 */
@SupportedAnnotationTypes("com.data.Data")
public class DataProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "-----开始自动生成源代码");
        try {
            // 返回被注释的节点
            Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Data.class);
            for (Element e : elements) {
                // 如果注释在类上
                if (e.getKind() == ElementKind.CLASS && e instanceof TypeElement) {
                    TypeElement element = (TypeElement) e;
                    // 类的全限定名
                    String classAllName = element.getQualifiedName().toString() + "New";
                    // 返回类内的所有节点
                    List<? extends Element> enclosedElements = element.getEnclosedElements();
                    // 保存字段的集合
                    Map<Name, TypeMirror> fieldMap = new HashMap<>();
                    for (Element ele : enclosedElements) {
                        if (ele.getKind() == ElementKind.FIELD) {
                            //字段的类型
                            TypeMirror typeMirror = ele.asType();
                            //字段的名称
                            Name simpleName = ele.getSimpleName();
                            fieldMap.put(simpleName, typeMirror);
                        }
                    }
                    // 生成一个Java源文件
                    JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(getClassName(classAllName));
                    // 写入代码
                    createSourceFile(classAllName, fieldMap, sourceFile.openWriter());
                } else {
                    return false;
                }
            }
        } catch (IOException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
        }
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "-----完成自动生成源代码");
        return true;
    }

    private String getClassName(String name) {
        String result = name;
        if (name.contains(".")) {
            result = name.substring(name.lastIndexOf(".") + 1);
        }
        return result;
    }

    private String getPackage(String name) {
        String result = name;
        if (name.contains(".")) {
            result = name.substring(0, name.lastIndexOf("."));
        } else {
            result = "";
        }
        return result;
    }

    private String humpString(String name) {
        String result = name;
        if (name.length() == 1) {
            result = name.toUpperCase();
        }
        if (name.length() > 1) {
            result = name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        return result;
    }

    private void createSourceFile(String className, Map<Name, TypeMirror> fieldMap, Writer writer) throws IOException {
        String[] errorPrefixes = {"get", "set", "is", "has"};
        for (Map.Entry<Name, TypeMirror> map : fieldMap.entrySet()) {
            String name = map.getKey().toString();
            for (String prefix : errorPrefixes) {
                if (name.startsWith(prefix)) {
                    throw new RuntimeException("Properties do not begin with 'get'、'set'、'is'、'has' in " + name);
                }
            }
        }
        // 生成源代码
        JavaWriter jw = new JavaWriter(writer);
        jw.emitPackage(getPackage(className));
        jw.beginType(getClassName(className), "class", EnumSet.of(Modifier.PUBLIC));
        jw.emitEmptyLine();
        for (Map.Entry<Name, TypeMirror> map : fieldMap.entrySet()) {
            String name = map.getKey().toString();
            String type = map.getValue().toString();
            //字段
            jw.emitField(type, name, EnumSet.of(Modifier.PRIVATE));
            jw.emitEmptyLine();
        }
        for (Map.Entry<Name, TypeMirror> map : fieldMap.entrySet()) {
            String name = map.getKey().toString();
            String type = map.getValue().toString();
            String prefixGet = "get";
            String prefixSet = "set";
            if (type.equals("java.lang.Boolean")) {
                prefixGet = "has";
                prefixSet = "is";
            }
            //getter
            jw.beginMethod(type, prefixGet + humpString(name), EnumSet.of(Modifier.PUBLIC))
                    .emitStatement("return " + name)
                    .endMethod();
            jw.emitEmptyLine();
            //setter
            jw.beginMethod("void", prefixSet + humpString(name), EnumSet.of(Modifier.PUBLIC), type, name)
                    .emitStatement("this." + name + " = " + name)
                    .endMethod();
            jw.emitEmptyLine();
        }
        jw.endType().close();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
