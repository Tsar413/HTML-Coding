package com.study.htmlCoding.controller;

import com.study.htmlCoding.dto.*;
import com.study.htmlCoding.service.ITeacherService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

    @Resource
    private ITeacherService iTeacherService;

    @PostMapping("/getAllProblems")
    public ProblemConsultDTO getAllProblems(@RequestBody UserDTO userDTO){
        return iTeacherService.getAllProblems(userDTO);
    }

    @PostMapping("/addProblem")
    public ProblemConsultDTO addProblem(@RequestBody ChangeProblemDTO changeProblemDTO){
        return iTeacherService.addProblem(changeProblemDTO);
    }

    @PostMapping("/updateProblem")
    public ProblemConsultDTO updateProblem(@RequestBody ChangeProblemDTO changeProblemDTO){
        return iTeacherService.updateProblem(changeProblemDTO);
    }

    @PostMapping("/deleteProblem")
    public ProblemConsultDTO deleteProblem(@RequestBody ProblemWithIdDTO problemWithIdDTO){
        return iTeacherService.deleteProblem(problemWithIdDTO);
    }

    @PostMapping("/getTestCasesByProblemId")
    public TestCaseConsultDTO getTestCasesByProblemId(@RequestBody ProblemWithIdDTO problemWithIdDTO){
        return iTeacherService.getTestCasesByProblemId(problemWithIdDTO);
    }

    @PostMapping("/addTestCase")
    public TestCaseConsultDTO addTestCase(@RequestBody ChangeTestCaseDTO changeTestCaseDTO){
        return iTeacherService.addTestCase(changeTestCaseDTO);
    }

    @PostMapping("/updateTestCase")
    public TestCaseConsultDTO updateTestCase(@RequestBody ChangeTestCaseDTO changeTestCaseDTO){
        return iTeacherService.updateTestCase(changeTestCaseDTO);
    }

    @PostMapping("/deleteTestCase")
    public TestCaseConsultDTO deleteTestCase(@RequestBody TestCaseWithIdDTO testCaseWithIdDTO){
        return iTeacherService.deleteTestCase(testCaseWithIdDTO);
    }

    @PostMapping("/getTeachingStudents")
    public StudentResultDTO getTeachingStudents(@RequestBody UserDTO userDTO){
        return iTeacherService.getTeachingStudents(userDTO);
    }

    @PostMapping("/getAllStudentSubmissions")
    public SubmissionHistoryDTO getAllStudentSubmissions(@RequestBody UserDTO userDTO){
        return iTeacherService.getAllStudentSubmissions(userDTO);
    }
}
