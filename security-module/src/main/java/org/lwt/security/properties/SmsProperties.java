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

    private String signName = "";

    private String templateCode = "";

    private String templateParam = "";

    private String accessKey = "";

    private String accessSecret = "";


}
