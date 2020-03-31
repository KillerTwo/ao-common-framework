package org.lwt.security.validatecode.filter;

import org.lwt.exception.ValidateCodeException;
import org.lwt.security.constants.SecurityConstants;
import org.lwt.security.properties.SecurityProperties;
import org.lwt.security.validatecode.entity.ImageCode;
import org.lwt.security.web.authentication.logout.JsonLoginFailureHandler;
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
@Component("imageVerifyFilter")
public class ImageVerifyFilter extends OncePerRequestFilter {

    private final JsonLoginFailureHandler jsonLoginFailureHandler;

    private final SecurityProperties securityProperties;

    public ImageVerifyFilter(JsonLoginFailureHandler jsonLoginFailureHandler, SecurityProperties securityProperties) {
        this.jsonLoginFailureHandler = jsonLoginFailureHandler;
        this.securityProperties = securityProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if(StringUtils.pathEquals(httpServletRequest.getRequestURI(), securityProperties.getLoginProcessingUrl())
        && StringUtils.pathEquals(httpServletRequest.getMethod(), "POST")) {
            ImageCode imageCode = (ImageCode) httpServletRequest.getSession().getAttribute(SecurityConstants.KAPTCHA_SESSION_KEY);

            if(imageCode == null) {
                imageCode = new ImageCode(null, null, null);
            }
            try {
                imageCode.validate(new ServletWebRequest(httpServletRequest, httpServletResponse),
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
        String code = request.getParameter("vercode");
        return code == null ? "" : code.trim();
    }

}
