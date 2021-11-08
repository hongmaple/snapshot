package com.snapshot.controller;

import com.snapshot.dto.response.AgeAnalysisVo;
import com.snapshot.dto.response.RankingListVo;
import com.snapshot.dto.response.StatisticsTopVo;
import com.snapshot.pojo.AjaxResult;
import com.snapshot.pojo.PageDomain;
import com.snapshot.pojo.PageList;
import com.snapshot.service.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据统计
 *
 * @author Chan
 */
@Slf4j
@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    /**
     * 首页 top 统计
     * @return 统计数据
     */
    @GetMapping("/top")
    public StatisticsTopVo getStatisticsTopVo() {
        return statisticsService.getStatisticsTopVo();
    }

    /**
     * 首页年龄分布统计
     * @return 年龄分布统计
     */
    @GetMapping("/ageAnalysis")
    public List<AgeAnalysisVo> getAgeAnalysisVo() {
        return statisticsService.getAgeAnalysisVo();
    }

    /**
     * 排行榜
     * @param query
     * @return
     */
    @PostMapping("/ranking")
    public AjaxResult queryRankingList(@RequestBody PageDomain query) {
        AjaxResult ajaxResult = AjaxResult.success(statisticsService.queryRankingList(query));
        return ajaxResult;
    }
}
