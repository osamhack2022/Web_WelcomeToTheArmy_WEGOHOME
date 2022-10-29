package mil.af.welcometoarmy.config.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import mil.af.welcometoarmy.web.dto.BasicResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        String exceptionMessage = (String) request.getAttribute("exception");
        setResponse(response, exceptionMessage);
    }

    private void setResponse(HttpServletResponse response, String exceptionMessage) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        if (exceptionMessage == null) exceptionMessage = "알 수 없는 에러가 발생했습니다.";

        BasicResponse responseDto =
                BasicResponse.builder()
                        .httpStatus(HttpStatus.UNAUTHORIZED)
                        .message(exceptionMessage)
                        .build();

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().print(mapper.writeValueAsString(responseDto));
        response.getWriter().flush();
    }
}
