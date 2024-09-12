package org.mf.langchain.service;

import lombok.Getter;
import org.mf.langchain.runtimeCompiler.MfRuntimeCompiler;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;


@Service
public class CompilerService {

    public String compileAndRun(String className, String code) throws Exception {


        // Compile
        Class<?> generatedClass = MfRuntimeCompiler.compile(className, code);

        Object instance = generatedClass.getDeclaredConstructor().newInstance();
        String result = generatedClass.getMethod("hello").invoke(instance).toString();

        System.out.print(result);

        return result;
    }

}
