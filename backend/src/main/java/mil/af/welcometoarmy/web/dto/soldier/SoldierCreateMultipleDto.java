package mil.af.welcometoarmy.web.dto.soldier;

import lombok.*;
import lombok.extern.jackson.Jacksonized;
import mil.af.welcometoarmy.domain.Soldier;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Builder
@Getter
@ToString
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class SoldierCreateMultipleDto {

    private int generation;

    private String battalion;

    private String company;

    private String platoon;

    private String platoonNum;

    private String name;

    private LocalDate birthday;

    private String phoneNumber;

    private String homeTel;

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
