package mil.af.welcometoarmy.web.dto.manager;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import mil.af.welcometoarmy.domain.Manager;

import javax.validation.constraints.NotBlank;
import java.util.regex.Pattern;

@Builder
@Getter
@Setter
@ToString
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class ManagerUpdateDto {

    @NotBlank(message = "아이디를 입력해주세요.")
    @ApiModelProperty(value = "아이디", required = true)
    private String managerId;

    @ApiModelProperty(value = "비밀번호")
    private String password;

    @ApiModelProperty(value = "비밀번호 재확인")
    private String passwordCheck;

    @ApiModelProperty(value = "현재 비밀번호")
    private String currentPw;

    @NotBlank(message = "이름을 입력해주세요.")
    @ApiModelProperty(value = "이름", required = true)
    private String name;

    @NotBlank(message = "계급을 입력해주세요.")
    @ApiModelProperty(value = "계급", required = true)
    private String rank;

    @NotBlank(message = "직책을 입력해주세요.")
    @ApiModelProperty(value = "직책", required = true)
    private String position;

    @NotBlank(message = "소속을 입력해주세요.")
    @ApiModelProperty(value = "소속", required = true, example = "111")
    private String belong;

    @NotBlank(message = "휴대전화 번호를 입력해주세요.")
    @ApiModelProperty(value = "휴대전화 번호", required = true)
    private String phoneNumber;

    public Manager toEntity() {
        return Manager.builder()
                .managerId(managerId)
                .password(password)
                .name(name)
                .rank(rank)
                .position(position)
                .belong(belong)
                .phoneNumber(phoneNumber)
                .build();
    }

    public void validatePassword() {
        String pattern = "(?=.*\\d)(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{9,12}";

        if (password == null) throw new IllegalArgumentException("비밀번호를 입력해주세요.");
        if (passwordCheck == null) throw new IllegalArgumentException("비밀번호 재확인을 입력해주세요.");
        if (!Pattern.matches(pattern, password))
            throw new IllegalArgumentException("비밀번호는 영문과 숫자, 특수기호가 적어도 1개 이상씩 포함된 9자 이상 12자 이하의 비밀번호여야 합니다.");
        if (!password.equals(passwordCheck))
            throw new IllegalArgumentException("비밀번호와 비밀번호 재확인이 일치하지 않습니다.");
    }
}
