package org.lwt.security.validatecode.generator;

import org.lwt.security.validatecode.entity.ValidateCode;

/**
 *  验证码生成器
 */
public interface ValidateCodeGenerator {

    ValidateCode buildValidateCode();

}
