package mil.af.welcometoarmy.web.dto.manager;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "아이디", required = true)
    private String managerId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @ApiModelProperty(value = "비밀번호", required = true)
    private String password;
}
