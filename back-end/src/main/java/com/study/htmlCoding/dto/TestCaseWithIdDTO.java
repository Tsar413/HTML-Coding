package com.study.htmlCoding.dto;

public class TestCaseWithIdDTO {

    private String id;

    private String token;

    private Long testCaseId;

    public TestCaseWithIdDTO() {
    }

    public TestCaseWithIdDTO(String id, String token, Long testCaseId) {
        this.id = id;
        this.token = token;
        this.testCaseId = testCaseId;
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

    public Long getTestCaseId() {
        return testCaseId;
    }

    public void setTestCaseId(Long testCaseId) {
        this.testCaseId = testCaseId;
    }

    @Override
    public String toString() {
        return "TestCaseWithIdDTO{" +
                "id='" + id + '\'' +
                ", token='" + token + '\'' +
                ", testCaseId=" + testCaseId +
                '}';
    }
}
