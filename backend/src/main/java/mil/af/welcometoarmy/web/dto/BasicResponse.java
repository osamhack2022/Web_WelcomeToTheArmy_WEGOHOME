package mil.af.welcometoarmy.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasicResponse {
    private HttpStatus httpStatus;
    private String message;
    private Object data;
}
