package org.lwt.security.validatecode.entity;

import lombok.Data;
import org.lwt.exception.ValidateCodeException;
import org.springframework.web.context.request.ServletWebRequest;

import java.time.LocalDateTime;

@Data
public abstract class ValidateCode {

    private String code;

    private LocalDateTime expireDate;

    public ValidateCode(String code, LocalDateTime expireDate) {
        this.code = code;
        this.expireDate = expireDate;
    }

    /**
     *
     * @param code
     * @param seconds	有效时间，秒。
     * @return
     */
    /*public static ValidateCode createValidateCode(String code, Long seconds) {
        return new ValidateCode(code, LocalDateTime.now().plusSeconds(seconds));
    }*/

    /**
     * 	是否过期
     * @return
     */
    public boolean isExpried() {
        return LocalDateTime.now().isAfter(expireDate);
    }

    public abstract boolean validate(ServletWebRequest request, String validateCode) throws ValidateCodeException;
}
