package com.study.htmlCoding.dao;

import com.study.htmlCoding.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProblemDao extends JpaRepository<Problem, Integer> {
    @Query(value = "select * from tb_problem where tb_problem.problem_id = ?1", nativeQuery = true)
    public Problem getProblemWithId(String problemId);

    @Query(value = "select * from tb_problem where deleted = 1", nativeQuery = true)
    public List<Problem> getAllProblems();

    @Query(value = "select max(problem_id) from tb_problem", nativeQuery = true)
    public Integer getMaxProblemId();

    @Query(value = "insert into tb_problem(problem_id, description, title, method_name, method_signature_params, return_type, deleted) values (?1, ?2, ?3, ?4, ?5, ?6, 1)", nativeQuery = true)
    @Transactional
    @Modifying
    Integer saveNewProblem(Integer problemId, String description, String title, String methodName, String methodSignatureParams, String returnType);

    @Query(value = "update tb_problem set description = ?2, title = ?3, method_name = ?4, method_signature_params = ?5, return_type = ?6 where problem_id = ?1", nativeQuery = true)
    @Transactional
    @Modifying
    Integer updateProblem(Integer problemId, String description, String title, String methodName, String methodSignatureParams, String returnType);

    @Query(value = "update tb_problem set deleted = 0 where problem_id = ?1", nativeQuery = true)
    @Transactional
    @Modifying
    Integer deleteProblem(Integer problemId);
}
