package org.mf.langchain.runtimeCompiler;

public class MfDefaultPreCompileAction implements IMfPreCompileAction {

    private final MfRemoveUnsupportedAnnotationsAction _action1 =
            new MfRemoveUnsupportedAnnotationsAction();

    @Override
    public String action(String className, String source) {
        return _action1.action(className, source);
    }
}
