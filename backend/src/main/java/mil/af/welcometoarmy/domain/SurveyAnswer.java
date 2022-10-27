package mil.af.welcometoarmy.domain;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.*;
import mil.af.welcometoarmy.web.dto.survey.QuestionDto;
import mil.af.welcometoarmy.web.dto.survey_answer.AnswerDto;
import mil.af.welcometoarmy.web.dto.survey_answer.SurveyAnswerResponseDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
public class SurveyAnswer extends BaseTimeEntity{

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String answers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SURVEY_ID", nullable = false)
    private Survey survey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOLDIER_ID", nullable = false)
    private Soldier soldier;

    public void setSurvey(Survey survey) {
        if (this.survey != null)
            this.survey.getSurveyAnswers().remove(this);
        if (survey == null) this.survey = null;
        else {
            this.survey = survey;
            if (survey.getSurveyAnswers() == null) {
                List<SurveyAnswer> list = new ArrayList<>();
                list.add(this);
                survey.setSurveyAnswers(list);
            } else survey.getSurveyAnswers().add(this);
        }
    }

    public void setSoldier(Soldier soldier) {
        this.soldier = soldier;
    }

    public void update(SurveyAnswer surveyAnswer) {
        answers = surveyAnswer.getAnswers();
    }

    public SurveyAnswerResponseDto toDto() {
        return SurveyAnswerResponseDto.builder()
                .id(id)
                .answers(new Gson().fromJson(answers, new TypeToken<List<AnswerDto>>(){}.getType()))
                .soldierName(soldier.getName())
                .platoonNum(soldier.getPlatoonNum())
                .createdDate(this.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }
}
