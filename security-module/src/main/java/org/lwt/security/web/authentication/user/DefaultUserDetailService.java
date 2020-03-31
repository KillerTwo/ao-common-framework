package org.lwt.security.web.authentication.user;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class DefaultUserDetailService implements UserDetailsService {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        logger.debug("获取用户名密码： " + s);
        return new User(s, "",
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));
    }
}
