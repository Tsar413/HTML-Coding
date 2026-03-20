package com.study.htmlCoding.dto;

public class CodeRequestDTO {
    private String code;

    private String userId;

    private String problemId;

    private String token;

    public CodeRequestDTO() {
    }

    public CodeRequestDTO(String code, String userId, String problemId, String token) {
        this.code = code;
        this.userId = userId;
        this.problemId = problemId;
        this.token = token;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProblemId() {
        return problemId;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "CodeRequestDTO{" +
                "code='" + code + '\'' +
                ", userId='" + userId + '\'' +
                ", problemId='" + problemId + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
