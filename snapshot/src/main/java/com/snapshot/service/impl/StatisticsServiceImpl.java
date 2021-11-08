package com.snapshot.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.snapshot.dao.*;
import com.snapshot.dto.response.AgeAnalysisVo;
import com.snapshot.dto.response.RankingListVo;
import com.snapshot.dto.response.StatisticsTopVo;
import com.snapshot.exception.ServiceException;
import com.snapshot.pojo.PageDomain;
import com.snapshot.pojo.PageList;
import com.snapshot.pojo.User;
import com.snapshot.pojo.Work;
import com.snapshot.security.JwtUser;
import com.snapshot.service.StatisticsService;
import com.snapshot.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * @author Chan
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final WorkDao workDao;
    private final UserDao userDao;

    public StatisticsServiceImpl(WorkDao workDao, UserDao userDao) {
        this.workDao = workDao;
        this.userDao = userDao;
    }


    @Override
    public StatisticsTopVo getStatisticsTopVo() {
        StatisticsTopVo statisticsTopVo = new StatisticsTopVo();
        statisticsTopVo.setNumberVideo(workDao.lambdaQuery().count());
        statisticsTopVo.setTotalNumberRegistrants(userDao.lambdaQuery().count());
        Integer menCount = userDao.lambdaQuery().eq(User::getSex, "男").count();
        Integer womenCount = userDao.lambdaQuery().eq(User::getSex, "女").count();
        statisticsTopVo.setMenThan(Chufa(menCount,statisticsTopVo.getTotalNumberRegistrants())*100);
        statisticsTopVo.setWomenThan(Chufa(womenCount,statisticsTopVo.getTotalNumberRegistrants())*100);
        LocalDateTime today_start = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime today_end = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        statisticsTopVo.setNewRegistrations(userDao.lambdaQuery().between(User::getCreateTime,today_start,today_end).count());
        return statisticsTopVo;
    }

    /**
     *
     * @param a
     * @param b
     * @return
     */
    public static double Chufa(int a,int b) {
        double jieguo = new BigDecimal((float)a/b).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return jieguo;
    }

    @Override
    public List<AgeAnalysisVo> getAgeAnalysisVo() {
        List<AgeAnalysisVo> ageAnalysisVos = new ArrayList<>();
        AgeAnalysisVo ageAnalysisVo = new AgeAnalysisVo();
        ageAnalysisVo.setName("15~25岁");
        ageAnalysisVo.setValue(userDao.lambdaQuery().between(User::getAge,15,25).count());
        if (ageAnalysisVo.getValue()>0) {
            ageAnalysisVos.add(ageAnalysisVo);
        }
        AgeAnalysisVo ageAnalysisVo1 = new AgeAnalysisVo();
        ageAnalysisVo1.setName("25~35岁");
        ageAnalysisVo1.setValue(userDao.lambdaQuery().between(User::getAge,25,35).count());
        if (ageAnalysisVo1.getValue()>0) {
            ageAnalysisVos.add(ageAnalysisVo1);
        }
        AgeAnalysisVo ageAnalysisVo2 = new AgeAnalysisVo();
        ageAnalysisVo2.setName("35~45岁");
        ageAnalysisVo2.setValue(userDao.lambdaQuery().between(User::getAge,35,45).count());
        if (ageAnalysisVo2.getValue()>0) {
            ageAnalysisVos.add(ageAnalysisVo2);
        }
        AgeAnalysisVo ageAnalysisVo3 = new AgeAnalysisVo();
        ageAnalysisVo3.setName("45~55岁");
        ageAnalysisVo3.setValue(userDao.lambdaQuery().between(User::getAge,45,55).count());
        if (ageAnalysisVo3.getValue()>0) {
            ageAnalysisVos.add(ageAnalysisVo3);
        }
        AgeAnalysisVo ageAnalysisVo4 = new AgeAnalysisVo();
        ageAnalysisVo4.setName("55~65岁");
        ageAnalysisVo4.setValue(userDao.lambdaQuery().between(User::getAge,55,65).count());
        if (ageAnalysisVo4.getValue()>0) {
            ageAnalysisVos.add(ageAnalysisVo4);
        }
        AgeAnalysisVo ageAnalysisVo5 = new AgeAnalysisVo();
        ageAnalysisVo5.setName("65~75岁");
        ageAnalysisVo5.setValue(userDao.lambdaQuery().between(User::getAge,65,75).count());
        if (ageAnalysisVo5.getValue()>0) {
            ageAnalysisVos.add(ageAnalysisVo5);
        }
        AgeAnalysisVo ageAnalysisVo6 = new AgeAnalysisVo();
        ageAnalysisVo6.setName("75~85岁");
        ageAnalysisVo6.setValue(userDao.lambdaQuery().between(User::getAge,75,85).count());
        if (ageAnalysisVo6.getValue()>0) {
            ageAnalysisVos.add(ageAnalysisVo6);
        }
        AgeAnalysisVo ageAnalysisVo7 = new AgeAnalysisVo();
        ageAnalysisVo7.setName("85岁以上");
        ageAnalysisVo7.setValue(userDao.lambdaQuery().between(User::getAge,85,100000).count());
        if (ageAnalysisVo7.getValue()>0) {
            ageAnalysisVos.add(ageAnalysisVo7);
        }
        return ageAnalysisVos;
    }

    @Override
    public PageList<RankingListVo> queryRankingList(PageDomain query) {
        // 获取登录用户
        JwtUser loginUser = null;
        try {
            loginUser = SecurityUtils.getLoginUser();
        } catch (Exception e) {
            System.out.println("获取用户信息异常");
        }
        LambdaQueryChainWrapper<User> lambdaQuery = userDao.lambdaQuery();
        lambdaQuery.orderByDesc(User::getPoint);
        lambdaQuery.eq(User::getUserType,0);
        Page<User> page = lambdaQuery.page(new Page<>(query.getPageNum(), query.getPageSize()));
        List<RankingListVo> rankingListVos = new LinkedList<>();
        List<User> records = page.getRecords();
        for (int i=1;i<=records.size();i++) {
            User user = records.get(i-1);
            RankingListVo rankingListVo = new RankingListVo();
            rankingListVo.setRankingIndex(i);
            rankingListVo.setUsername(user.getUsername());
            rankingListVo.setAvatarImage(user.getAvatarImage());
            rankingListVo.setPoint(user.getPoint());
            if (Objects.nonNull(loginUser)) {
                rankingListVo.setIsMy(loginUser.getId().equals(user.getId()));
            }else {
                rankingListVo.setIsMy(false);
            }
            rankingListVos.add(rankingListVo);
        }
        return PageList.of(rankingListVos,page);
    }
}
