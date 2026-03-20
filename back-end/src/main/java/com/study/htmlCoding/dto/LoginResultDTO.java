package com.study.htmlCoding.dto;

public class LoginResultDTO {
    private Integer status;
    private UserDTO userDTO;

    public LoginResultDTO() {
    }

    public LoginResultDTO(Integer status, UserDTO userDTO) {
        this.status = status;
        this.userDTO = userDTO;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    @Override
    public String toString() {
        return "LoginDTO{" +
                "status=" + status +
                ", userDTO=" + userDTO +
                '}';
    }
}
