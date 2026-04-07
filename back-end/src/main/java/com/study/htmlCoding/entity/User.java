package com.study.htmlCoding.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.persistence.*;

@Entity
@Table(name = "tb_user")
@TableName("tb_user")
public class User {
    @Id
    private Integer id;

    @Column(name = "school_id")
    @TableField("school_id")
    private String schoolId;

    @Column(name = "username")
    private String username;

    @Column(name = "passwords")
    private String passwords;

    @Column(name = "identity")
    private String identity;

    @Column(name = "class_id")
    @TableField("class_id")
    private String class_id;

    @Column(name = "teacher_id")
    @TableField("teacher_id")
    private String teacher_id;

    public User() {
    }

    public User(Integer id, String schoolId, String username, String passwords, String identity, String class_id, String teacher_id) {
        this.id = id;
        this.schoolId = schoolId;
        this.username = username;
        this.passwords = passwords;
        this.identity = identity;
        this.class_id = class_id;
        this.teacher_id = teacher_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswords() {
        return passwords;
    }

    public void setPasswords(String passwords) {
        this.passwords = passwords;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", schoolId='" + schoolId + '\'' +
                ", username='" + username + '\'' +
                ", passwords='" + passwords + '\'' +
                ", identity='" + identity + '\'' +
                ", class_id='" + class_id + '\'' +
                ", teacher_id='" + teacher_id + '\'' +
                '}';
    }
}
