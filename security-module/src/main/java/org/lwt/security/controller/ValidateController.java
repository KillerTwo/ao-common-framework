package org.lwt.security.controller;

import org.lwt.modal.Result;
import org.lwt.security.validatecode.process.ImageValidateCodeProcess;
import org.lwt.security.validatecode.process.SmsValidateCodeProcess;
import org.lwt.security.validatecode.process.ValidateCodeProcess;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class ValidateController {

    private final Map<String, ValidateCodeProcess> validateCodeProcesse;// imageValidateCodeProcess;

    // private final Map<String, ValidateCodeGenerator> validateCodeGenerator; // imageCodeGenerator;

    public ValidateController(Map<String, ValidateCodeProcess> validateCodeProcesse
                              /*Map<String, ValidateCodeGenerator> validateCodeGenerator*/) {
        this.validateCodeProcesse = validateCodeProcesse;
        // this.validateCodeGenerator = validateCodeGenerator;
    }

    @GetMapping("/kaptcha")
    public void kaptcha(HttpServletRequest request, HttpServletResponse response) {
        ImageValidateCodeProcess imageValidateCodeProcess = (ImageValidateCodeProcess) validateCodeProcesse.get("imageValidateCodeProcess");

        imageValidateCodeProcess.process(new ServletWebRequest(request, response));
    }


    @GetMapping("/smscode")
    public @ResponseBody
    Result smsCode(HttpServletRequest request, HttpServletResponse response) {
        SmsValidateCodeProcess smsValidateCodeProcess = (SmsValidateCodeProcess) validateCodeProcesse.get("smsValidateCodeProcess");

        smsValidateCodeProcess.process(new ServletWebRequest(request, response));

        return new Result(200, "", "");
    }

}
