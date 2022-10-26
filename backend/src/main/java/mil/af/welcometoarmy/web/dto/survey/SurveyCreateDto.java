package mil.af.welcometoarmy.web.dto.survey;

import com.google.gson.Gson;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import mil.af.welcometoarmy.domain.Survey;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Builder
@Getter
@ToString
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class SurveyCreateDto {

    @NotBlank(message = "제목을 입력해주세요.")
    @ApiModelProperty(value = "제목", required = true)
    private String title;

    private List<QuestionDto> questions;

    @NotNull(message = "기수를 입력해주세요.")
    @Positive(message = "기수는 양수만 입력해주세요.")
    @ApiModelProperty(value = "기수", required = true)
    private int generation;

    @NotBlank(message = "공개범위를 입력해주세요.")
    @ApiModelProperty(value = "공개범위", required = true, example = "111")
    private String belong;

    @NotBlank(message = "조사 시작일을 입력해주세요.")
    @ApiModelProperty(value = "조사 시작일", required = true, example = "2000-01-01 00:00")
    private String startDate;

    @NotBlank(message = "조사 마감일을 입력해주세요.")
    @ApiModelProperty(value = "조사 마감일", required = true, example = "2000-01-01 00:00")
    private String endDate;

    public Survey toEntity() {
        questions.stream().filter(q -> q.getType().equals("객관식")).forEach(q -> q.setCounts(new int[q.getOptions().size()]));
        return Survey.builder()
                .title(title)
                .questions(new Gson().toJson(questions))
                .generation(generation)
                .belong(belong)
                .startDate(LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .endDate(LocalDateTime.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }
}
