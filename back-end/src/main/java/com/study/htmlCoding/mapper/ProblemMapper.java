package com.study.htmlCoding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.htmlCoding.entity.Problem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProblemMapper extends BaseMapper<Problem> {
    public Integer getMaxProblemId();

    public int deleteProblem(Integer problemId);
}
