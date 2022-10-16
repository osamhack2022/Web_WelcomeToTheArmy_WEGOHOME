package mil.af.welcometoarmy.web.dto.soldier;

import lombok.*;
import lombok.extern.jackson.Jacksonized;
import mil.af.welcometoarmy.domain.Soldier;

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
    private String birthday;

    public Soldier toEntity() {

        return Soldier.builder()
                .generation(generation)
                .battalion(battalion)
                .company(company)
                .platoon(platoon)
                .platoonNum(platoonNum)
                .name(name)
                .birthday(LocalDate.parse(birthday, DateTimeFormatter.ISO_DATE))
                .build();
    }
}
