package org.lwt.security.web.authentication.logout;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.lwt.modal.Result;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component("jsonLoginFailureHandler")
public class JsonLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    public JsonLoginFailureHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        Result result = new Result(200, exception.getMessage(), exception.getMessage());
        response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(result));
        // super.onAuthenticationFailure(request, response, exception);
    }
}
