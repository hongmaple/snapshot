package com.snapshot.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snapshot.dao.EvaluationDao;
import com.snapshot.mapper.EvaluationMapper;

import com.snapshot.pojo.Evaluation;
import org.springframework.stereotype.Repository;

/**
 * @author Chan
 */
@Repository
public class EvaluationDaoImpl extends ServiceImpl<EvaluationMapper, Evaluation> implements EvaluationDao {
}
