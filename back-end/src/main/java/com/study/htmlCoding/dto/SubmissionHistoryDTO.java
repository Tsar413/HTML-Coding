package com.study.htmlCoding.dto;

import com.study.htmlCoding.entity.SubmissionRecords;

import java.util.List;

public class SubmissionHistoryDTO {
    private Integer status;

    private List<SubmissionRecords> submissionRecords;

    public SubmissionHistoryDTO() {
    }

    public SubmissionHistoryDTO(Integer status, List<SubmissionRecords> submissionRecords) {
        this.status = status;
        this.submissionRecords = submissionRecords;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<SubmissionRecords> getSubmissionRecords() {
        return submissionRecords;
    }

    public void setSubmissionRecords(List<SubmissionRecords> submissionRecords) {
        this.submissionRecords = submissionRecords;
    }

    @Override
    public String toString() {
        return "SubmissionHistoryDTO{" +
                "status=" + status +
                ", submissionRecords=" + submissionRecords +
                '}';
    }
}
