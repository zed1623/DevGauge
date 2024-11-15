package com.ljh.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljh.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/login**", "/login/**", "/error**", "/api/**", "/doc.html#/home", "/api/developer/**",
                        "/api/githubApi/**", "/api/apiCallCount/**", "/api/websocket/**", "/api/project/**","/api/ai/**",
                        "/callback") // 将 /login/oauth2/code/github 路径添加到允许访问的路径中
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .successHandler(successHandler())
                .failureHandler(failureHandler())
                .and()
                .csrf().disable();
    }

    private AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
        };
    }

    private AuthenticationFailureHandler failureHandler() {
        return (request, response, exception) -> {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        };
    }
}
