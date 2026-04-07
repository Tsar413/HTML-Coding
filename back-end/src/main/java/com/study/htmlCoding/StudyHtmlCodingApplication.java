package com.study.htmlCoding;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.study.htmlCoding.mapper")
public class StudyHtmlCodingApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyHtmlCodingApplication.class, args);
    }
}
