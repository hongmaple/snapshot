package com.withpat.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.withpat.dao.WorkDao;
import com.withpat.dto.request.WorkQuery;
import com.withpat.exception.ServiceException;
import com.withpat.pojo.PageList;
import com.withpat.pojo.Work;
import com.withpat.security.JwtUser;
import com.withpat.service.WorkService;
import com.withpat.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author haiyan
 */
@Service
public class WorkServiceImpl implements WorkService {
    private final WorkDao workDao;

    public WorkServiceImpl(WorkDao workDao) {
        this.workDao = workDao;
    }


    @Override
    public Long addVideoTeaching(Work work) {
        // 获取登录用户
        JwtUser user = SecurityUtils.getLoginUser();
        if (Objects.isNull(user)) {
            throw new ServiceException("请先登陆",400);
        }
        work.setCreatorId(user.getId());
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
