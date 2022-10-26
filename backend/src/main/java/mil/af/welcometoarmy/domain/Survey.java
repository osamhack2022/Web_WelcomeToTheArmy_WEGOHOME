package mil.af.welcometoarmy.domain;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.*;
import mil.af.welcometoarmy.domain.enums.HasAnswer;
import mil.af.welcometoarmy.domain.enums.Range;
import mil.af.welcometoarmy.web.dto.survey.QuestionDto;
import mil.af.welcometoarmy.web.dto.survey.SurveyResponseDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
public class Survey extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String questions;

    @Positive
    private int generation;

    @NotNull
    private String belong;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;

    @NotNull
    private int total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MANAGER_ID")
    private Manager manager;

    @OneToMany(
            mappedBy = "survey",
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private List<SurveyAnswer> surveyAnswers = new ArrayList<>();

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public void setSurveyAnswers(List<SurveyAnswer> surveyAnswers) {
        this.surveyAnswers = surveyAnswers;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void update(Survey survey) {
        if (survey.getStartDate().isAfter(survey.getEndDate()))
            throw new IllegalArgumentException("조사 마감일보다 조사 시작일이 빨라야 합니다");

        title = survey.getTitle();
        questions = survey.getQuestions();
        generation = survey.generation;
        belong = survey.getBelong();
        startDate = survey.getStartDate();
        endDate = survey.getEndDate();
    }

    public SurveyResponseDto toDto() {
        return SurveyResponseDto.builder()
                .id(id)
                .title(title)
                .questions(new Gson().fromJson(questions, new TypeToken<List<QuestionDto>>(){}.getType()))
                .generation(generation)
                .belong(belong)
                .startDate(startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .endDate(endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .total(total)
                .build();
    }
}
