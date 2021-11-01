package com.snapshot.controller;

import com.snapshot.pojo.AjaxResult;
import com.snapshot.pojo.PageDomain;
import com.snapshot.pojo.User;
import com.snapshot.security.LoginBody;
import com.snapshot.security.service.JwtAuthService;
import com.snapshot.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author Chan
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final JwtAuthService jwtAuthService;

    public UserController(UserService userService, JwtAuthService jwtAuthService) {
        this.userService = userService;
        this.jwtAuthService = jwtAuthService;
    }


    /**
     * c端注册
     * @param user 参数
     * @return
     */
    @PostMapping("/register")
    public AjaxResult register(@RequestBody User user) {
        AjaxResult ajaxResult = AjaxResult.success("注册成功",userService.register(user));
        return ajaxResult;
    }

    /**
     * 后台注册
     * @param user 参数
     * @return
     */
    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping("/adminRegister")
    public AjaxResult adminRegister(@RequestBody User user) {
        AjaxResult ajaxResult = AjaxResult.success("注册成功",userService.adminRegister(user));
        return ajaxResult;
    }

    /**
     * 加载C端用户
     * @param pageDomain 参数
     * @return 结果
     */
    @PostMapping("/list")
    @PreAuthorize("hasAnyAuthority('admin')")
    public AjaxResult ListUser(@RequestBody PageDomain pageDomain) {
        AjaxResult ajaxResult = AjaxResult.success(userService.ListUser(pageDomain));
        return ajaxResult;
    }

    /**
     * 加载后台用户
     * @param pageDomain 参数
     * @return 结果
     */
    @PostMapping("/adminList")
    @PreAuthorize("hasAnyAuthority('admin')")
    public AjaxResult AdminListUser(@RequestBody PageDomain pageDomain) {
        AjaxResult ajaxResult = AjaxResult.success(userService.AdminListUser(pageDomain));
        return ajaxResult;
    }

    /**
     * 登陆
     * @param loginBody 参数
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        String token = jwtAuthService.login(loginBody.getUsername(), loginBody.getPassword());
        AjaxResult ajaxResult = AjaxResult.success("登录成功",token);
        return ajaxResult;
    }

    /**
     * 修改用户信息，密码，手机号，头像
     * @param user 用户
     * @return 结果
     */
    @PutMapping
    public AjaxResult updateUser(@RequestBody User user) {
        AjaxResult ajaxResult = AjaxResult.success(userService.updateUser(user));
        return ajaxResult;
    }

    /**
     * 修改用户信息，密码，手机号，头像
     * @param user 用户
     * @return 结果
     */
    @PutMapping("/admin")
    @PreAuthorize("hasAnyAuthority('admin')")
    public AjaxResult adminUpdateUser(@RequestBody User user) {
        System.out.println(user);
        AjaxResult ajaxResult = AjaxResult.success(userService.adminUpdateUser(user));
        return ajaxResult;
    }

    /**
     * 获取当前用户登陆的信息
     * @return 结果
     */
    @PostMapping("/info")
    public AjaxResult getUserInfo() {
        AjaxResult ajaxResult = AjaxResult.success(userService.getUserInfo());
        return ajaxResult;
    }

    /**
     * 删除用户
     * @param id 用户id
     * @return 结果
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public AjaxResult deletedUser(@PathVariable Long id) {
        AjaxResult ajaxResult = AjaxResult.success(userService.deletedUser(id));
        return ajaxResult;
    }
}
