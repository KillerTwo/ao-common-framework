package org.lwt.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.lwt.security.properties.SecurityProperties;
import org.lwt.security.validatecode.generator.ImageCodeGenerator;
import org.lwt.security.validatecode.generator.SmsCodeGenerator;
import org.lwt.security.validatecode.generator.ValidateCodeGenerator;
import org.lwt.security.validatecode.process.ImageValidateCodeProcess;
import org.lwt.security.validatecode.process.SmsValidateCodeProcess;
import org.lwt.security.validatecode.process.ValidateCodeProcess;
import org.lwt.security.web.authentication.user.DefaultUserDetailService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class BeanConfig {

    @Bean
    @ConditionalOnMissingBean(name = "imageCodeGenerator")
    public ValidateCodeGenerator imageCodeGenerator() {
        return new ImageCodeGenerator();
    }

    @Bean
    @ConditionalOnMissingBean(name = "imageValidateCodeProcess")
    public ValidateCodeProcess imageValidateCodeProcess(@Qualifier("imageCodeGenerator") ValidateCodeGenerator imageCodeGenerator) {
        return new ImageValidateCodeProcess(imageCodeGenerator);
    }

    @Bean
    @ConditionalOnMissingBean(name = "smsValidateCodeProcess")
    public ValidateCodeProcess smsValidateCodeProcess(@Qualifier("smsCodeGenerator") ValidateCodeGenerator smsCodeGenerator,
                                                      SecurityProperties securityProperties, ObjectMapper objectMapper) {
        return new SmsValidateCodeProcess(smsCodeGenerator, securityProperties, objectMapper);
    }

    @Bean
    @ConditionalOnMissingBean(name = "smsCodeGenerator")
    public ValidateCodeGenerator smsCodeGenerator() {
        return new SmsCodeGenerator();
    }

    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public DefaultUserDetailService userDetailService() {
        return new DefaultUserDetailService();
    }

}
