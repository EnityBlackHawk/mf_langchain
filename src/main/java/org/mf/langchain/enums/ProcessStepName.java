package org.mf.langchain.enums;
// FlowStepName
public enum ProcessStepName {

    INIT("INIT"), // Initialize the flow
    VERIFY_CARDINALITY("VERIFY_CARDINALITY"), // Show the cardinality to the user (the user can edit it)
    GENERATE_MODEL("GENERATE_MODEL"), // Generate the model (the user needs to approve the model)
    GENERATE_JAVA_CODE("GENERATE_JAVA_CODE"); // Generate the Java code

    private final String value;

    ProcessStepName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
