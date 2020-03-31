package org.lwt.security.web.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.lwt.modal.Result;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("jsonLoginSuccessHandler")
public class JsonLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;

    public JsonLoginSuccessHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        Result result = new Result(200, "登录成功", authentication);
        response.setStatus(200);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(result));

        // super.onAuthenticationSuccess(request, response, authentication);
    }
}
