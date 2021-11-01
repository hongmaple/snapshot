package com.snapshot.security.handle;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.snapshot.pojo.AjaxResult;
import com.snapshot.utils.ServletUtils;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * 认证失败处理类 返回未授权
 * 
 * @author Chan
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable
{
    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException
    {
        int code = HttpStatus.UNAUTHORIZED.value();
        String msg = StringUtils.format("请求访问：{%s}，认证失败，无法访问系统资源", request.getRequestURI());
        ServletUtils.renderString(response, code,JSON.toJSONString(AjaxResult.error(code, msg)));
    }
}