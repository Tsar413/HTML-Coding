package com.study.htmlCoding.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.htmlCoding.dto.LoginResultDTO;
import com.study.htmlCoding.dto.UserDTO;
import com.study.htmlCoding.entity.User;
import com.study.htmlCoding.mapper.UserMapper;
import com.study.htmlCoding.service.ILoginService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LoginServiceImpl extends ServiceImpl<UserMapper, User> implements ILoginService {

    @Override
    public LoginResultDTO login(String username, String password) {
        System.out.println(username);
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        wrapper.eq("username", username).eq("passwords", password);
        List<User> users = baseMapper.selectList(wrapper);
        if(users.isEmpty()){
            return new LoginResultDTO(401, null);
        }
        User user = users.get(0);
        System.out.println(user);
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
