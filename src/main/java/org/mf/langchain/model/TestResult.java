package org.mf.langchain.model;

import jakarta.persistence.*;
import lombok.Data;
import org.mf.langchain.DTO.SpecificationDTO;

import java.util.Date;
import java.util.Objects;

@Entity
public class TestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "test_result_seq")
    @SequenceGenerator(name = "test_result_seq", sequenceName = "test_result_seq", allocationSize = 1)
    private Integer id;
    @Column(columnDefinition = "TEXT")
    private String request;
    @OneToOne
    private Specification spec;
    @Column(columnDefinition = "TEXT")
    private String response;
    private Integer tokenCount;
    private Date date;

    public TestResult(
            Integer id,
            String request,
            Specification spec,
            String response,
            Integer tokenCount,
            Date date
    ) {
        this.id = id;
        this.request = request;
        this.spec = spec;
        this.response = response;
        this.tokenCount = tokenCount;
        this.date = date;
    }

    public TestResult() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public Specification getSpec() {
        return spec;
    }

    public void setSpec(Specification spec) {
        this.spec = spec;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Integer getTokenCount() {
        return tokenCount;
    }

    public void setTokenCount(Integer tokenCount) {
        this.tokenCount = tokenCount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (TestResult) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.request, that.request) &&
                Objects.equals(this.spec, that.spec) &&
                Objects.equals(this.response, that.response) &&
                Objects.equals(this.tokenCount, that.tokenCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, request, spec, response, tokenCount);
    }

    @Override
    public String toString() {
        return "TestResult[" +
                "id=" + id + ", " +
                "request=" + request + ", " +
                "spec=" + spec + ", " +
                "response=" + response + ", " +
                "tokenCount=" + tokenCount + ']';
    }

}
