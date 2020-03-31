package org.lwt.security.validatecode.generator;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.lwt.security.validatecode.entity.SmsCode;
import org.lwt.security.validatecode.entity.ValidateCode;

import java.util.Properties;

/**
 *  短信验证码生成器
 */
public class SmsCodeGenerator implements ValidateCodeGenerator {


    @Override
    public ValidateCode buildValidateCode() {
        DefaultKaptcha defaultKaptcha = kaptcha();

        String code = defaultKaptcha.createText();
        SmsCode smsCode = (SmsCode) SmsCode.createValidateCode(code, 30000l);

        return smsCode;
    }

    public DefaultKaptcha kaptcha() {
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty("kaptcha.border", "no");
        properties.setProperty("kaptcha.textproducer.font.color", "black");
        properties.setProperty("kaptcha.textproducer.char.space", "4");
        properties.put("kaptcha.textproducer.char.space", "4");
        Config config = new Config(properties);

        kaptcha.setConfig(config);

        return kaptcha;
    }

}
