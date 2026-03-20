package com.study.htmlCoding.dto;

import com.study.htmlCoding.entity.Problem;

import java.util.List;

public class ChangeProblemDTO {

    private String schoolId;

    private String token;

    private Problem problem;

    public ChangeProblemDTO() {
    }

    public ChangeProblemDTO(String schoolId, String token, Problem problem) {
        this.schoolId = schoolId;
        this.token = token;
        this.problem = problem;
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

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    @Override
    public String toString() {
        return "ChangeProblemDTO{" +
                "schoolId='" + schoolId + '\'' +
                ", token='" + token + '\'' +
                ", problem=" + problem +
                '}';
    }
}
