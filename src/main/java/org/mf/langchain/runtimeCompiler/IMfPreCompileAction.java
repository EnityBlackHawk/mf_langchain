package org.mf.langchain.runtimeCompiler;

public interface IMfPreCompileAction {
    String action(String className, String source);
}
