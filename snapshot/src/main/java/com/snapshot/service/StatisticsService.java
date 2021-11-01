package com.snapshot.service;

import com.snapshot.dto.response.AgeAnalysisVo;
import com.snapshot.dto.response.StatisticsTopVo;

import java.util.List;

/**
 * @author Chan
 */
public interface StatisticsService {

    /**
     * 首页 top 统计
     * @return 统计数据
     */
    StatisticsTopVo getStatisticsTopVo();

    /**
     * 首页年龄分布统计
     * @return 年龄分布统计
     */
    List<AgeAnalysisVo> getAgeAnalysisVo();

}
