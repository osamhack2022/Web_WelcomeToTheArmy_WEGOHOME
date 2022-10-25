package mil.af.welcometoarmy.web.dto.schedule;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Builder
@Getter
@ToString
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponseDto {
    private Long id;
    private String name;
    private int generation;
    private String belong;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
}
