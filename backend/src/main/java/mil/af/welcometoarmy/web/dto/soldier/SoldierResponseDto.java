package mil.af.welcometoarmy.web.dto.soldier;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

@Builder
@Getter
@ToString
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class SoldierResponseDto {
    private Long id;
    private String platoonNum;
    private LocalDate birthday;
    private int generation;
    private String belong;
    private String name;
    private String cautionLevel;
    private String authority;
    private String disease;
    private String phoneNumber;
    private String homeTel;
    private String uniqueness;
    private String isVegan;
    private String hasAllergy;
    private int point;
}
