package org.lwt.security.config;

import org.lwt.security.web.authentication.sms.SmsAuthenticationFilter;
import org.lwt.security.web.authentication.sms.SmsAuthenticationProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.lwt.security.web.authentication.JsonLoginSuccessHandler;
import org.lwt.security.web.authentication.logout.JsonLoginFailureHandler;

import org.springframework.stereotype.Component;

@Component
public class SmsCodeAuthenticationSecurityConfig extends
        SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JsonLoginSuccessHandler jsonLoginSuccessHandler;

    private final JsonLoginFailureHandler jsonLoginFailureHandler;

    private final UserDetailsService userDetailsService;

    /*private final AuthenticationManager authenticationManager;*/

    public SmsCodeAuthenticationSecurityConfig(JsonLoginSuccessHandler jsonLoginSuccessHandler,
                                               JsonLoginFailureHandler jsonLoginFailureHandler,
                                               @Qualifier("userDetailService") UserDetailsService userDetailsService/*, AuthenticationManager authenticationManager*/) {
        this.jsonLoginSuccessHandler = jsonLoginSuccessHandler;
        this.jsonLoginFailureHandler = jsonLoginFailureHandler;
        this.userDetailsService = userDetailsService;
        /*this.authenticationManager = authenticationManager;*/
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        SmsAuthenticationFilter smsAuthenticationFilter = new SmsAuthenticationFilter();
        smsAuthenticationFilter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        smsAuthenticationFilter.setAuthenticationFailureHandler(jsonLoginFailureHandler);
        smsAuthenticationFilter.setAuthenticationSuccessHandler(jsonLoginSuccessHandler);

        SmsAuthenticationProvider smsAuthenticationProvider = new SmsAuthenticationProvider();
        smsAuthenticationProvider.setUserDetailsService(userDetailsService);

        builder.authenticationProvider(smsAuthenticationProvider)
                .addFilterBefore(smsAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
