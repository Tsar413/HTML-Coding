package com.study.htmlCoding.dto;

public class ProblemWithIdDTO {

    private String schoolId;

    private String token;

    private Integer problemId;

    public ProblemWithIdDTO() {
    }

    public ProblemWithIdDTO(String schoolId, String token, Integer problemId) {
        this.schoolId = schoolId;
        this.token = token;
        this.problemId = problemId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    @Override
    public String toString() {
        return "DeleteProblemId{" +
                "schoolId='" + schoolId + '\'' +
                ", token='" + token + '\'' +
                ", problemId=" + problemId +
                '}';
    }
}
