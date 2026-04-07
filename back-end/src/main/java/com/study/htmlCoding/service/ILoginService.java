package com.study.htmlCoding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.htmlCoding.dto.LoginResultDTO;
import com.study.htmlCoding.entity.User;

public interface ILoginService extends IService<User> {
    LoginResultDTO login(String username, String password);
}
