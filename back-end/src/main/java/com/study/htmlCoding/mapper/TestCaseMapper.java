package com.study.htmlCoding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.htmlCoding.entity.TestCase;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestCaseMapper extends BaseMapper<TestCase> {
    public Long getMaxTestCaseId();

    public int deleteTestCase(Long testCaseId);
}
