package org.lwt.security.validatecode.filter;

import org.lwt.exception.ValidateCodeException;
import org.lwt.security.constants.SecurityConstants;
import org.lwt.security.web.authentication.logout.JsonLoginFailureHandler;
import org.lwt.security.validatecode.entity.SmsCode;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  图片验证码拦截器
 */
@Component("smsVerifyFilter")
public class SmsVerifyFilter extends OncePerRequestFilter {

    private final JsonLoginFailureHandler jsonLoginFailureHandler;

    public SmsVerifyFilter(JsonLoginFailureHandler jsonLoginFailureHandler) {
        this.jsonLoginFailureHandler = jsonLoginFailureHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if(StringUtils.pathEquals(httpServletRequest.getRequestURI(), SecurityConstants.SMS_LOGIN_PROCESSING_URL)
        && StringUtils.pathEquals(httpServletRequest.getMethod(), "POST")) {
            SmsCode smsCode = (SmsCode) httpServletRequest.getSession().getAttribute(SecurityConstants.SMS_SESSION_KEY);

            if(smsCode == null) {
                smsCode = new SmsCode(null, null);
            }
            try {
                smsCode.validate(new ServletWebRequest(httpServletRequest, httpServletResponse),
                        obtainValidateCode(httpServletRequest));
            } catch (ValidateCodeException e) {
                e.printStackTrace();
                jsonLoginFailureHandler.onAuthenticationFailure(httpServletRequest,httpServletResponse, e);
                return;
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    public String obtainValidateCode(HttpServletRequest request) {
        String code = request.getParameter(SecurityConstants.SMS_CODE_PARAM_KEY);
        return code == null ? "" : code.trim();
    }

}
