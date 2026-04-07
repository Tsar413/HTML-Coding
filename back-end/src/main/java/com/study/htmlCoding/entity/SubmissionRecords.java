package com.study.htmlCoding.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.persistence.*;

@Entity
@Table(name = "submission_record")
@TableName("submission_record")
public class SubmissionRecords {
    @Id
    @Column(name = "submission_record_id")
    @TableField("submission_record_id")
    private Long submissionRecordId;

    @Column(name = "school_id")
    @TableField("school_id")
    private String schoolId;

    @Column(name = "problem_id")
    @TableField("problem_id")
    private String problemId; // 问题ID

    @Lob
    @Column(name = "submission_code", columnDefinition = "LONGTEXT")
    @TableField("submission_code")
    private String submissionCode; // 提交代码

    @Column(name = "passed")
    private Boolean passed; // 是否通过

    @Column(name = "passed_number")
    @TableField("passed_number")
    private Integer passedNumber; // 通过测试用例数量

    @Column(name = "all_number")
    @TableField("all_number")
    private Integer allNumber; // 所有测试用例数目

    @Column(name = "message")
    private String message; // 信息

    @Column(name = "input")
    private String input; // 输入

    @Column(name = "expected_output")
    @TableField("expected_output")
    private String expectedOutput; // 预期输出

    @Column(name = "actual_output")
    @TableField("actual_output")
    private String actualOutput; // 实际输出

    @Column(name = "error_info")
    @TableField("error_info")
    private String errorInfo; // 错误信息

    @Column(name = "time_used_ms")
    @TableField("time_used_ms")
    private Long timeUsedMs; // 执行时间

    public SubmissionRecords() {
    }

    public SubmissionRecords(Long submissionRecordId, String schoolId, String problemId, String submissionCode, Boolean passed, Integer passedNumber, Integer allNumber, String message, String input, String expectedOutput, String actualOutput, String errorInfo, Long timeUsedMs) {
        this.submissionRecordId = submissionRecordId;
        this.schoolId = schoolId;
        this.problemId = problemId;
        this.submissionCode = submissionCode;
        this.passed = passed;
        this.passedNumber = passedNumber;
        this.allNumber = allNumber;
        this.message = message;
        this.input = input;
        this.expectedOutput = expectedOutput;
        this.actualOutput = actualOutput;
        this.errorInfo = errorInfo;
        this.timeUsedMs = timeUsedMs;
    }

    public Long getSubmissionRecordId() {
        return submissionRecordId;
    }

    public void setSubmissionRecordId(Long submissionRecordId) {
        this.submissionRecordId = submissionRecordId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getProblemId() {
        return problemId;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    public String getSubmissionCode() {
        return submissionCode;
    }

    public void setSubmissionCode(String submissionCode) {
        this.submissionCode = submissionCode;
    }

    public Boolean getPassed() {
        return passed;
    }

    public void setPassed(Boolean passed) {
        this.passed = passed;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    @Override
    public String toString() {
        return "SubmissionRecords{" +
                "submissionRecordId=" + submissionRecordId +
                ", schoolId='" + schoolId + '\'' +
                ", problemId='" + problemId + '\'' +
                ", submissionCode='" + submissionCode + '\'' +
                ", passed=" + passed +
                ", passedNumber=" + passedNumber +
                ", allNumber=" + allNumber +
                ", message='" + message + '\'' +
                ", input='" + input + '\'' +
                ", expectedOutput='" + expectedOutput + '\'' +
                ", actualOutput='" + actualOutput + '\'' +
                ", errorInfo='" + errorInfo + '\'' +
                ", timeUsedMs=" + timeUsedMs +
                '}';
    }
}
