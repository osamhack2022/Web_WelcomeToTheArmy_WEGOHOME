package mil.af.welcometoarmy.web.dto.survey_answer;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@ToString
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDto {

    @NotNull(message = "id를 입력해주세요.")
    @ApiModelProperty(value = "id", required = true)
    private Long id;

    @NotBlank(message = "답변을 입력해주세요.")
    @ApiModelProperty(value = "답변", required = true)
    private String answer;
}
