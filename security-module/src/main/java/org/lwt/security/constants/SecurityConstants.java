package org.lwt.security.constants;

public class SecurityConstants {

    /** 短信登录处理url **/
    public final static String SMS_LOGIN_PROCESSING_URL = "/process_mobile_login";

    /** 短信登录时发送手机号的参数名 mobile=18909990999 **/
    public final static String SPRING_SECURITY_SMS_PHONE_KEY = "mobile";

    /** 验证码session key **/
    public final static String KAPTCHA_SESSION_KEY = "kaptcha";

    /** 短信验证码(发送给手机的短信code) session key **/
    public final static String SMS_SESSION_KEY = "mobileCode";

    /** 发送短信是将手机号发送给后端的参数名 **/
    public final static String SEND_SMS_PARAMER_KEY = "phone";

    public final static String LOGIN_PAGE = "/user/login";

    /** 短信登录的页面 **/
    public final static String SMS_LOGIN_PAGE = "/user/sms_login";

    /** 登录时发送短信验证码的参数名 **/
    public final static String SMS_CODE_PARAM_KEY = "mobileCode";

    /** 验证码参数名 **/
    public final static String VERIFY_CODE_PARAM_KEY = "vercode";





}
