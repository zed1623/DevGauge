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
                .antMatchers("/", "/login**", "/error**","/api/**", "/doc.html#/home","/api/developer/**",
                        "/api/githubApi/**","/api/apiCallCount/**","/api/websocket/**","/api/project/**") // 允许访问相关路径
                .permitAll() // 允许所有人访问这些页面
                .anyRequest().authenticated() // 其他请求需要认证
                .and()
                .oauth2Login() // 启用 OAuth2 登录
                .successHandler(successHandler()) // 登录成功处理
                .failureHandler(failureHandler()) // 登录失败处理
                .and()
                .csrf().disable(); // 禁用 CSRF 防护，适用于 OAuth2 登录场景
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
