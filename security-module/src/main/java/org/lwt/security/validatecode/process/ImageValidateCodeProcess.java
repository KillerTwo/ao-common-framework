package org.lwt.security.validatecode.process;

import org.lwt.security.constants.SecurityConstants;
import org.lwt.security.validatecode.entity.ImageCode;
import org.lwt.security.validatecode.entity.ValidateCode;
import org.lwt.security.validatecode.generator.ValidateCodeGenerator;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

public class ImageValidateCodeProcess extends AbstractValidateCodeProcess {

    private final ValidateCodeGenerator imageCodeGenerator;

    public ImageValidateCodeProcess(ValidateCodeGenerator imageCodeGenerator) {
        super(imageCodeGenerator);
        this.imageCodeGenerator = imageCodeGenerator;
    }


    @Override
    public void saveSession(ServletWebRequest request, ValidateCode validateCode) {
        HttpSession session = request.getRequest().getSession();
        session.setAttribute(SecurityConstants.KAPTCHA_SESSION_KEY, validateCode);
    }

    @Override
    public void send(ServletWebRequest request, ValidateCode validateCode) {
        if(ImageCode.class.isAssignableFrom(validateCode.getClass())) {
            try {
                ImageIO.write(((ImageCode) validateCode).getBufferedImage(), "JPEG", request.getResponse().getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
