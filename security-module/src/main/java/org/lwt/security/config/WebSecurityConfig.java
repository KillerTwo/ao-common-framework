package org.lwt.security.config;

import org.lwt.security.properties.SecurityProperties;
import org.lwt.security.validatecode.filter.ImageVerifyFilter;
import org.lwt.security.web.authentication.JsonLoginSuccessHandler;
import org.lwt.security.web.authentication.logout.JsonLoginFailureHandler;
import org.lwt.security.validatecode.filter.SmsVerifyFilter;
import org.lwt.security.web.authentication.rbac.AuthorizeConfigManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public WebSecurityConfig(JsonLoginSuccessHandler jsonLoginSuccessHandler,
                             JsonLoginFailureHandler jsonLoginFailureHandler,
                             ImageVerifyFilter imageVerifyFilter,
                             SmsVerifyFilter smsVerifyFilter, AuthorizeConfigManager authorizeConfigManager,
                             SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig, SecurityProperties securityProperties) {
        this.jsonLoginSuccessHandler = jsonLoginSuccessHandler;
        this.jsonLoginFailureHandler = jsonLoginFailureHandler;
        this.imageVerifyFilter = imageVerifyFilter;
        this.smsVerifyFilter = smsVerifyFilter;
        this.authorizeConfigManager = authorizeConfigManager;
        this.smsCodeAuthenticationSecurityConfig = smsCodeAuthenticationSecurityConfig;
        this.securityProperties = securityProperties;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final JsonLoginSuccessHandler jsonLoginSuccessHandler;

    private final JsonLoginFailureHandler jsonLoginFailureHandler;

    private final ImageVerifyFilter imageVerifyFilter;

    private final SmsVerifyFilter smsVerifyFilter;

    private final AuthorizeConfigManager authorizeConfigManager;

    private final  SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    private final SecurityProperties securityProperties;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("$2a$10$x1bn5u0s3gAkY31absGnguAGYYLnGLYp/iCAu3OPGf9ysojaW3YLm")
                .authorities("ROLE_ADMIN");
    }



    @Override
    public void configure(HttpSecurity http) throws Exception {

        /** 短信验证码 **/
        if(securityProperties.isEnableSmsLogin()) {
            http.apply(smsCodeAuthenticationSecurityConfig);
        }

        http.addFilterBefore(smsVerifyFilter, AbstractPreAuthenticatedProcessingFilter.class)
                .addFilterBefore(imageVerifyFilter, AbstractPreAuthenticatedProcessingFilter.class)
                .formLogin(formLogin-> // /user/login
                        formLogin.loginPage(securityProperties.getLoginPage()).permitAll()
                                /**下面的配置对自定义过滤器链不起作用**/
                                .loginProcessingUrl(securityProperties.getLoginProcessingUrl())
                                .successHandler(jsonLoginSuccessHandler)
                                .failureHandler(jsonLoginFailureHandler))
                .logout(logout->
                        logout.logoutUrl(securityProperties.getLogoutUrl())
                                .logoutSuccessUrl(securityProperties.getLogoutSuccessUrl()))
                .csrf().disable()
                .headers(headers->
                        headers.frameOptions().disable());

        authorizeConfigManager.config(http.authorizeRequests());
    }


    /**
     *  权限
     * @return
     */
    @Bean
    @ConditionalOnBean(PermissionEvaluator.class)
    public DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler(PermissionEvaluator permissionEvaluator){
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setPermissionEvaluator(permissionEvaluator);
        return handler;
    }


    public static void main(String[] args) {
        BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
        System.err.println(encode.encode("123456"));

//        .authorizeRequests(authorizeRequests->
//                authorizeRequests
//                        .antMatchers("/layuiadmin/**/*.css",
//                                "/layuiadmin/**/*.js", "/layuiadmin/**",
//                                "/kaptcha", "/user/forget", "/user/reg",
//                                "/process_mobile_login", "/smscode").permitAll()
//                        .anyRequest().authenticated());

    }
}
