package org.lwt.security.validatecode.entity;

import lombok.Data;
import org.lwt.exception.ValidateCodeException;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

@Data
public class SmsCode extends ValidateCode {


    @Override
    public boolean validate(ServletWebRequest request, String validateCode) throws ValidateCodeException {
        // HttpSession session = request.getRequest().getSession();
        // ImageCode imageCode = (ImageCode) session.getAttribute("kaptcha");
        String code = getCode();
        if(StringUtils.isEmpty(code)) {
            throw new ValidateCodeException("短信验证码不存在");
        } else if(!code.equalsIgnoreCase(validateCode)) {
            throw new ValidateCodeException("短信验证码不正确");
        } else if(isExpried()) {
            throw new ValidateCodeException("短信验证码已过期");
        } else {
            return true;
        }
    }

    public SmsCode(String code, LocalDateTime expireDate) {
        super(code, expireDate);
    }

    /**
     *
     * @param code
     * @param seconds	有效时间，秒。
     * @return
     */
    public static ValidateCode createValidateCode(String code, Long seconds) {
        return new SmsCode(code, LocalDateTime.now().plusSeconds(seconds));
    }
}
