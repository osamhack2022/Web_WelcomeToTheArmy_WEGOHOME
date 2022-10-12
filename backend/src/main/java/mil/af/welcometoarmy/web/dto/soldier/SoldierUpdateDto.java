package mil.af.welcometoarmy.web.dto.soldier;

import lombok.*;
import lombok.extern.jackson.Jacksonized;
import mil.af.welcometoarmy.domain.Soldier;
import mil.af.welcometoarmy.domain.enums.CautionLevel;
import mil.af.welcometoarmy.domain.enums.IsVegan;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    private String platoonNum;

    private String password;

    @NotBlank(message = "생년월일을 입력해주세요.")
    private String birthday;

    @NotNull(message = "기수를 입력해주세요.")
    private int generation;

    @NotBlank(message = "대대를 입력해주세요.")
    private String battalion;

    @NotBlank(message = "중대를 입력해주세요.")
    private String company;

    @NotBlank(message = "소대를 입력해주세요.")
    private String platoon;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "주의 정도를 입력해주세요.")
    private String cautionLevel;

    private String disease;

    @NotBlank(message = "전화번호를 입력해주세요.")
    private String phoneNumber;

    @NotBlank(message = "보호자 연락처를 입력해주세요.")
    private String homeTel;

    private String uniqueness;

    @NotBlank(message = "비건여부를 입력해주세요.")
    private String isVegan;

    private String hasAllergy;

    private String currentPw;

    public Soldier toEntity() {
        return Soldier.builder()
                .platoonNum(platoonNum)
                .password(password)
                .birthday(LocalDate.parse(birthday, DateTimeFormatter.ISO_DATE))
                .generation(generation)
                .battalion(battalion)
                .company(company)
                .platoon(platoon)
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
        
        if (!Pattern.matches(pattern, password))
            throw new IllegalArgumentException("비밀번호는 영문과 숫자, 특수기호가 적어도 1개 이상씩 포함된 9자 이상 12자 이하의 비밀번호여야 합니다.");
    }
}
