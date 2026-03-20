package com.study.htmlCoding.dto;

public class CodeResultDTO {
    private Integer status;

    private String message;

    private String compileOutput;

    private Integer passedNumber;

    private Integer allNumber;

    private Integer caseId;

    private Boolean passed;

    private String input;

    private String expectedOutput;

    private String actualOutput;

    private String errorInfo;

    private Long timeUsedMs;

    private Long memoryUsedKb;

    public CodeResultDTO() {
    }

    public CodeResultDTO(Integer status, String message, String compileOutput, Integer passedNumber, Integer allNumber, Integer caseId, Boolean passed, String input, String expectedOutput, String actualOutput, String errorInfo, Long timeUsedMs, Long memoryUsedKb) {
        this.status = status;
        this.message = message;
        this.compileOutput = compileOutput;
        this.passedNumber = passedNumber;
        this.allNumber = allNumber;
        this.caseId = caseId;
        this.passed = passed;
        this.input = input;
        this.expectedOutput = expectedOutput;
        this.actualOutput = actualOutput;
        this.errorInfo = errorInfo;
        this.timeUsedMs = timeUsedMs;
        this.memoryUsedKb = memoryUsedKb;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCompileOutput() {
        return compileOutput;
    }

    public void setCompileOutput(String compileOutput) {
        this.compileOutput = compileOutput;
    }

    public Integer getPassedNumber() {
        return passedNumber;
    }

    public void setPassedNumber(Integer passedNumber) {
        this.passedNumber = passedNumber;
    }

    public Integer getAllNumber() {
        return allNumber;
    }

    public void setAllNumber(Integer allNumber) {
        this.allNumber = allNumber;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public Boolean getPassed() {
        return passed;
    }

    public void setPassed(Boolean passed) {
        this.passed = passed;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getExpectedOutput() {
        return expectedOutput;
    }

    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

    public String getActualOutput() {
        return actualOutput;
    }

    public void setActualOutput(String actualOutput) {
        this.actualOutput = actualOutput;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public Long getTimeUsedMs() {
        return timeUsedMs;
    }

    public void setTimeUsedMs(Long timeUsedMs) {
        this.timeUsedMs = timeUsedMs;
    }

    public Long getMemoryUsedKb() {
        return memoryUsedKb;
    }

    public void setMemoryUsedKb(Long memoryUsedKb) {
        this.memoryUsedKb = memoryUsedKb;
    }

    @Override
    public String toString() {
        return "CodeResultDTO{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", compileOutput='" + compileOutput + '\'' +
                ", passedNumber=" + passedNumber +
                ", allNumber=" + allNumber +
                ", caseId=" + caseId +
                ", passed=" + passed +
                ", input='" + input + '\'' +
                ", expectedOutput='" + expectedOutput + '\'' +
                ", actualOutput='" + actualOutput + '\'' +
                ", errorInfo='" + errorInfo + '\'' +
                ", timeUsedMs=" + timeUsedMs +
                ", memoryUsedKb=" + memoryUsedKb +
                '}';
    }
}
