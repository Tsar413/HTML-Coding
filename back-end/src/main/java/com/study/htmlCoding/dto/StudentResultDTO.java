package com.study.htmlCoding.dto;

import com.study.htmlCoding.entity.User;

import java.util.List;

public class StudentResultDTO {

    private Integer status;

    private List<User> students;

    public StudentResultDTO() {
    }

    public StudentResultDTO(Integer status, List<User> students) {
        this.status = status;
        this.students = students;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<User> getStudents() {
        return students;
    }

    public void setStudents(List<User> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "StudentResultDTO{" +
                "status=" + status +
                ", students=" + students +
                '}';
    }
}
