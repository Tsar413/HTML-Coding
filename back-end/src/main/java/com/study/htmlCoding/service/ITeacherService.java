package com.study.htmlCoding.service;

import com.study.htmlCoding.dto.*;

public interface ITeacherService {
    ProblemConsultDTO getAllProblems(UserDTO userDTO);

    ProblemConsultDTO addProblem(ChangeProblemDTO changeProblemDTO);

    ProblemConsultDTO updateProblem(ChangeProblemDTO changeProblemDTO);

    ProblemConsultDTO deleteProblem(ProblemWithIdDTO problemWithIdDTO);

    TestCaseConsultDTO getTestCasesByProblemId(ProblemWithIdDTO problemWithIdDTO);

    TestCaseConsultDTO addTestCase(ChangeTestCaseDTO changeTestCaseDTO);

    TestCaseConsultDTO updateTestCase(ChangeTestCaseDTO changeTestCaseDTO);

    TestCaseConsultDTO deleteTestCase(TestCaseWithIdDTO testCaseWithIdDTO);

    StudentResultDTO getTeachingStudents(UserDTO userDTO);

    SubmissionHistoryDTO getAllStudentSubmissions(UserDTO userDTO);
}
