package com.ljh.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljh.result.Result;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/login**", "/error**").permitAll() // 允许所有人访问
                .anyRequest().authenticated()                         // 其他请求需要认证
                .and()
                .oauth2Login()
                .successHandler(successHandler())                     // 登录成功处理
                .failureHandler(failureHandler());                   // 登录失败处理
    }

    private AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> {
            response.setContentType("application/json;charset=UTF-8");
            Result<String> result = Result.success("登录成功");
            response.getWriter().write(new ObjectMapper().writeValueAsString(result));
            response.setStatus(HttpServletResponse.SC_OK);
        };
    }

    private AuthenticationFailureHandler failureHandler() {
        return (request, response, exception) -> {
            response.setContentType("application/json;charset=UTF-8");
            Result<String> result = Result.error("登录失败");
            response.getWriter().write(new ObjectMapper().writeValueAsString(result));
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        };
    }
}
