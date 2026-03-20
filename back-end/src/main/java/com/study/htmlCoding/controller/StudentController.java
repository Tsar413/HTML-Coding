package com.study.htmlCoding.controller;

import com.study.htmlCoding.dto.*;
import com.study.htmlCoding.entity.Problem;
import com.study.htmlCoding.entity.SubmissionRecords;
import com.study.htmlCoding.service.IStudentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Resource
    private IStudentService iStudentService;

    @PostMapping("/submitCode")
    public CodeResultDTO submitCode(@RequestBody CodeRequestDTO codeRequestDTO){
        return iStudentService.submitCode(codeRequestDTO);
    }

    @PostMapping("/getAllProblems")
    public ProblemConsultDTO getAllProblems(@RequestBody UserDTO userDTO){
        return iStudentService.getAllProblems(userDTO);
    }

    @PostMapping("/getSubmissions")
    public SubmissionHistoryDTO getSubmissions(@RequestBody CodeRequestDTO codeRequestDTO){
        return iStudentService.getSubmissions(codeRequestDTO);
    }

    @PostMapping("/getAllSubmissions")
    public SubmissionHistoryDTO getAllSubmissions(@RequestBody UserDTO userDTO){
        return iStudentService.getAllSubmissions(userDTO);
    }
}
