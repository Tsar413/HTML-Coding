package com.study.htmlCoding.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_test_case")
@TableName("tb_test_case")
public class TestCase {
    @Id
    @Column(name = "test_case_id")
    @TableField("test_case_id")
    private Long testCaseId;

    @Column(name = "problem_id")
    @TableField("problem_id")
    private Integer problemId;

    @Column(name = "input")
    private String input;

    @Column(name = "expected_output")
    @TableField("expected_output")
    private String expectedOutput;

    @Column(name = "deleted")
    private Boolean deleted;

    public TestCase() {
    }

    public TestCase(Long testCaseId, Integer problemId, String input, String expectedOutput) {
        this.testCaseId = testCaseId;
        this.problemId = problemId;
        this.input = input;
        this.expectedOutput = expectedOutput;
        this.deleted = false;
    }

    public TestCase(Long testCaseId, Integer problemId, String input, String expectedOutput, Boolean deleted) {
        this.testCaseId = testCaseId;
        this.problemId = problemId;
        this.input = input;
        this.expectedOutput = expectedOutput;
        this.deleted = deleted;
    }

    public Long getTestCaseId() {
        return testCaseId;
    }

    public void setTestCaseId(Long testCaseId) {
        this.testCaseId = testCaseId;
    }

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "TestCase{" +
                "testCaseId=" + testCaseId +
                ", problemId=" + problemId +
                ", input='" + input + '\'' +
                ", expectedOutput='" + expectedOutput + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
