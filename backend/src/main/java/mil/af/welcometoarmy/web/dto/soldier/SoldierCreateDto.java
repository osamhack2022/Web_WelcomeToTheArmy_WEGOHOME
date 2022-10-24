package mil.af.welcometoarmy.web.dto.soldier;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import mil.af.welcometoarmy.domain.Soldier;
import mil.af.welcometoarmy.domain.enums.Authority;
import mil.af.welcometoarmy.domain.enums.CautionLevel;
import mil.af.welcometoarmy.domain.enums.IsVegan;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Builder
@Getter
@ToString
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class SoldierCreateDto {

    @NotNull(message = "기수를 입력해주세요.")
    @ApiModelProperty(value = "기수", required = true)
    private int generation;

    @NotBlank(message = "대대를 입력해주세요.")
    @ApiModelProperty(value = "대대", required = true)
    private String battalion;

    @NotBlank(message = "중대를 입력해주세요.")
    @ApiModelProperty(value = "중대", required = true)
    private String company;

    @NotBlank(message = "소대를 입력해주세요.")
    @ApiModelProperty(value = "소대", required = true)
    private String platoon;

    @NotBlank(message = "소대번호를 입력해주세요.")
    @ApiModelProperty(value = "소대번호", required = true)
    private String platoonNum;

    @NotBlank(message = "이름을 입력해주세요.")
    @ApiModelProperty(value = "이름", required = true)
    private String name;

    @NotBlank(message = "생년월일을 입력해주세요.")
    @ApiModelProperty(value = "기수", required = true, example = "2000-01-01")
    private String birthday;

    @NotBlank(message = "휴대전화 번호를 입력해주세요.")
    @ApiModelProperty(value = "휴대전화 번호", required = true)
    private String phoneNumber;

    @NotBlank(message = "보호자 연락처를 입력해주세요.")
    @ApiModelProperty(value = "보호자 연락처", required = true)
    private String homeTel;

    public Soldier toEntity() {

        return Soldier.builder()
                .generation(generation)
                .battalion(battalion)
                .company(company)
                .platoon(platoon)
                .platoonNum(platoonNum)
                .name(name)
                .birthday(LocalDate.parse(birthday, DateTimeFormatter.ISO_DATE))
                .phoneNumber(phoneNumber)
                .homeTel(homeTel)
                .isVegan(IsVegan.NOT_VEGAN)
                .cautionLevel(CautionLevel.NORMAL)
                .point(0)
                .authority(Authority.ROLE_SOLDIER)
                .logInFailCnt(0)
                .build();
    }
}
