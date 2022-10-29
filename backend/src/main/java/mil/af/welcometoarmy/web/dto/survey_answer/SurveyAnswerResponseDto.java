package mil.af.welcometoarmy.web.dto.survey_answer;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Builder
@Getter
@ToString
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class SurveyAnswerResponseDto {
    private Long id;
    private List<AnswerDto> answers;
    private String soldierName;
    private String platoonNum;
    private String createdDate;
}
