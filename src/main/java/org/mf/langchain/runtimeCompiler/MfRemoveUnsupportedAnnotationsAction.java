package org.mf.langchain.runtimeCompiler;

public class MfRemoveUnsupportedAnnotationsAction implements IMfPreCompileAction {
    @Override
    public String action(String className, String source) {
        return source.replaceAll("@Document.*(?:\\\\r?\\\\n|\\\\r)?", "");
    }
}
