package mil.af.welcometoarmy.config.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import mil.af.welcometoarmy.web.dto.BasicResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler  {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();

        BasicResponse responseDto =
                BasicResponse.builder()
                        .httpStatus(HttpStatus.UNAUTHORIZED)
                        .message("접근 권한이 없습니다.")
                        .build();

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().print(mapper.writeValueAsString(responseDto));
        response.getWriter().flush();
    }
}
