package com.study.htmlCoding.dto;

import com.study.htmlCoding.entity.TestCase;

import java.util.List;

public class TestCaseConsultDTO {
    private Integer status;

    private List<TestCase> testCases;

    public TestCaseConsultDTO() {
    }

    public TestCaseConsultDTO(Integer status, List<TestCase> testCases) {
        this.status = status;
        this.testCases = testCases;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<TestCase> getTestCases() {
        return testCases;
    }

    public void setTestCases(List<TestCase> testCases) {
        this.testCases = testCases;
    }

    @Override
    public String toString() {
        return "TestCaseConsultDTO{" +
                "status=" + status +
                ", testCases=" + testCases +
                '}';
    }
}
