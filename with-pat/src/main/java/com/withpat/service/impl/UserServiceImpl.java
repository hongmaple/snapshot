package com.withpat.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.withpat.dao.UserDao;
import com.withpat.enums.UserState;
import com.withpat.exception.ServiceException;
import com.withpat.mapper.UserMapper;
import com.withpat.pojo.PageDomain;
import com.withpat.pojo.PageList;
import com.withpat.pojo.User;
import com.withpat.security.JwtUser;
import com.withpat.service.UserService;
import com.withpat.utils.CookieUtils;
import com.withpat.utils.SecurityUtils;
import com.withpat.utils.ServletUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Objects;

/**
 * @author haiyan
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
    public User login(User user) {
        if (userDao.lambdaQuery()
                .eq(User::getPhone,user.getPhone())
                .count()==0) {
            throw new ServiceException("登陆失败，手机号码不存在",400);
        }
        User loginUser = userDao.lambdaQuery()
                .eq(User::getPhone, user.getPhone())
                .eq(User::getPassword, user.getPassword())
                .one();
        if(Objects.isNull(loginUser)) {
            throw new ServiceException("登陆失败，密码不正确",400);
        }
        HttpSession session = ServletUtils.getSession();
        session.setAttribute("token", loginUser);
        //session过期时间设置，以秒为单位，即在没有活动120分钟后，session将失效
        session.setMaxInactiveInterval(120 * 60);
        CookieUtils.setCookie(ServletUtils.getRequest(),ServletUtils.getResponse(),"token", JSON.toJSONString(loginUser),1);
        return loginUser;
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
        User up_user = userDao.lambdaQuery().eq(User::getPhone, user.getPhone()).one();
        if (Objects.nonNull(up_user)) {
            if(up_user.getId().equals(loginUser.getId())) {
                if(userDao.lambdaQuery().eq(User::getPhone,user.getPhone()).ne(User::getId,loginUser.getId()).count()>0) {
                    throw new ServiceException("修改失败，手机号码已存在",400);
                }
                if(userDao.lambdaQuery().eq(User::getUsername,user.getUsername()).ne(User::getId,loginUser.getId()).count()>0) {
                    throw new ServiceException("修改失败，用户名已存在",400);
                }
            }else {
                if(userDao.lambdaQuery().eq(User::getPhone,user.getPhone()).count()>1) {
                    throw new ServiceException("修改失败，手机号码已存在",400);
                }
                if(userDao.lambdaQuery().eq(User::getUsername,user.getUsername()).count()>1) {
                    throw new ServiceException("修改失败，用户名已存在",400);
                }
            }
        }
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
        if(userMapper.deleteById(id)==0) {
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
}
