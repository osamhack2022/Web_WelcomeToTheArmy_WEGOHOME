package mil.af.welcometoarmy.web.dto.soldier;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import mil.af.welcometoarmy.domain.Soldier;
import mil.af.welcometoarmy.domain.enums.CautionLevel;
import mil.af.welcometoarmy.domain.enums.IsVegan;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

@Builder
@Getter
@Setter
@ToString
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class SoldierUpdateDto {

    @NotBlank(message = "아이디를 입력해주세요.")
    @ApiModelProperty(value = "아이디", required = true)
    private String platoonNum;

    @ApiModelProperty(value = "비밀번호")
    private String password;

    @NotBlank(message = "생년월일을 입력해주세요.")
    @ApiModelProperty(value = "생년월일", required = true)
    private String birthday;

    @NotNull(message = "기수를 입력해주세요.")
    @Positive(message = "기수는 양수만 입력해주세요.")
    @ApiModelProperty(value = "기수", required = true)
    private int generation;

    @NotBlank(message = "소속을 입력해주세요.")
    @ApiModelProperty(value = "소속", required = true, example = "111")
    private String belong;

    @NotBlank(message = "이름을 입력해주세요.")
    @ApiModelProperty(value = "이름", required = true)
    private String name;

    @NotBlank(message = "주의 정도를 입력해주세요.")
    @ApiModelProperty(value = "기수", required = true, example = "NORMAL / INTEREST")
    private String cautionLevel;

    @ApiModelProperty(value = "질병")
    private String disease;

    @NotBlank(message = "전화번호를 입력해주세요.")
    @ApiModelProperty(value = "전화번호", required = true)
    private String phoneNumber;

    @NotBlank(message = "보호자 연락처를 입력해주세요.")
    @ApiModelProperty(value = "보호자 연락처", required = true)
    private String homeTel;

    @ApiModelProperty(value = "특이사항")
    private String uniqueness;

    @NotBlank(message = "비건여부를 입력해주세요.")
    @ApiModelProperty(value = "비건여부", required = true, example = "VEGAN / NOT_VEGAN")
    private String isVegan;

    @ApiModelProperty(value = "알러지 여부")
    private String hasAllergy;

    @ApiModelProperty(value = "현재 비밀번호")
    private String currentPw;

    @ApiModelProperty(value = "비밀번호 재확인")
    private String passwordCheck;

    public Soldier toEntity() {
        return Soldier.builder()
                .platoonNum(platoonNum)
                .password(password)
                .birthday(LocalDate.parse(birthday, DateTimeFormatter.ISO_DATE))
                .generation(generation)
                .belong(belong)
                .name(name)
                .cautionLevel(CautionLevel.valueOf(cautionLevel))
                .disease(disease)
                .phoneNumber(phoneNumber)
                .homeTel(homeTel)
                .uniqueness(uniqueness)
                .isVegan(IsVegan.valueOf(isVegan))
                .hasAllergy(hasAllergy)
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
