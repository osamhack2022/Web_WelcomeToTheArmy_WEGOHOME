package mil.af.welcometoarmy.web.dto.manager;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@ToString
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class ManagerLoginDto {

    @NotBlank(message = "아이디를 입력해주세요.")
    private String managerId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
