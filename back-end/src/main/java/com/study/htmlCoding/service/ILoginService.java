package com.study.htmlCoding.service;

import com.study.htmlCoding.dto.LoginResultDTO;

public interface ILoginService {
    LoginResultDTO login(String username, String password);
}
