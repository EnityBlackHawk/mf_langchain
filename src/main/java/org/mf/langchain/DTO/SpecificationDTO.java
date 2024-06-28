package org.mf.langchain.DTO;

import java.util.List;
import java.util.Objects;

public class SpecificationDTO {
    private String name;
    private String data_source;
    private List<WorkloadDTO> workload;
    private Boolean allow_ref;
    private Boolean prioritize_performance;
    private String framework;
    private List<String> custom_prompt;
    private String LLM;
    private Credentials credentials;
    private List<RelationCardinality> cardinality;

    public SpecificationDTO(){}

    public SpecificationDTO(
            String name,
            String data_source,
            List<WorkloadDTO> workload,
            Boolean allow_ref,
            Boolean prioritize_performance,
            String framework,
            List<String> custom_prompt,
            String LLM,
            Credentials credentials,
            List<RelationCardinality> cardinality
    ) {
        this.name = name;
        this.data_source = data_source;
        this.workload = workload;
        this.allow_ref = allow_ref;
        this.prioritize_performance = prioritize_performance;
        this.framework = framework;
        this.custom_prompt = custom_prompt;
        this.LLM = LLM;
        this.credentials = credentials;
        this.cardinality = cardinality;
    }

    public static class WorkloadDTO {
        private Integer regularity;
        private String query;

        public WorkloadDTO(){}

        public WorkloadDTO(Integer regularity, String query) {
            this.regularity = regularity;
            this.query = query;
        }

        public Integer getRegularity() {
            return regularity;
        }

        public void setRegularity(Integer regularity) {
            this.regularity = regularity;
        }

        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }
    }

    public static SpecificationDTO overrideDataSource(SpecificationDTO spec, String dataSource) {
        return new SpecificationDTO(
                spec.name(),
                dataSource,
                spec.workload(),
                spec.allow_ref(),
                spec.prioritize_performance(),
                spec.framework(),
                spec.custom_prompt(),
                spec.LLM(),
                spec.credentials(),
                spec.cardinality()
        );
    }

    public static SpecificationDTO overrideCardinality(SpecificationDTO spec, List<RelationCardinality> cardinality) {
        return new SpecificationDTO(
                spec.name(),
                spec.data_source(),
                spec.workload(),
                spec.allow_ref(),
                spec.prioritize_performance(),
                spec.framework(),
                spec.custom_prompt(),
                spec.LLM(),
                spec.credentials(),
                cardinality
        );
    }

    public boolean isInvalid() {
        return data_source == null && credentials == null;
    }

    public boolean isOnline() {
        return credentials != null;
    }

    public String name() {
        return name;
    }

    public String data_source() {
        return data_source;
    }

    public List<WorkloadDTO> workload() {
        return workload;
    }

    public Boolean allow_ref() {
        return allow_ref;
    }

    public Boolean prioritize_performance() {
        return prioritize_performance;
    }

    public String framework() {
        return framework;
    }

    public List<String> custom_prompt() {
        return custom_prompt;
    }

    public String LLM() {
        return LLM;
    }

    public Credentials credentials() {
        return credentials;
    }

    public List<RelationCardinality> cardinality() {
        return cardinality;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (SpecificationDTO) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.data_source, that.data_source) &&
                Objects.equals(this.workload, that.workload) &&
                Objects.equals(this.allow_ref, that.allow_ref) &&
                Objects.equals(this.prioritize_performance, that.prioritize_performance) &&
                Objects.equals(this.framework, that.framework) &&
                Objects.equals(this.custom_prompt, that.custom_prompt) &&
                Objects.equals(this.LLM, that.LLM) &&
                Objects.equals(this.credentials, that.credentials) &&
                Objects.equals(this.cardinality, that.cardinality);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, data_source, workload, allow_ref, prioritize_performance, framework, custom_prompt, LLM, credentials, cardinality);
    }

    @Override
    public String toString() {
        return "SpecificationDTO[" +
                "name=" + name + ", " +
                "data_source=" + data_source + ", " +
                "workload=" + workload + ", " +
                "allow_ref=" + allow_ref + ", " +
                "prioritize_performance=" + prioritize_performance + ", " +
                "framework=" + framework + ", " +
                "custom_prompt=" + custom_prompt + ", " +
                "LLM=" + LLM + ", " +
                "credentials=" + credentials + ", " +
                "cardinality=" + cardinality + ']';
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setData_source(String data_source) {
        this.data_source = data_source;
    }

    public void setWorkload(List<WorkloadDTO> workload) {
        this.workload = workload;
    }

    public void setAllow_ref(Boolean allow_ref) {
        this.allow_ref = allow_ref;
    }

    public void setPrioritize_performance(Boolean prioritize_performance) {
        this.prioritize_performance = prioritize_performance;
    }

    public void setFramework(String framework) {
        this.framework = framework;
    }

    public void setCustom_prompt(List<String> custom_prompt) {
        this.custom_prompt = custom_prompt;
    }

    public void setLLM(String LLM) {
        this.LLM = LLM;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public void setCardinality(List<RelationCardinality> cardinality) {
        this.cardinality = cardinality;
    }
}
