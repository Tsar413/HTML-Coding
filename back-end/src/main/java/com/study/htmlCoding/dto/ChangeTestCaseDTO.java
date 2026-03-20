package com.study.htmlCoding.dto;

import com.study.htmlCoding.entity.TestCase;

public class ChangeTestCaseDTO {

    private String id;

    private String token;

    private TestCase testCase;

    public ChangeTestCaseDTO() {
    }

    public ChangeTestCaseDTO(String id, String token, TestCase testCase) {
        this.id = id;
        this.token = token;
        this.testCase = testCase;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public TestCase getTestCase() {
        return testCase;
    }

    public void setTestCase(TestCase testCase) {
        this.testCase = testCase;
    }

    @Override
    public String toString() {
        return "ChangeTestCaseDTO{" +
                "id='" + id + '\'' +
                ", token='" + token + '\'' +
                ", testCase=" + testCase +
                '}';
    }
}
