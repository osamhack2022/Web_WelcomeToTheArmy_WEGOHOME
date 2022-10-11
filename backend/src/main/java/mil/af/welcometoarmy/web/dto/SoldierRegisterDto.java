package mil.af.welcometoarmy.web.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;
import mil.af.welcometoarmy.domain.Soldier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Builder
@Getter
@ToString
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class SoldierRegisterDto {

    private int generation;

    private String battalion;

    private String company;

    private String platoon;

    private String platoonNum;

    private String name;

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
