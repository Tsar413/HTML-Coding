package com.study.htmlCoding.controller;

import com.study.htmlCoding.dto.LoginResultDTO;
import com.study.htmlCoding.dto.LoginRequestDTO;
import com.study.htmlCoding.service.ILoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Resource
    private ILoginService iLoginService;

    @PostMapping("/login")
    public LoginResultDTO login(@RequestBody LoginRequestDTO loginRequestDTO){
        return iLoginService.login(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
    }
}
