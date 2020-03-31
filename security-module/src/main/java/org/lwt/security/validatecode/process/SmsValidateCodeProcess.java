package org.lwt.security.validatecode.process;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.lwt.security.constants.SecurityConstants;
import org.lwt.security.properties.SecurityProperties;
import org.lwt.security.validatecode.entity.SmsCode;
import org.lwt.security.validatecode.entity.ValidateCode;
import org.lwt.security.validatecode.generator.ValidateCodeGenerator;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class SmsValidateCodeProcess extends AbstractValidateCodeProcess {

    private final ValidateCodeGenerator smsCodeGenerator;

    private final SecurityProperties securityProperties;

    private final ObjectMapper objectMapper;

    public SmsValidateCodeProcess(ValidateCodeGenerator smsCodeGenerator, SecurityProperties securityProperties, ObjectMapper objectMapper) {
        super(smsCodeGenerator);
        this.smsCodeGenerator = smsCodeGenerator;
        this.securityProperties = securityProperties;
        this.objectMapper = objectMapper;
    }


    @Override
    public void saveSession(ServletWebRequest request, ValidateCode validateCode) {
        HttpSession session = request.getRequest().getSession();
        session.setAttribute(SecurityConstants.SMS_SESSION_KEY, validateCode);
    }

    @Override
    public void send(ServletWebRequest request, ValidateCode validateCode) {

        String mobile = obtainMobile(request.getRequest());
        System.err.println("手机号是：" + mobile);
        if(SmsCode.class.isAssignableFrom(validateCode.getClass())) {
            try {
                System.err.println("发送短信验证码："  + validateCode.getCode());
                sendSms(mobile, validateCode.getCode());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String obtainMobile(HttpServletRequest request) {

        return request.getParameter(SecurityConstants.SEND_SMS_PARAMER_KEY);
    }

    private String sendSms(String phone, String code) throws JsonProcessingException {
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);

        DefaultProfile profile = DefaultProfile.getProfile(securityProperties.getSms().getRegionId(), securityProperties.getSms().getAccessKey(), securityProperties.getSms().getAccessSecret());
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(securityProperties.getSms().getDomain());
        request.setVersion(securityProperties.getSms().getVersion());
        request.setAction(securityProperties.getSms().getAction());
        request.putQueryParameter("RegionId", securityProperties.getSms().getRegionId());

        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", securityProperties.getSms().getSignName());
        request.putQueryParameter("TemplateCode", securityProperties.getSms().getTemplateCode());
        request.putQueryParameter("TemplateParam", objectMapper.writeValueAsString(param));
        try {
            CommonResponse response = client.getCommonResponse(request);
            return response.getData();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return "";
    }
}
