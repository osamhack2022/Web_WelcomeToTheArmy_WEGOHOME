package mil.af.welcometoarmy.web.dto.survey;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import mil.af.welcometoarmy.domain.Survey;
import mil.af.welcometoarmy.domain.enums.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    private QuestionDto questionDto;

    @NotBlank(message = "공개 범위를 입력해주세요.")
    @ApiModelProperty(value = "공개 범위", required = true)
    private String range;

    @NotNull(message = "기수를 입력해주세요.")
    @ApiModelProperty(value = "기수", required = true)
    private int generation;

    @ApiModelProperty(value = "대대")
    private String battalion;

    @ApiModelProperty(value = "중대")
    private String company;

    @ApiModelProperty(value = "소대")
    private String platoon;

    @NotBlank(message = "조사 시작일을 입력해주세요.")
    @ApiModelProperty(value = "조사 시작일", required = true, example = "2000-01-01 00:00")
    private String startDate;

    @NotBlank(message = "조사 마감일을 입력해주세요.")
    @ApiModelProperty(value = "조사 마감일", required = true, example = "2000-01-01 00:00")
    private String endDate;

    public Survey toEntity() {
        return Survey.builder()
                .title(title)
                .questions(convertObjectToJsonString(questionDto))
                .range(Range.valueOf(range))
                .generation(generation)
                .battalion(battalion)
                .company(company)
                .platoon(platoon)
                .startDate(LocalDateTime.parse(startDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .endDate(LocalDateTime.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();
    }

    public String convertObjectToJsonString(QuestionDto questionDto) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = null;
        try {
            jsonStr = mapper.writeValueAsString(questionDto);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("올바른 json 형태가 아닙니다.");
        }
        return jsonStr;
    }
}
