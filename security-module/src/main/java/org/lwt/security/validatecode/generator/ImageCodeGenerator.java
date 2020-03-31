package org.lwt.security.validatecode.generator;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.lwt.security.validatecode.entity.ImageCode;
import org.lwt.security.validatecode.entity.ValidateCode;

import java.awt.image.BufferedImage;
import java.util.Properties;

/**
 *  图片验证码生成器
 */
public class ImageCodeGenerator implements ValidateCodeGenerator {



    @Override
    public ValidateCode buildValidateCode() {
        DefaultKaptcha defaultKaptcha = kaptcha();

        String code = defaultKaptcha.createText();
        BufferedImage image = defaultKaptcha.createImage(code);
        ImageCode imageCode = (ImageCode) ImageCode.createValidateCode(code, 30000l, image);

        return imageCode;
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
