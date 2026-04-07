package com.study.htmlCoding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.htmlCoding.entity.SubmissionRecords;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SubmissionRecordsMapper extends BaseMapper<SubmissionRecords> {
    public Long getMaxSubmissionRecordId();

    public Integer saveNewSubmissionRecordPassed(Long submissionRecordId, String schoolId, String problemId, String submissionCode, Integer passedNumber, Integer allNumber, Long timeUsedMs);

    public Integer saveNewSubmissionRecordFailed(Long submissionRecordId, String schoolId, String problemId, String submissionCode, Integer passedNumber, Integer allNumber, String message, String input, String expectedOutput, String actualOutput, String errorInfo, Long timeUsedMs);
}
