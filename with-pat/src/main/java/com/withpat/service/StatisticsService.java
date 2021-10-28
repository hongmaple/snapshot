package com.withpat.service;

import com.withpat.dto.response.AgeAnalysisVo;
import com.withpat.dto.response.FlowerRankingVo;
import com.withpat.dto.response.RegionalDistributionVo;
import com.withpat.dto.response.StatisticsTopVo;

import java.util.List;

/**
 * @author maple
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
