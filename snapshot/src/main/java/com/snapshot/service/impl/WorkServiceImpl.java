package com.snapshot.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.snapshot.dao.UserDao;
import com.snapshot.dao.WorkDao;
import com.snapshot.dto.request.WorkQuery;
import com.snapshot.dto.response.WorkHomeVo;
import com.snapshot.enums.WorkState;
import com.snapshot.exception.ServiceException;
import com.snapshot.pojo.PageList;
import com.snapshot.pojo.User;
import com.snapshot.pojo.Work;
import com.snapshot.security.JwtUser;
import com.snapshot.service.WorkService;
import com.snapshot.utils.SecurityUtils;
import jdk.nashorn.internal.runtime.linker.LinkerCallSite;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author Chan
 */
@Service
public class WorkServiceImpl implements WorkService {
    private final WorkDao workDao;
    private final UserDao userDao;
    private final ModelMapper modelMapper;

    public WorkServiceImpl(WorkDao workDao, UserDao userDao, ModelMapper modelMapper) {

        this.workDao = workDao;
        this.userDao = userDao;
        this.modelMapper = modelMapper;
    }


    @Override
    public Long addWork(Work work) {
        // 获取登录用户
        JwtUser user = SecurityUtils.getLoginUser();
        if (Objects.isNull(user)) {
            throw new ServiceException("请先登陆",400);
        }
        work.setCreatorId(user.getId());
        work.setCreatorType(userDao.getById(user.getId()).userType);
        workDao.save(work);
        //添加积分
        userDao.lambdaUpdate().set(User::getPoint,5).eq(User::getId,user.getId()).update();
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

    @Override
    public Boolean updateStatus(Long id, WorkState state) {
        boolean update = workDao.lambdaUpdate().set(Work::getStatus, state).eq(Work::getId, id).update();
        if (!update) {
            throw new ServiceException("修改失败",400);
        }
        return update;
    }

    @Override
    public PageList<WorkHomeVo> queryWorkListByWorkType(WorkQuery query) {
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
        if (query.getWorkType()!=null) {
            lambdaQuery.eq(Work::getWorkType,query.getWorkType());
        }
        if (query.getStatus()!=null) {
            lambdaQuery.eq(Work::getStatus,query.getStatus());
        }
        Page<Work> page = lambdaQuery.page(new Page<>(query.getPageNum(), query.getPageSize()));
        List<WorkHomeVo> workHomeVos = new LinkedList<>();
        for (Work work:page.getRecords()) {
            WorkHomeVo workHomeVo = modelMapper.map(work, WorkHomeVo.class);
            workHomeVo.setUsername("热心市民");
            User user = userDao.getById(workHomeVo.getCreatorId());
            if (Objects.nonNull(user)) {
                workHomeVo.setAvatarImage(user.getAvatarImage());
            }
            workHomeVos.add(workHomeVo);
        }
        return PageList.of(workHomeVos,page);
    }
}
