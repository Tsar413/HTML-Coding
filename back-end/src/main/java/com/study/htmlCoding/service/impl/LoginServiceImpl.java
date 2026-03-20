package com.study.htmlCoding.service.impl;

import com.study.htmlCoding.dao.UserDao;
import com.study.htmlCoding.dto.LoginResultDTO;
import com.study.htmlCoding.dto.UserDTO;
import com.study.htmlCoding.entity.User;
import com.study.htmlCoding.service.ILoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

@Service
public class LoginServiceImpl implements ILoginService {

    @Resource
    private UserDao userDao;

    @Override
    public LoginResultDTO login(String username, String password) {
        System.out.println(username);
        User user = userDao.login(username, password);
        System.out.println(user);
        if(user == null){
            return new LoginResultDTO(401, null);
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getSchoolId());
        userDTO.setUsername(user.getUsername());
        userDTO.setIdentity(user.getIdentity());
        userDTO.setSchoolId(user.getSchoolId());
        userDTO.setToken(String.valueOf(UUID.randomUUID()));
        System.out.println(userDTO);
        LoginResultDTO loginResultDTO = new LoginResultDTO(200, userDTO);
        return loginResultDTO;
    }
}
