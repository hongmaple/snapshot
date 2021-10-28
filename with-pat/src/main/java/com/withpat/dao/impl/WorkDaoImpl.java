package com.withpat.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.withpat.dao.WorkDao;
import com.withpat.mapper.WorkMapper;
import com.withpat.pojo.Work;
import org.springframework.stereotype.Repository;

/**
 * @author haiyan
 */
@Repository
public class WorkDaoImpl extends ServiceImpl<WorkMapper, Work> implements WorkDao {
}
