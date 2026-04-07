package com.study.htmlCoding.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.persistence.*;

@Entity
@Table(name = "tb_problem")
@TableName("tb_problem")
public class Problem {
    @Id
    @Column(name = "problem_id")
    @TableField("problem_id")
    private Integer problemId;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "description", columnDefinition = "LONGTEXT")
    private String description;

    @Column(name = "return_type")
    @TableField("return_type")
    private String returnType;

    @Column(name = "method_name")
    @TableField("method_name")
    private String methodName;

    @Column(name = "method_signature_params")
    @TableField("method_signature_params")
    private String methodSignatureParams;

    @Column(name = "deleted")
    private Boolean deleted;

    public Problem() {
    }

    public Problem(Integer problemId, String title, String description, String returnType, String methodName, String methodSignatureParams) {
        this.problemId = problemId;
        this.title = title;
        this.description = description;
        this.returnType = returnType;
        this.methodName = methodName;
        this.methodSignatureParams = methodSignatureParams;
        this.deleted = false;
    }

    public Problem(Integer problemId, String title, String description, String returnType, String methodName, String methodSignatureParams, Boolean deleted) {
        this.problemId = problemId;
        this.title = title;
        this.description = description;
        this.returnType = returnType;
        this.methodName = methodName;
        this.methodSignatureParams = methodSignatureParams;
        this.deleted = deleted;
    }

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodSignatureParams() {
        return methodSignatureParams;
    }

    public void setMethodSignatureParams(String methodSignatureParams) {
        this.methodSignatureParams = methodSignatureParams;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Problem{" +
                "problemId=" + problemId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", returnType='" + returnType + '\'' +
                ", methodName='" + methodName + '\'' +
                ", methodSignatureParams='" + methodSignatureParams + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
