package mil.af.welcometoarmy.web.dto.survey;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Builder
@Getter
@ToString
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class SurveyResponseDto {
    private Long id;
    private String title;
    private List<QuestionDto> questions;
    private String range;
    private int generation;
    private String battalion;
    private String company;
    private String platoon;
    private String startDate;
    private String endDate;
}
