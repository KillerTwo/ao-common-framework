package org.lwt.security.validatecode.process;

import org.lwt.security.validatecode.entity.ValidateCode;
import org.lwt.security.validatecode.generator.ValidateCodeGenerator;
import org.springframework.util.Assert;
import org.springframework.web.context.request.ServletWebRequest;

public abstract class AbstractValidateCodeProcess implements ValidateCodeProcess {

    private ValidateCodeGenerator validateCodeGenerator;

    protected AbstractValidateCodeProcess(ValidateCodeGenerator validateCodeGenerator) {
        Assert.notNull(validateCodeGenerator, "validateCodeGenerator cannot be null");
        this.validateCodeGenerator = validateCodeGenerator;
    }

    @Override
    public void process(ServletWebRequest request) {
        ValidateCode validateCode = generator(this.validateCodeGenerator);
        saveSession(request, validateCode);
        send(request, validateCode);
    }

    /**
     *  生成验证码
     * @return ValidateCode
     */
    public ValidateCode generator(ValidateCodeGenerator validateCodeGenerator) {
        return validateCodeGenerator.buildValidateCode();
    }

    /**
     *  保存生成的验证码
     */
    public abstract void saveSession(ServletWebRequest request, ValidateCode validateCode);

    /**
     * 发送验证码
     * @param request
     */
    public abstract void send(ServletWebRequest request, ValidateCode validateCode);

    protected void setValidateCodeGenerator(ValidateCodeGenerator validateCodeGenerator) {
        this.validateCodeGenerator = validateCodeGenerator;
    }

    protected ValidateCodeGenerator getValidateCodeGenerator() {
        return this.validateCodeGenerator;
    }

}
