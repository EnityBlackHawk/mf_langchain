package org.mf.langchain.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Type;
import org.mf.langchain.DTO.SpecificationDTO;
import org.mf.langchain.model.Workload;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;

@Entity
public class Specification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "specification_seq")
    @SequenceGenerator(name = "specification_seq", sequenceName = "specification_seq", allocationSize = 1)
    private Integer id;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String data_source;
    @OneToMany
    private List<Workload> workload;
    private Boolean allow_ref;
    private Boolean prioritize_performance;
    private String framework;
    private String custom_prompt;
    private String LLM;
    @Column(columnDefinition = "jsonb")
    @Type(value = JsonBinaryType.class)
    private Map<String, Integer> cardinality;

    public Specification(
            Integer id,
            String name,
            String data_source,
            List<Workload> workload,
            Boolean allow_ref,
            Boolean prioritize_performance,
            String framework,
            List<String> custom_prompt,
            String LLM,
            Map<String, Integer> cardinality
    ) {
        StringBuilder cp = new StringBuilder();
        if(custom_prompt != null)
            for (String s : custom_prompt) {
                cp.append(s).append("|");
            }

        this.id = id;
        this.name = name;
        this.data_source = data_source;
        this.workload = workload;
        this.allow_ref = allow_ref;
        this.prioritize_performance = prioritize_performance;
        this.framework = framework;
        this.custom_prompt = cp.toString();
        this.LLM = LLM;
        this.cardinality = cardinality;
    }

    public Specification(SpecificationDTO dto) {
        this(null, dto.name(), dto.data_source(), dto.workload().stream().map(Workload::new).toList(), dto.allow_ref(), dto.prioritize_performance(), dto.framework(), dto.custom_prompt(), dto.LLM(), dto.cardinality());
    }

    public Specification() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData_source() {
        return data_source;
    }

    public void setData_source(String data_source) {
        this.data_source = data_source;
    }

    public List<Workload> getWorkload() {
        return workload;
    }

    public void setWorkload(List<Workload> workload) {
        this.workload = workload;
    }

    public Boolean getAllow_ref() {
        return allow_ref;
    }

    public void setAllow_ref(Boolean allow_ref) {
        this.allow_ref = allow_ref;
    }

    public Boolean getPrioritize_performance() {
        return prioritize_performance;
    }

    public void setPrioritize_performance(Boolean prioritize_performance) {
        this.prioritize_performance = prioritize_performance;
    }

    public String getFramework() {
        return framework;
    }

    public void setFramework(String framework) {
        this.framework = framework;
    }

    public List<String> getCustom_prompt() {
        if(custom_prompt == null || custom_prompt.isEmpty())
            return List.of();
        return Arrays.stream(custom_prompt.split("\\|")).toList();
    }

    public void setCustom_prompt(String custom_prompt) {
        this.custom_prompt = custom_prompt;
    }

    public String getLLM() {
        return LLM;
    }

    public void setLLM(String LLM) {
        this.LLM = LLM;
    }

    public Map<String, Integer> getCardinality() {
        return cardinality;
    }

    public void setCardinality(Map<String, Integer> cardinality) {
        this.cardinality = cardinality;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Specification) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.data_source, that.data_source) &&
                Objects.equals(this.workload, that.workload) &&
                Objects.equals(this.allow_ref, that.allow_ref) &&
                Objects.equals(this.prioritize_performance, that.prioritize_performance) &&
                Objects.equals(this.framework, that.framework) &&
                Objects.equals(this.custom_prompt, that.custom_prompt) &&
                Objects.equals(this.LLM, that.LLM);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, data_source, workload, allow_ref, prioritize_performance, framework, custom_prompt, LLM);
    }

    @Override
    public String toString() {
        return "Specification[" +
                "id=" + id + ", " +
                "name=" + name + ", " +
                "data_source=" + data_source + ", " +
                "workload=" + workload + ", " +
                "allow_ref=" + allow_ref + ", " +
                "prioritize_performance=" + prioritize_performance + ", " +
                "framework=" + framework + ", " +
                "custom_prompt=" + custom_prompt + ", " +
                "LLM=" + LLM + ']';
    }

}
