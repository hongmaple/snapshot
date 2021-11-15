package com.snapshot.service;

import com.snapshot.dto.response.AgeAnalysisVo;
import com.snapshot.dto.response.RankingListVo;
import com.snapshot.dto.response.StatisticsTopVo;
import com.snapshot.dto.response.WxinMineInfoVo;
import com.snapshot.pojo.PageDomain;
import com.snapshot.pojo.PageList;

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

    /**
     * 排行榜
     * @param query
     * @return
     */
    PageList<RankingListVo> queryRankingList(PageDomain query);

    /**
     * 文明点赞/曝光台审核情况统计
     * @return
     */
    WxinMineInfoVo queryWxinMineInfoVo();
}
