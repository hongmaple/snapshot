package com.withpat.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.withpat.dao.UserDao;
import com.withpat.mapper.UserMapper;
import com.withpat.pojo.User;
import org.springframework.stereotype.Repository;

/**
 * @author haiyan
 */
@Repository
public class UserDaoImpl extends ServiceImpl<UserMapper, User> implements UserDao {
}
