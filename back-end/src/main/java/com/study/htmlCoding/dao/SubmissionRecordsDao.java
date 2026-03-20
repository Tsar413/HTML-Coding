package com.study.htmlCoding.dao;

import com.study.htmlCoding.entity.SubmissionRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SubmissionRecordsDao extends JpaRepository<SubmissionRecords, Integer> {
    @Query(value = "insert into submission_record(submission_record_id, school_id, problem_id, submission_code, passed, passed_number, all_number, message, time_used_ms) values (?1, ?2, ?3, ?4, true, ?5, ?6, '所有测试用例通过！', ?7)", nativeQuery = true)
    @Transactional
    @Modifying
    Integer saveNewSubmissionRecordPassed(Long submissionRecordId, String schoolId, String problemId, String submissionCode, Integer passedNumber, Integer allNumber, Long timeUsedMs);

    @Query(value = "insert into submission_record(submission_record_id, school_id, problem_id, submission_code, passed, passed_number, all_number, message, input, expected_output, actual_output, error_info, time_used_ms) values (?1, ?2, ?3, ?4, false, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12)", nativeQuery = true)
    @Transactional
    @Modifying
    Integer saveNewSubmissionRecordFailed(Long submissionRecordId, String schoolId, String problemId, String submissionCode, Integer passedNumber, Integer allNumber, String message, String input, String expectedOutput, String actualOutput, String errorInfo, Long timeUsedMs);

    @Query(value = "select max(submission_record_id) from submission_record", nativeQuery = true)
    public Long getMaxSubmissionRecordId();

    @Query(value = "select * from submission_record where school_id = ?1 and problem_id = ?2", nativeQuery = true)
    public List<SubmissionRecords> getSubmissionRecordByProblemIdSchoolId(String schoolId, String problemId);

    @Query(value = "select * from submission_record where school_id = ?1 order by submission_record_id desc", nativeQuery = true)
    public List<SubmissionRecords> getSubmissionRecordBySchoolId(String schoolId);
}
