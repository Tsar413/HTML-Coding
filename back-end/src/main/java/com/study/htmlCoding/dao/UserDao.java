package com.study.htmlCoding.dao;

import com.study.htmlCoding.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserDao extends JpaRepository<User, Integer> {
    @Query(value = "select * from tb_user where tb_user.username = ?1 and tb_user.passwords = ?2", nativeQuery = true)
    public User login(String username, String passwords);

    @Query(value = "select * from tb_user where tb_user.teacher_id = ?1", nativeQuery = true)
    public List<User> getStudentsBySchoolId(String teacherId);
}
