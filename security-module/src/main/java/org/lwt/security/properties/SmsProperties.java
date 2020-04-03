package org.lwt.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
public class SmsProperties {

    private String domain = "dysmsapi.aliyuncs.com";
    private String version = "2017-05-25";
    private String action = "SendSms";
    private String regionId = "cn-hangzhou";

    private String phoneNumbers = "";

    private String signName = "aopoll";

    private String templateCode = "SMS_170550108";

    private String templateParam = "";

    private String accessKey = "";

    private String accessSecret = "";


}
