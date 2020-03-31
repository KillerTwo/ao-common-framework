package org.lwt.security.web.authentication.rbac;

import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

@Component
@Order(Integer.MIN_VALUE)
public class FormAuthorizeConfigProvider implements AuthorizeConfigProvider {


    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry httpSecurity) {
        httpSecurity.antMatchers("/layuiadmin/**/*.css",
                "/layuiadmin/**/*.js", "/layuiadmin/**",
                "/kaptcha", "/user/forget", "/user/reg",
                "/process_mobile_login", "/smscode").permitAll();
    }
}
