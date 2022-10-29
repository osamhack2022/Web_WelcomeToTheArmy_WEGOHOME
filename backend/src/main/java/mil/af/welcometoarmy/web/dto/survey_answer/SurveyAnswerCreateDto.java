package mil.af.welcometoarmy.web.dto.survey_answer;

import com.google.gson.Gson;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import mil.af.welcometoarmy.domain.SurveyAnswer;

import java.util.List;

@Builder
@Getter
@ToString
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class SurveyAnswerCreateDto {

    private List<AnswerDto> answers;

    public SurveyAnswer toEntity() {
        return SurveyAnswer.builder()
                .answers(new Gson().toJson(answers))
                .build();
    }
}
