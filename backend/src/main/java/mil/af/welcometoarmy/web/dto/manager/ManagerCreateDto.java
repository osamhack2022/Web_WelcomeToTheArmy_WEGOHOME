package mil.af.welcometoarmy.web.dto.manager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import mil.af.welcometoarmy.domain.Manager;
import mil.af.welcometoarmy.domain.enums.Authority;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@Setter
@ToString
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class ManagerCreateDto {

    @NotBlank(message = "아이디를 입력해주세요.")
    @ApiModelProperty(value = "아이디", required = true)
    private String managerId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @ApiModelProperty(value = "비밀번호", required = true)
    private String password;

    @NotBlank(message = "비밀번호 재확인을 입력해주세요.")
    @ApiModelProperty(value = "비밀번호 재확인", required = true)
    private String passwordCheck;

    @NotBlank(message = "이름을 입력해주세요.")
    @ApiModelProperty(value = "이름", required = true)
    private String name;

    @NotBlank(message = "계급을 입력해주세요.")
    @ApiModelProperty(value = "계급", required = true)
    private String rank;

    @NotBlank(message = "직책을 입력해주세요.")
    @ApiModelProperty(value = "직책", required = true)
    private String position;

    @ApiModelProperty(value = "대대")
    private String battalion;

    @ApiModelProperty(value = "중대")
    private String company;

    @ApiModelProperty(value = "소대")
    private String platoon;

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
                .battalion(battalion)
                .company(company)
                .platoon(platoon)
                .phoneNumber(phoneNumber)
                .logInFailCnt(0)
                .authority(Authority.ROLE_MANAGER)
                .build();
    }

    public void passwordTypoCheck() {
        if (!password.equals(passwordCheck))
            throw new IllegalArgumentException("비밀번호와 비밀번호 재확인이 일치하지 않습니다.");
    }
}
