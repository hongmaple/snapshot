package com.snapshot.service;

import com.snapshot.dto.request.UpdatePwdReq;
import com.snapshot.pojo.PageDomain;
import com.snapshot.pojo.PageList;
import com.snapshot.pojo.User;

/**
 * @author Chan
 */
public interface UserService {
    /**
     * 注册
     * @param user 参数
     * @return 结果
     */
    Boolean register(User user);

    /**
     * 注册
     * @param user 参数
     * @return 结果
     */
    Boolean adminRegister(User user);

    /**
     * 修改用户信息，密码，手机号，头像
     * @param user 用户
     * @return 结果
     */
    Boolean updateUser(User user);

    /**
     * 修改用户信息，密码，手机号，头像
     * @param user 用户
     * @return 结果
     */
    Boolean adminUpdateUser(User user);


    /**
     * 加载C端用户
     * @param pageDomain 参数
     * @return 结果
     */
    PageList<User> ListUser(PageDomain pageDomain);

    /**
     * 加载后台用户
     * @param pageDomain 参数
     * @return 结果
     */
    PageList<User> AdminListUser(PageDomain pageDomain);

    /**
     * 删除用户
     * @param id 用户id
     * @return 结果
     */
    Boolean deletedUser(Long id);

    /**
     * 获取当前用户登陆的信息
     * @return 结果
     */
    User getUserInfo();

    /**
     * 根据手机获取用户信息
     * @param userName 手机号
     * @return 用户信息
     */
    User getUserByName(String userName);

    /**
     * 修改密码
     * @param updatePwdReq 参数
     * @return 结果
     */
    Boolean updatePwdReq(UpdatePwdReq updatePwdReq);

}
