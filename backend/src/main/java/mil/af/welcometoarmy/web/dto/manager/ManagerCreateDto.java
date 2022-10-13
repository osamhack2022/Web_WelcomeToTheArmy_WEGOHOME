package mil.af.welcometoarmy.web.dto.manager;

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
    private String managerId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "비밀번호 재확인을 입력해주세요.")
    private String passwordCheck;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "계급을 입력해주세요.")
    private String rank;

    @NotBlank(message = "직책을 입력해주세요.")
    private String position;

    private String battalion;

    private String company;

    private String platoon;

    @NotBlank(message = "휴대전화 번호를 입력해주세요.")
    private String phoneNumber;

    private int loginFailCnt;

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
