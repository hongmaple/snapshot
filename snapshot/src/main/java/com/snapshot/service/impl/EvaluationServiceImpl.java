package com.snapshot.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.snapshot.dao.EvaluationDao;
import com.snapshot.dao.UserDao;
import com.snapshot.dto.request.EvaluationQuery;
import com.snapshot.dto.response.EvaluationRowVo;
import com.snapshot.exception.ServiceException;
import com.snapshot.pojo.Evaluation;
import com.snapshot.pojo.PageList;
import com.snapshot.pojo.User;
import com.snapshot.security.JwtUser;
import com.snapshot.service.EvaluationService;
import com.snapshot.utils.SecurityUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Chan
 */
@Service
public class EvaluationServiceImpl implements EvaluationService {
    private final EvaluationDao evaluationDao;
    private final UserDao userDao;
    private final ModelMapper modelMapper;

    public EvaluationServiceImpl(EvaluationDao evaluationDao, UserDao userDao, ModelMapper modelMapper) {
        this.evaluationDao = evaluationDao;
        this.userDao = userDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public Long saveEvaluation(Evaluation evaluation) {
        // 获取登录用户
        JwtUser user = SecurityUtils.getLoginUser();
        User loginUser = userDao.getById(user.getId());
        if (Objects.isNull(loginUser)) {
            throw new ServiceException("请先登陆",400);
        }
        evaluation.setCreatorId(loginUser.getId());
        evaluation.setCreatorType(loginUser.getUserType());
        if(!evaluationDao.save(evaluation)) {
            throw new ServiceException("评论失败",400);
        }
        return evaluation.getId();
    }

    @Override
    public PageList<EvaluationRowVo> queryEvaluationList(EvaluationQuery query) {
        Page<Evaluation> page = evaluationDao
                .lambdaQuery()
                .eq(Evaluation::getWorkId,query.getWorkId())
                .orderByDesc(Evaluation::getCreateTime)
                .page(new Page<>(query.getPageNum(), query.getPageSize()));
        List<EvaluationRowVo> evaluationRowVos = new ArrayList<>();
        page.getRecords().forEach(evaluation -> {
            EvaluationRowVo evaluationRowVo = modelMapper.map(evaluation,EvaluationRowVo.class);
            User creator = userDao.getById(evaluation.getCreatorId());
            if (!Objects.isNull(creator)) {
                evaluationRowVo.setCommentator(creator.getUsername());
                evaluationRowVo.setCommentatorAvatar(creator.getAvatarImage());
            }
            evaluationRowVos.add(evaluationRowVo);
        });
        return PageList.of(evaluationRowVos, page);
    }

    @Override
    public Integer countEvaluationByProdId(Long prodId) {
        return evaluationDao.lambdaQuery().eq(Evaluation::getWorkId,prodId).count();
    }
}
