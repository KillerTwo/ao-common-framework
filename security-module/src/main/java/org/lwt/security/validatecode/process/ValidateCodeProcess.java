package org.lwt.security.validatecode.process;

import org.lwt.security.validatecode.entity.ValidateCode;
import org.lwt.security.validatecode.generator.ValidateCodeGenerator;
import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeProcess {

    /**
     *  处理验证码
     * @param request
     */
    void process(ServletWebRequest request);

}
