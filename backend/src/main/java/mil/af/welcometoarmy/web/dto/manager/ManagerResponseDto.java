package mil.af.welcometoarmy.web.dto.manager;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Builder
@Getter
@Setter
@ToString
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class ManagerResponseDto {

    private Long id;
    private String managerId;
    private String name;
    private String rank;
    private String position;
    private String battalion;
    private String company;
    private String phoneNumber;
    private String authority;
}
