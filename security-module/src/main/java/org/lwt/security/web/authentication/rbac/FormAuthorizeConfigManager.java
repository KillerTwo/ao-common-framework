package org.lwt.security.web.authentication.rbac;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FormAuthorizeConfigManager implements AuthorizeConfigManager {

    private final List<AuthorizeConfigProvider> authorizeConfigProviders;

    public FormAuthorizeConfigManager(List<AuthorizeConfigProvider> authorizeConfigProviders) {
        this.authorizeConfigProviders = authorizeConfigProviders;
    }

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry httpSecurity) {
        for (AuthorizeConfigProvider  provider: authorizeConfigProviders) {
            provider.config(httpSecurity);
        }
        httpSecurity.anyRequest().authenticated();
    }
}
