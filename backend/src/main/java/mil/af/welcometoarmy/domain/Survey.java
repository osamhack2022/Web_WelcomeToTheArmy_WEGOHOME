package mil.af.welcometoarmy.domain;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.*;
import mil.af.welcometoarmy.domain.enums.Range;
import mil.af.welcometoarmy.web.dto.survey.QuestionDto;
import mil.af.welcometoarmy.web.dto.survey.SurveyResponseDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
public class Survey {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String questions;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Range range;

    @NotNull
    private int generation;

    private String battalion;

    private String company;

    private String platoon;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;

    public void update(Survey survey) {
        if (survey.getStartDate().isAfter(survey.getEndDate()))
            throw new IllegalArgumentException("조사 마감일보다 조사 시작일이 빨라야 합니다");

        this.title = survey.getTitle();
        this.questions = survey.getQuestions();
        this.range = survey.getRange();
        this.generation = survey.generation;
        this.battalion = survey.getBattalion();
        this.company = survey.getCompany();
        this.platoon = survey.getPlatoon();
        this.startDate = survey.getStartDate();
        this.endDate = survey.getEndDate();
    }

    public SurveyResponseDto toDto() {
        return SurveyResponseDto.builder()
                .id(id)
                .title(title)
                .questions(new Gson().fromJson(questions, new TypeToken<List<QuestionDto>>(){}.getType()))
                .range(range.name())
                .generation(generation)
                .battalion(battalion)
                .company(company)
                .platoon(platoon)
                .startDate(startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .endDate(endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }
}
