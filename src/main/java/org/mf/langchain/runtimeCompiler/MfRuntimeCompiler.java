package org.mf.langchain.runtimeCompiler;

import io.hypersistence.utils.common.ClassLoaderUtils;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.List;

public class MfRuntimeCompiler {

    public static Class<?> compile(String className, String source) throws Exception {

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        MfFileManager fileManager = new MfFileManager(compiler.getStandardFileManager(null, null, null));
        JavaFileObject sourceObj = new MfSourceFromString(className, source);
        List<JavaFileObject> files = List.of(sourceObj);
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, null, null, files);
        boolean result = task.call();
        if (!result) {
            throw new RuntimeException("Compilation failed.");
        }
        ClassLoader classLoader = fileManager.getClassLoader(null);
        return classLoader.loadClass(className);
    }
}
