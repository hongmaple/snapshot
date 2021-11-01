package com.snapshot.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.snapshot.dao.UserDao;
import com.snapshot.dao.WorkDao;
import com.snapshot.dto.request.WorkQuery;
import com.snapshot.exception.ServiceException;
import com.snapshot.pojo.PageList;
import com.snapshot.pojo.Work;
import com.snapshot.security.JwtUser;
import com.snapshot.service.WorkService;
import com.snapshot.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author Chan
 */
@Service
public class WorkServiceImpl implements WorkService {
    private final WorkDao workDao;
    private final UserDao userDao;

    public WorkServiceImpl(WorkDao workDao,UserDao userDao) {

        this.workDao = workDao;
        this.userDao = userDao;
    }


    @Override
    public Long addVideoTeaching(Work work) {
        // 获取登录用户
        JwtUser user = SecurityUtils.getLoginUser();
        if (Objects.isNull(user)) {
            throw new ServiceException("请先登陆",400);
        }
        work.setCreatorId(user.getId());
        work.setCreatorType(userDao.getById(user.getId()).userType);
        workDao.save(work);
        return work.getId();
    }

    @Override
    public Boolean updateVideoTeaching(Work work) {
        return workDao.updateById(work);
    }

    @Override
    public PageList<Work> videoTeachingList(WorkQuery query) {
        LambdaQueryChainWrapper<Work> lambdaQuery = workDao.lambdaQuery();
        if (StringUtils.hasText(query.getTitle())) {
            lambdaQuery.like(Work::getTitle,query.getTitle());
        }
        if (query.getCreatorId()!=null) {
            lambdaQuery.eq(Work::getCreatorId,query.getCreatorId());
        }
        if (query.getCreatorType()!=null) {
            lambdaQuery.eq(Work::getCreatorType,query.getCreatorType());
        }
        Page<Work> page = lambdaQuery.page(new Page<>(query.getPageNum(), query.getPageSize()));
        return PageList.of(page.getRecords(),page);
    }

    @Override
    public Boolean deletedVideoTeaching(Long id) {
        return workDao.removeById(id);
    }

    @Override
    public Work getVideoTeachingById(Long id) {
        return workDao.getById(id);
    }
}
