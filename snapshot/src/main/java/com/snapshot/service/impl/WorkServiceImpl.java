package com.snapshot.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.snapshot.dao.EvaluationDao;
import com.snapshot.dao.UserDao;
import com.snapshot.dao.WorkDao;
import com.snapshot.dto.request.WorkQuery;
import com.snapshot.dto.response.WorkHomeVo;
import com.snapshot.enums.EvaluationState;
import com.snapshot.enums.WorkState;
import com.snapshot.exception.ServiceException;
import com.snapshot.pojo.Evaluation;
import com.snapshot.pojo.PageList;
import com.snapshot.pojo.User;
import com.snapshot.pojo.Work;
import com.snapshot.security.JwtUser;
import com.snapshot.service.WorkService;
import com.snapshot.utils.SecurityUtils;
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
    private final EvaluationDao evaluationDao;

    public WorkServiceImpl(WorkDao workDao, UserDao userDao, ModelMapper modelMapper, EvaluationDao evaluationDao) {

        this.workDao = workDao;
        this.userDao = userDao;
        this.modelMapper = modelMapper;
        this.evaluationDao = evaluationDao;
    }


    @Override
    public Long addWork(Work work) {
        // 获取登录用户
        JwtUser loginUser = SecurityUtils.getLoginUser();
        if (Objects.isNull(loginUser)) {
            throw new ServiceException("请先登陆",400);
        }
        User user = userDao.getById(loginUser.getId());
        if (Objects.isNull(user)) {
            throw new ServiceException("用户不存在",400);
        }
        userDao.getById(user.getId());
        work.setCreatorId(user.getId());
        work.setCreatorType(user.userType);
        workDao.save(work);
        //添加积分
        userDao.lambdaUpdate().set(User::getPoint,user.getPoint()+5).eq(User::getId,user.getId()).update();
        return work.getId();
    }

    @Override
    public Boolean updateVideoTeaching(Work work) {
        return workDao.updateById(work);
    }

    @Override
    public PageList<WorkHomeVo> videoTeachingList(WorkQuery query) {
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
            User user = userDao.getById(workHomeVo.getCreatorId());
            if (Objects.nonNull(user)) {
                //获取发布者头像
                workHomeVo.setAvatarImage(user.getAvatarImage());
                workHomeVo.setUsername(user.getUsername());
            }else {
                workHomeVo.setAvatarImage("");
                workHomeVo.setUsername("该用户已注销");
            }
            //查询评论数
            Integer count = evaluationDao.lambdaQuery().eq(Evaluation::getWorkId, workHomeVo.getId()).count();
            workHomeVo.setComments(count);
            workHomeVos.add(workHomeVo);
        }
        return PageList.of(workHomeVos,page);
    }

    @Override
    public Boolean deletedVideoTeaching(Long id) {
        return workDao.removeById(id);
    }

    @Override
    public WorkHomeVo getVideoTeachingById(Long id) {
        Work work = workDao.getById(id);
        if (Objects.isNull(work)) {
            throw new ServiceException("修改失败",400);
        }
        WorkHomeVo workHomeVo = modelMapper.map(work,WorkHomeVo.class);
        User user = userDao.getById(workHomeVo.getCreatorId());
        if (Objects.nonNull(user)) {
            //获取发布者头像
            workHomeVo.setUsername("热心市民");
            workHomeVo.setAvatarImage(user.getAvatarImage());
        }else {
            workHomeVo.setAvatarImage("");
            workHomeVo.setUsername("该用户已注销");
        }
        //查询评论数
        Integer count = evaluationDao.lambdaQuery().eq(Evaluation::getWorkId, workHomeVo.getId()).count();
        workHomeVo.setComments(count);
        return workHomeVo;
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
        lambdaQuery.eq(Work::getStatus,WorkState.PASS);
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
            User user = userDao.getById(workHomeVo.getCreatorId());
            if (Objects.nonNull(user)) {
                //获取发布者头像
                workHomeVo.setUsername("热心市民");
                workHomeVo.setAvatarImage(user.getAvatarImage());
            }else {
                workHomeVo.setAvatarImage("");
                workHomeVo.setUsername("该用户已注销");
            }
            //查询评论数
            Integer count = evaluationDao.lambdaQuery()
                    .eq(Evaluation::getWorkId, workHomeVo.getId())
                    .eq(Evaluation::getStatus, EvaluationState.PASS).count();
            workHomeVo.setComments(count);
            workHomeVos.add(workHomeVo);
        }
        return PageList.of(workHomeVos,page);
    }
}
