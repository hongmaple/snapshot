package com.withpat.security.service;

import com.withpat.exception.ServiceException;
import com.withpat.security.JwtUser;
import com.withpat.utils.JwtTokenUtil;
import com.withpat.config.RSAKeyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class JwtAuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RSAKeyProperties rsaKeyProp;

    public String login(String username, String password) {
        // 用户验证
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            /*throw new RuntimeException("用户不存在/密码错误");*/
            throw new ServiceException("用户不存在/密码错误",400);
        }
        JwtUser loginUser = (JwtUser) authentication.getPrincipal();
        // 生成Token
        return jwtTokenUtil.generateToken(loginUser, rsaKeyProp.getPrivateKey());
    }
}
