package com.sky.interceptor;

import com.sky.constant.JwtClaimsConstant;
import com.sky.context.BaseContext;
import com.sky.properties.JwtProperties;
import com.sky.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * jwt令牌校验的拦截器
 */
@Component
@Slf4j
public class JwtTokenAdminInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    // 校验jwt
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断当前拦截到的是 Controller 的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            // 放行静态资源等非控制器请求
            return true;
        }

        // 从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getAdminTokenName());

        // 校验令牌
        try {
            log.info("jwt 校验:{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
            Long empId = Long.valueOf(claims.get(JwtClaimsConstant.EMP_ID).toString());
            log.info("当前员工 id：{}", empId);
            // 将当前登录用户的 id 保存到 ThreadLocal 中
            BaseContext.setCurrentId(empId);
            // 通过，放行
            return true;
        } catch (Exception ex) {
            // 不通过，响应 401 状态码
            response.setStatus(401);
            return false;
        }
    }
}
