package org.lwt.security.validatecode.entity;

import lombok.Data;
import org.lwt.exception.ValidateCodeException;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

@Data
public class ImageCode extends ValidateCode {

    private BufferedImage bufferedImage;

    @Override
    public boolean validate(ServletWebRequest request, String validateCode) throws ValidateCodeException {
        // HttpSession session = request.getRequest().getSession();
        // ImageCode imageCode = (ImageCode) session.getAttribute("kaptcha");
        String code = getCode();
        if(StringUtils.isEmpty(code)) {
            throw new ValidateCodeException("验证码不存在");
        } else if(!code.equalsIgnoreCase(validateCode)) {
            throw new ValidateCodeException("验证码不正确");
        } else if(isExpried()) {
            throw new ValidateCodeException("验证码已过期");
        } else {
            return true;
        }
    }

    public ImageCode(String code, LocalDateTime expireDate, BufferedImage image) {
        super(code, expireDate);
        this.bufferedImage = image;
    }

    /**
     *
     * @param code
     * @param seconds	有效时间，秒。
     * @return
     */
    public static ValidateCode createValidateCode(String code, Long seconds, BufferedImage image) {
        return new ImageCode(code, LocalDateTime.now().plusSeconds(seconds), image);
    }
}
