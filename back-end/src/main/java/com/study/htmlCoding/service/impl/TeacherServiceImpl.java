package com.study.htmlCoding.service.impl;

import com.study.htmlCoding.dao.ProblemDao;
import com.study.htmlCoding.dao.SubmissionRecordsDao;
import com.study.htmlCoding.dao.TestCaseDao;
import com.study.htmlCoding.dao.UserDao;
import com.study.htmlCoding.dto.*;
import com.study.htmlCoding.entity.Problem;
import com.study.htmlCoding.entity.SubmissionRecords;
import com.study.htmlCoding.entity.TestCase;
import com.study.htmlCoding.entity.User;
import com.study.htmlCoding.service.ITeacherService;
import com.study.htmlCoding.util.GenerateMethodSignatureParams;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherServiceImpl implements ITeacherService {

    @Resource
    private ProblemDao problemDao;

    @Resource
    private TestCaseDao testCaseDao;

    @Resource
    private UserDao userDao;

    @Resource
    private SubmissionRecordsDao submissionRecordsDao;

    /**
     * 页面提交请求获取问题集
     *
     * @param userDTO 用户信息
     * @return 查询结果 由Problem组成的list
     */
    @Override
    public ProblemConsultDTO getAllProblems(UserDTO userDTO) {
        // TODO token处理

        // 获取所有问题
        List<Problem> allProblems = problemDao.getAllProblems();
        // 把参数换成正常格式
        for (Problem problem : allProblems) {
            String[] methodSignatureParams = problem.getMethodSignatureParams().split(";");
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = 0; j < methodSignatureParams.length; j++) {
                String[] strings = methodSignatureParams[j].split(",");
                if (strings[2].equals("one") || strings[2].equals("other")) {
                    stringBuilder.append(strings[1]).append(" ").append(strings[0]);
                } else if (strings[2].equals("array")) {
                    stringBuilder.append(strings[1]).append("[]").append(" ").append(strings[0]);
                } else if (strings[2].equals("list")) {
                    stringBuilder.append("List<").append(strings[1]).append("> ").append(strings[0]);
                }
                if (j < methodSignatureParams.length - 1) {
                    stringBuilder.append(", ");
                }
            }
            problem.setMethodSignatureParams(stringBuilder.toString());
        }

        ProblemConsultDTO problemConsultDTO = new ProblemConsultDTO();
        problemConsultDTO.setStatus(200);
        problemConsultDTO.setProblems(allProblems);
        return problemConsultDTO;
    }

    /**
     * 页面提交添加问题的内容
     *
     * @param changeProblemDTO 题目信息
     * @return 返回status为200，problem为空
     */
    @Override
    public ProblemConsultDTO addProblem(ChangeProblemDTO changeProblemDTO) {
        System.out.println(changeProblemDTO);
        // TODO token处理

        // 处理提交的problem
        Problem newProblem = changeProblemDTO.getProblem();
        String methodSignatureParams = GenerateMethodSignatureParams.generateMethodSignatureParams(newProblem.getMethodSignatureParams());
        Integer maxProblemId = problemDao.getMaxProblemId();
        if(maxProblemId == null){
            maxProblemId = 1;
        } else {
            maxProblemId++;
        }
        Integer i = problemDao.saveNewProblem(maxProblemId, newProblem.getDescription(), newProblem.getTitle(), newProblem.getMethodName(), methodSignatureParams, newProblem.getReturnType());
        if(i == 1){
            return new ProblemConsultDTO(200, null);
        }
        return new ProblemConsultDTO(500, null);
    }

    /**
     * 该方法的作用为修改问题
     *
     * @param changeProblemDTO 需要修改的题目信息 包含ID
     * @return 返回status为200，problem为空
     */
    @Override
    public ProblemConsultDTO updateProblem(ChangeProblemDTO changeProblemDTO) {
        System.out.println(changeProblemDTO);
        // TODO token处理

        // 修改提交的problem
        Problem updateProblem = changeProblemDTO.getProblem();
        String methodSignatureParams = GenerateMethodSignatureParams.generateMethodSignatureParams(updateProblem.getMethodSignatureParams());
        Integer i = problemDao.updateProblem(updateProblem.getProblemId(), updateProblem.getDescription(), updateProblem.getTitle(), updateProblem.getMethodName(), methodSignatureParams, updateProblem.getReturnType());
        if(i == 1){
            return new ProblemConsultDTO(200, null);
        }
        return new ProblemConsultDTO(500, null);
    }

    /**
     * 该方法的作用为删除不需要的题目
     *
     * @param problemWithIdDTO 需要删除的题目信息
     * @return 返回status为200，problem为空
     */
    @Override
    public ProblemConsultDTO deleteProblem(ProblemWithIdDTO problemWithIdDTO) {
        // TODO token处理

        // 删除提交的problem 具体方法为软删除 将deleted修改为0
        Integer i = problemDao.deleteProblem(problemWithIdDTO.getProblemId());
        if(i == 1){
            return new ProblemConsultDTO(200, null);
        }
        return new ProblemConsultDTO(500, null);
    }

    /**
     * 该方法的作用在于根据题目id获取所有测试用例
     *
     * @param problemWithIdDTO 需要查询的题目id
     * @return 返回status为200，包含TestCases的list
     */
    @Override
    public TestCaseConsultDTO getTestCasesByProblemId(ProblemWithIdDTO problemWithIdDTO) {
        // TODO token处理

        // 查询TestCases
        return new TestCaseConsultDTO(200, testCaseDao.getTestCasesWithId(String.valueOf(problemWithIdDTO.getProblemId())));
    }

    /**
     * 该方法的作用在于为问题添加新的测试用例
     *
     * @param changeTestCaseDTO 需要添加的测试用例信息，无测试用例id
     * @return 返回status为200，testCases为空
     */
    @Override
    public TestCaseConsultDTO addTestCase(ChangeTestCaseDTO changeTestCaseDTO) {
        // TODO token处理

        // 添加新的测试用例
        TestCase testCase = changeTestCaseDTO.getTestCase();
        Long maxTestCaseId = testCaseDao.getMaxTestCaseId();
        if(maxTestCaseId == null){
            maxTestCaseId = 1L;
        } else {
            maxTestCaseId++;
        }
        Integer i = testCaseDao.saveNewTestCase(maxTestCaseId, testCase.getExpectedOutput(), testCase.getInput(), testCase.getProblemId());
        if(i == 1){
            return new TestCaseConsultDTO(200, null);
        }
        return new TestCaseConsultDTO(500, null);
    }

    /**
     * 该方法的作用在于为已经存在的测试用例进行修改
     *
     * @param changeTestCaseDTO 需要添加的测试用例信息，有测试用例id
     * @return 返回status为200，testCases为空
     */
    @Override
    public TestCaseConsultDTO updateTestCase(ChangeTestCaseDTO changeTestCaseDTO) {
        // TODO token处理

        // 添加新的测试用例
        TestCase testCase = changeTestCaseDTO.getTestCase();
        Integer i = testCaseDao.updateTestCase(testCase.getTestCaseId(), testCase.getExpectedOutput(), testCase.getInput());
        if(i == 1){
            return new TestCaseConsultDTO(200, null);
        }
        return new TestCaseConsultDTO(500, null);
    }

    /**
     * 该方法的作用在于为已经存在的测试用例进行删除
     *
     * @param testCaseWithIdDTO 需要添加的测试用例信息，有测试用例id
     * @return 返回status为200，testCases为空
     */
    @Override
    public TestCaseConsultDTO deleteTestCase(TestCaseWithIdDTO testCaseWithIdDTO) {
        // TODO token处理

        // 添加新的测试用例
        Integer i = testCaseDao.deleteTestCase(testCaseWithIdDTO.getTestCaseId());
        if(i == 1){
            return new TestCaseConsultDTO(200, null);
        }
        return new TestCaseConsultDTO(500, null);
    }

    /**
     * 该方法的作用在于根据教师id查询学生信息
     *
     * @param userDTO 需要查询的教师id
     * @return 返回status为200，包含user的list
     */
    @Override
    public StudentResultDTO getTeachingStudents(UserDTO userDTO) {
        System.out.println(userDTO);
        // TODO token处理

        // 获取教师教授班级的学生
        return new StudentResultDTO(200, userDao.getStudentsBySchoolId(userDTO.getSchoolId()));
    }

    /**
     * 该方法能根据教师id, 前往user数据表查询学生，再通过user数据查询记录表中的school_id
     *
     * @param userDTO 需要查询的教师id
     * @return 回status为200，包含submission_record的list
     */
    @Override
    public SubmissionHistoryDTO getAllStudentSubmissions(UserDTO userDTO) {
        // TODO token处理

        // 获取教师教授班级的学生
        List<User> students = userDao.getStudentsBySchoolId(userDTO.getSchoolId());
        // 获取学生的答题记录
        List<SubmissionRecords> submissionRecords = new ArrayList<SubmissionRecords>();
        for (User user : students){
            submissionRecords.addAll(submissionRecordsDao.getSubmissionRecordBySchoolId(user.getSchoolId()));
        }
        SubmissionHistoryDTO submissionHistoryDTO = new SubmissionHistoryDTO(200, submissionRecords);
        System.out.println(submissionHistoryDTO);
        return submissionHistoryDTO;
    }
}
