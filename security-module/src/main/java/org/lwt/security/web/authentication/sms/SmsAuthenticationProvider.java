package org.lwt.security.web.authentication.sms;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

public class SmsAuthenticationProvider implements AuthenticationProvider {

    protected final Log logger = LogFactory.getLog(this.getClass());
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    private static final String USER_NOT_FOUND_PASSWORD = "userNotFoundPassword";
    private PasswordEncoder passwordEncoder;
    private volatile String userNotFoundEncodedPassword;
    private UserDetailsService userDetailsService;
    private UserDetailsPasswordService userDetailsPasswordService;

    public SmsAuthenticationProvider() {
        this.setPasswordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsAuthenticationToken authenticationToken = (SmsAuthenticationToken) authentication;

        UserDetails user = userDetailsService.loadUserByUsername((String) authentication.getPrincipal());

        if(user == null) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }
        SmsAuthenticationToken authenticationTokenResult = new SmsAuthenticationToken(user, user.getAuthorities());

        authenticationTokenResult.setDetails(authentication.getDetails());

        return authenticationTokenResult;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return SmsAuthenticationToken.class.isAssignableFrom(aClass);
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");
        this.passwordEncoder = passwordEncoder;
        this.userNotFoundEncodedPassword = null;
    }

    protected PasswordEncoder getPasswordEncoder() {
        return this.passwordEncoder;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    protected UserDetailsService getUserDetailsService() {
        return this.userDetailsService;
    }

    public void setUserDetailsPasswordService(UserDetailsPasswordService userDetailsPasswordService) {
        this.userDetailsPasswordService = userDetailsPasswordService;
    }

}
