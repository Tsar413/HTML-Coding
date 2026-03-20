package com.study.htmlCoding.dto;

import com.study.htmlCoding.entity.Problem;

import java.util.List;

public class ProblemConsultDTO {
    private Integer status;

    private List<Problem> problems;

    public ProblemConsultDTO() {
    }

    public ProblemConsultDTO(Integer status, List<Problem> problems) {
        this.status = status;
        this.problems = problems;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Problem> getProblems() {
        return problems;
    }

    public void setProblems(List<Problem> problems) {
        this.problems = problems;
    }

    @Override
    public String toString() {
        return "ProblemConsultDTO{" +
                "status=" + status +
                ", problems=" + problems +
                '}';
    }
}
