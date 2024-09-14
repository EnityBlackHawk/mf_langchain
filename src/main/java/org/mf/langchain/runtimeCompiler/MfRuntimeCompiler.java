package org.mf.langchain.runtimeCompiler;

import io.hypersistence.utils.common.ClassLoaderUtils;

import javax.annotation.processing.Processor;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;
import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URLClassLoader;
import java.util.*;
import lombok.launch.*;
import org.springframework.data.mongodb.repository.support.MongoAnnotationProcessor;

public class MfRuntimeCompiler {

    public static Map<String, Class<?>> compile(Map<String, String> sources) throws Exception {

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        MfFileManager fileManager = new MfFileManager(compiler.getStandardFileManager(null, null, null));
        List<JavaFileObject> files = new ArrayList<>();

        for (String className : sources.keySet()) {
            JavaFileObject sourceObj = new MfSourceFromString(className, sources.get(className));
            files.add(sourceObj);
        }

        var classPath = System.getProperty("java.class.path") +
                File.pathSeparator + "/home/luan/jars/lombok.jar" +
                File.pathSeparator + "/home/luan/jars/spring-data-commons-3.3.4.jar" +
                File.pathSeparator + "/home/luan/jars/spring-data-mongodb-4.3.4.jar";
        Iterable<String> options = List.of("-classpath", classPath, "--add-exports", "jdk.compiler/com.sun.tools.javac.processing=ALL-UNNAMED");


        //Class<?> cLombokProcessor = Class.forName("lombok.launch.AnnotationProcessorHider$AnnotationProcessor");
        //Processor lomProcessor = (Processor) cLombokProcessor.getDeclaredConstructor().newInstance();
        Processor mongoAnnotationProcessor = new MongoAnnotationProcessor();
        Iterable<Processor> processors = List.of(mongoAnnotationProcessor);

        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, options, null, files);
        task.setProcessors(processors);
        boolean result = task.call();
        if (!result) {
            throw new RuntimeException("Compilation failed.");
        }
        ClassLoader classLoader = fileManager.getClassLoader(null);

        Map<String, Class<?>> classes = new HashMap<>();

        for(String className : sources.keySet()) {
            classes.put(className, classLoader.loadClass(className));
        }

        return classes;
    }

    public static Class<?> compile(String className, String source) throws Exception {

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        MfFileManager fileManager = new MfFileManager(compiler.getStandardFileManager(null, null, null));

        JavaFileObject sourceObj = new MfSourceFromString(className, source);
        List<JavaFileObject> files = List.of(sourceObj);

        var classPath = System.getProperty("java.class.path") + File.pathSeparator + "/home/luan/jars/lombok.jar";
        Iterable<String> options = List.of("-classpath", "/home/luan/jars/lombok.jar");

        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, options, null, files);
        boolean result = task.call();
        if (!result) {
            throw new RuntimeException("Compilation failed.");
        }
        ClassLoader classLoader = fileManager.getClassLoader(null);
        return classLoader.loadClass(className);
    }
}
