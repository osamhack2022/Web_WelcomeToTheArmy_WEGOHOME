package mil.af.welcometoarmy.web.dto.soldier;

import lombok.*;
import lombok.extern.jackson.Jacksonized;
import mil.af.welcometoarmy.domain.Soldier;
import mil.af.welcometoarmy.domain.enums.Authority;
import mil.af.welcometoarmy.domain.enums.CautionLevel;
import mil.af.welcometoarmy.domain.enums.IsVegan;

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
                .belong(battalion+company+platoon)
                .platoonNum(platoonNum)
                .name(name)
                .birthday(birthday)
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
