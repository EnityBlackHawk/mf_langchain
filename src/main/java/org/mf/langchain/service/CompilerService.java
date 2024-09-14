package org.mf.langchain.service;

import lombok.Getter;
import org.mf.langchain.runtimeCompiler.MfRuntimeCompiler;
import org.springframework.stereotype.Service;

import java.io.File;
import java.lang.reflect.InvocationTargetException;


@Service
public class CompilerService {

    public String compileAndRun(String className, String code) throws Exception {

        // Compile
        Class<?> generatedClass = MfRuntimeCompiler.compile(className, code);

        Object instance = generatedClass.getDeclaredConstructor().newInstance();

        var methods = generatedClass.getMethods();

        return generatedClass.getMethod("getName").invoke(instance).toString();
    }

}
