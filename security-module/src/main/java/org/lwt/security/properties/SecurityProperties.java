package org.lwt.security.properties;

import lombok.Data;
import org.lwt.security.constants.SecurityConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "lwt.security")
public class SecurityProperties {

    private SmsProperties sms = new SmsProperties();

    private String loginPage = SecurityConstants.LOGIN_PAGE;

    /** 不用短信登录时的登录处理url **/
    private String loginProcessingUrl = "/process_login";

    private String logoutUrl = "/user/logout";

    private String logoutSuccessUrl = "/user/login";

    private boolean enableSmsLogin = true;




}
