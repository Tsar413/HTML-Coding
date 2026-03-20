package com.study.htmlCoding.dto;

public class TestCaseResultDTO {
    private Integer caseId;        // 测试用例编号
    private Boolean passed;        // 是否通过
    private String input;          // 输入
    private String expectedOutput; // 预期输出
    private String actualOutput;   // 实际输出
    private String errorInfo;      // 错误信息（如运行时错误）
    private Long timeUsedMs;       // 执行时间（毫秒）
    private Long memoryUsedKb;     // 内存使用（KB，模拟）

    public TestCaseResultDTO() {
    }

    public TestCaseResultDTO(Integer caseId, Boolean passed, String input, String expectedOutput, String actualOutput, String errorInfo, Long timeUsedMs, Long memoryUsedKb) {
        this.caseId = caseId;
        this.passed = passed;
        this.input = input;
        this.expectedOutput = expectedOutput;
        this.actualOutput = actualOutput;
        this.errorInfo = errorInfo;
        this.timeUsedMs = timeUsedMs;
        this.memoryUsedKb = memoryUsedKb;
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
        return "TestCaseResultDTO{" +
                "caseId=" + caseId +
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
