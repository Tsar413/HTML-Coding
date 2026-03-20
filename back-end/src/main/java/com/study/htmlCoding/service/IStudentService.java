package com.study.htmlCoding.service;

import com.study.htmlCoding.dto.*;

public interface IStudentService {
    CodeResultDTO submitCode(CodeRequestDTO codeRequestDTO);

    ProblemConsultDTO getAllProblems(UserDTO userDTO);

    SubmissionHistoryDTO getSubmissions(CodeRequestDTO codeRequestDTO);

    SubmissionHistoryDTO getAllSubmissions(UserDTO userDTO);
}
