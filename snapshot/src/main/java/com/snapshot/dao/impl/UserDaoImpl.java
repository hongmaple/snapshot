package com.snapshot.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snapshot.dao.UserDao;
import com.snapshot.mapper.UserMapper;
import com.snapshot.pojo.User;
import org.springframework.stereotype.Repository;

/**
 * @author Chan
 */
@Repository
public class UserDaoImpl extends ServiceImpl<UserMapper, User> implements UserDao {
}
