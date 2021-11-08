package com.snapshot.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.snapshot.dao.UserDao;
import com.snapshot.dto.request.UpdatePwdReq;
import com.snapshot.enums.UserState;
import com.snapshot.exception.ServiceException;
import com.snapshot.mapper.UserMapper;
import com.snapshot.pojo.PageDomain;
import com.snapshot.pojo.PageList;
import com.snapshot.pojo.User;
import com.snapshot.security.JwtUser;
import com.snapshot.service.UserService;
import com.snapshot.utils.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * @author Chan
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserDao userDao;

    public UserServiceImpl(UserMapper userMapper, UserDao userDao) {
        this.userMapper = userMapper;
        this.userDao = userDao;
    }

    @Override
    public Boolean register(User user) {
        if(userDao.lambdaQuery().eq(User::getPhone,user.getPhone()).count()>0) {
            throw new ServiceException("注册失败，手机号码已存在",400);
        }
        user.setUsername(user.getPhone().substring(6,11));
        user.setUserType(0);
        user.setRole("consumer");
        user.setCreateTime(new Date());
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        if (userMapper.insert(user)==0) {
            throw new ServiceException("注册失败",400);
        }
        return true;
    }

    @Override
    public Boolean adminRegister(User user) {
        if(userDao.lambdaQuery().eq(User::getPhone,user.getPhone()).count()>0) {
            throw new ServiceException("注册失败，手机号码已存在",400);
        }
        if(userDao.lambdaQuery().eq(User::getUsername,user.getUsername()).count()>0) {
            throw new ServiceException("注册失败，用户名已存在",400);
        }
        user.setUsername(user.getPhone().substring(6,11));
        user.setUserType(1);
        user.setRole("admin");
        user.setCreateTime(new Date());
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        if (userMapper.insert(user)==0) {
            throw new ServiceException("注册失败",400);
        }
        return true;
    }

    @Override
    public Boolean updateUser(User user) {
        JwtUser loginUser = SecurityUtils.getLoginUser();
        if (userDao.lambdaQuery().eq(User::getId,loginUser.getId()).count()==0) {
            throw new ServiceException("该用户不存在",400);
        }
        if(userDao.lambdaQuery().eq(User::getPhone,user.getPhone()).ne(User::getId,loginUser.getId()).count()>0) {
            throw new ServiceException("修改失败，手机号码已存在",400);
        }
        if(userDao.lambdaQuery().eq(User::getUsername,user.getUsername()).ne(User::getId,loginUser.getId()).count()>0) {
            throw new ServiceException("修改失败，用户名已存在",400);
        }
        //给密码设置加密
        if (user.getPassword()!=null&& StringUtils.isNoneEmpty(user.getPassword())) {
            user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        }else {
            user.setPassword(null);
        }
        if(!userDao.updateById(user)) {
            throw new ServiceException("修改失败",400);
        }
        return true;
    }

    @Override
    public Boolean adminUpdateUser(User user) {
        JwtUser loginUser = SecurityUtils.getLoginUser();
        if (userDao.lambdaQuery().eq(User::getId,loginUser.getId()).count()==0) {
            throw new ServiceException("该用户不存在",400);
        }
        //给密码设置加密
        if (user.getPassword()!=null&& StringUtils.isNoneEmpty(user.getPassword())) {
            user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        }else {
            user.setPassword(null);
        }
        if(!userDao.updateById(user)) {
            throw new ServiceException("修改失败",400);
        }
        return true;
    }

    @Override
    public PageList<User> ListUser(PageDomain pageDomain) {
        Page<User> page = userDao.lambdaQuery().eq(User::getUserType,0).page(new Page<>(pageDomain.getPageNum(), pageDomain.getPageSize()));
        return PageList.of(page.getRecords(),page);
    }

    @Override
    public PageList<User> AdminListUser(PageDomain pageDomain) {
        Page<User> page = userDao.lambdaQuery().eq(User::getUserType,1).page(new Page<>(pageDomain.getPageNum(), pageDomain.getPageSize()));
        return PageList.of(page.getRecords(),page);
    }

    @Override
    public Boolean deletedUser(Long id) {
        JwtUser loginUser = SecurityUtils.getLoginUser();
        if(loginUser.getId()!=1)
        throw new ServiceException("无此操作权限",401);
        if (userDao.lambdaQuery().eq(User::getId,loginUser.getId()).count()==0) {
            throw new ServiceException("当前用户不存在",400);
        }
        if(userDao.removeById(id)) {
            throw new ServiceException("删除失败",400);
        }
        return true;
    }

    @Override
    public User getUserInfo() {
        JwtUser loginUser = SecurityUtils.getLoginUser();
        return userDao.getById(loginUser.getId());
    }

    @Override
    public User getUserByName(String userName) {
        return userDao.lambdaQuery()
                .eq(User::getPhone,userName)
                .eq(User::getStatus, UserState.valid)
                .one();
    }

    @Override
    public Boolean updatePwdReq(UpdatePwdReq updatePwdReq) {
        JwtUser loginUser = SecurityUtils.getLoginUser();
        User user = userDao.lambdaQuery()
                .eq(User::getId, loginUser.getId())
                .select(User::getPassword)
                .one();

        if (!SecurityUtils.matchesPassword(updatePwdReq.getPassword(),user.getPassword())) {
            throw new ServiceException("原密码不正确",400);
        }
        boolean update = userDao.lambdaUpdate()
                .set(User::getPassword, SecurityUtils.encryptPassword(updatePwdReq.getPassword2()))
                .eq(User::getId, loginUser.getId())
                .update();
        if (!update) {
            throw new ServiceException("修改密码失败",400);
        }
        return update;
    }
}
