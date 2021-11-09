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
        String sads = "湖南作为我国一个高等教育大省，虽然没有湖北那么强势，但是省内优秀高校数量也不少，包括中南大学、湖南大学两所 985高校和湖南师大这样的211高校。当然也有不少优秀的双非高校，比如曾经的全国重点大学湘潭大学，还有长沙理工、湖南科大等高校，因此湖南的高校选择还是比较多的。\n" +
                "\n" +
                "\n" +
                "而湖南的高校也有一对冤家，那就是湘潭大学和湖南师范大学，湘潭大学作为曾经的全国重点大学，大家觉得湘潭大学无论是实力还是名气都对得起一个211的称号，但是湖南省属211名额却给了湖南师大，因此很多人心里也是愤愤不平。那么这两所高校到底谁更值得一个211的名额呢？我们一起来看看吧。\n" +
                "\n" +
                "\n" +
                "首先看最近出炉的泰晤士世界大学排名，在这份榜单中，湘潭大学位居106位，而湖南师大则位居173位，差距还是比较明显的。虽说每个榜单的判断依据不一样，但是基本上也是八九不离十，因此在外界看来，湘潭大学的综合实力还是更胜一筹的。\n" +
                "\n" +
                "\n" +
                "看过了排名再来看看学科博士点数量，毕竟博士点也是对一个学校实力最直观的一个反映，也是各个学校优势学科所在。湘潭大学拥有3个国家重点学科，同时拥有15个博士后科研流动站，15个一级学科博士点，这个数量基本上跟一个中下游211有得一拼了。而且它的数学、工程学、化学、材料科学进入全球前1%的行列，这种表现在双非里面也是属于翘楚。\n" +
                "\n" +
                "\n" +
                "看完了湘潭大学再来看看湖南师大，湖南师大拥有20个博士后科研流动站，21个博士学位授权一级学科、1个博士专业学位授权点，并且拥有国家重点学科6个，从这里可以看出，国家对于211高校和非211高校的待遇区别还是挺大的，因此湖南师大在一些重点建设的学科方面还是要优于湘潭大学的。\n" +
                "\n" +
                "\n" +
                "最后再来看看学科评估情况，以第四轮学科评估为例，湘潭大学的法学、马克思主义理论和数学获得B+的成绩，而另外有六个学科进入B档，实力也不算弱了。而湖南师大同样获得B+的学科数量为3个，分别是教育学、外国语言文学和数学，但是另外进入B档的学科数量高达16个，不得不称赞湖南师大虽然顶尖学科数量很少，但是整体学科质量也是不差的，虽然相对于其他211来说还是存在一定的差距。\n" +
                "\n" +
                "\n" +
                "看了这些，我们会觉得两所高校其实各有所长，而湖南师大进入211已经有20多个年头，所以也是得到了国家的大力扶持，因此发展的劲头也是更足一点。但是湘潭大学也不差，如果当初是它进入 211的话，那么如今的湘大肯定会更上一层楼。但是这些都没有如果，如今的湘大几乎上可以跟湖南师大平起平坐，这也是许多网友所认同的，因此希望两校都能借着双一流的东风，为湖南高等教育做出更多的贡献。\n" +
                "\n湖南作为我国一个高等教育大省，虽然没有湖北那么强势，但是省内优秀高校数量也不少，包括中南大学、湖南大学两所 985高校和湖南师大这样的211高校。当然也有不少优秀的双非高校，比如曾经的全国重点大学湘潭大学，还有长沙理工、湖南科大等高校，因此湖南的高校选择还是比较多的。\n" +
                "\n" +
                "\n" +
                "而湖南的高校也有一对冤家，那就是湘潭大学和湖南师范大学，湘潭大学作为曾经的全国重点大学，大家觉得湘潭大学无论是实力还是名气都对得起一个211的称号，但是湖南省属211名额却给了湖南师大，因此很多人心里也是愤愤不平。那么这两所高校到底谁更值得一个211的名额呢？我们一起来看看吧。\n" +
                "\n" +
                "\n" +
                "首先看最近出炉的泰晤士世界大学排名，在这份榜单中，湘潭大学位居106位，而湖南师大则位居173位，差距还是比较明显的。虽说每个榜单的判断依据不一样，但是基本上也是八九不离十，因此在外界看来，湘潭大学的综合实力还是更胜一筹的。\n" +
                "\n" +
                "\n" +
                "看过了排名再来看看学科博士点数量，毕竟博士点也是对一个学校实力最直观的一个反映，也是各个学校优势学科所在。湘潭大学拥有3个国家重点学科，同时拥有15个博士后科研流动站，15个一级学科博士点，这个数量基本上跟一个中下游211有得一拼了。而且它的数学、工程学、化学、材料科学进入全球前1%的行列，这种表现在双非里面也是属于翘楚。\n" +
                "\n" +
                "\n" +
                "看完了湘潭大学再来看看湖南师大，湖南师大拥有20个博士后科研流动站，21个博士学位授权一级学科、1个博士专业学位授权点，并且拥有国家重点学科6个，从这里可以看出，国家对于211高校和非211高校的待遇区别还是挺大的，因此湖南师大在一些重点建设的学科方面还是要优于湘潭大学的。\n" +
                "\n" +
                "\n" +
                "最后再来看看学科评估情况，以第四轮学科评估为例，湘潭大学的法学、马克思主义理论和数学获得B+的成绩，而另外有六个学科进入B档，实力也不算弱了。而湖南师大同样获得B+的学科数量为3个，分别是教育学、外国语言文学和数学，但是另外进入B档的学科数量高达16个，不得不称赞湖南师大虽然顶尖学科数量很少，但是整体学科质量也是不差的，虽然相对于其他211来说还是存在一定的差距。\n" +
                "\n" +
                "\n" +
                "看了这些，我们会觉得两所高校其实各有所长，而湖南师大进入211已经有20多个年头，所以也是得到了国家的大力扶持，因此发展的劲头也是更足一点。但是湘潭大学也不差，如果当初是它进入 211的话，那么如今的湘大肯定会更上一层楼。但是这些都没有如果，如今的湘大几乎上可以跟湖南师大平起平坐，这也是许多网友所认同的，因此希望两校都能借着双一流的东风，为湖南高等教育做出更多的贡献。\n" +
                "\n";
        return PageList.of(rankingListVos,page);
    }
}
