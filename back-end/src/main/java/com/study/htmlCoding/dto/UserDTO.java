package com.study.htmlCoding.dto;

public class UserDTO {
    private String id;
    private String schoolId;
    private String username;
    private String identity;
    private String token;

    public UserDTO() {
    }

    public UserDTO(String id, String schoolId, String username, String identity, String token) {
        this.id = id;
        this.schoolId = schoolId;
        this.username = username;
        this.identity = identity;
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id='" + id + '\'' +
                ", schoolId='" + schoolId + '\'' +
                ", username='" + username + '\'' +
                ", identity='" + identity + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
