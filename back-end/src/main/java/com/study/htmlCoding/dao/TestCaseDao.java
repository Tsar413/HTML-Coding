package com.study.htmlCoding.dao;

import com.study.htmlCoding.entity.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TestCaseDao extends JpaRepository<TestCase, Integer> {
    @Query(value = "select * from tb_test_case where tb_test_case.problem_id = ?1 and deleted = 1", nativeQuery = true)
    public List<TestCase> getTestCasesWithId(String problemId);

    @Query(value = "select max(test_case_id) from tb_test_case", nativeQuery = true)
    public Long getMaxTestCaseId();

    @Query(value = "insert into tb_test_case(test_case_id, expected_output, input, problem_id, deleted) values (?1, ?2, ?3, ?4, 1)", nativeQuery = true)
    @Transactional
    @Modifying
    Integer saveNewTestCase(Long testCaseId, String expectedOutput, String input, Integer problemId);

    @Query(value = "update tb_test_case set expected_output =?2, input = ?3 where test_case_id = ?1", nativeQuery = true)
    @Transactional
    @Modifying
    Integer updateTestCase(Long testCaseId, String expectedOutput, String input);

    @Query(value = "update tb_test_case set deleted = 0 where test_case_id = ?1", nativeQuery = true)
    @Transactional
    @Modifying
    Integer deleteTestCase(Long testCaseId);
}
