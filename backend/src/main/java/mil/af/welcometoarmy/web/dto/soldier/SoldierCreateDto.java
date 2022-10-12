package mil.af.welcometoarmy.web.dto.soldier;

import lombok.*;
import lombok.extern.jackson.Jacksonized;
import mil.af.welcometoarmy.domain.Soldier;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Builder
@Getter
@ToString
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class SoldierCreateDto {

    @NotBlank(message = "기수를 입력해주세요.")
    private int generation;

    @NotBlank(message = "대대를 입력해주세요.")
    private String battalion;

    @NotBlank(message = "중대를 입력해주세요.")
    private String company;

    @NotBlank(message = "소대를 입력해주세요.")
    private String platoon;

    @NotBlank(message = "소대번호를 입력해주세요.")
    private String platoonNum;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "생년월일을 입력해주세요.")
    private LocalDate birthday;

    public Soldier toEntity() {

        return Soldier.builder()
                .generation(generation)
                .battalion(battalion)
                .company(company)
                .platoon(platoon)
                .platoonNum(platoonNum)
                .name(name)
                .birthday(birthday)
                .build();
    }
}
