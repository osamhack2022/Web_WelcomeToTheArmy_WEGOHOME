package mil.af.welcometoarmy.web.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@ToString
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class SoldierDto {

    @NotBlank(message = "아이디를 입력해주세요.")
    private String platoonNum;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

}
