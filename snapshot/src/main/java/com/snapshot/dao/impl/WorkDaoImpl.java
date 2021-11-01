package com.snapshot.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snapshot.dao.WorkDao;
import com.snapshot.mapper.WorkMapper;
import com.snapshot.pojo.Work;
import org.springframework.stereotype.Repository;

/**
 * @author Chan
 */
@Repository
public class WorkDaoImpl extends ServiceImpl<WorkMapper, Work> implements WorkDao {
}
