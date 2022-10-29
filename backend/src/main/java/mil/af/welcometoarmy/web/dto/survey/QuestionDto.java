package mil.af.welcometoarmy.web.dto.survey;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Getter
@Setter
@ToString
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {

    @NotNull(message = "id를 입력해주세요.")
    @ApiModelProperty(value = "id", required = true)
    private Long id;

    @NotBlank(message = "타입을 입력해주세요.")
    @ApiModelProperty(value = "타입", required = true)
    private String type;

    @NotBlank(message = "질문 제목을 입력해주세요.")
    @ApiModelProperty(value = "질문 제목", required = true)
    private String title;

    @ApiModelProperty(value = "질문 설명")
    private String description;

    private List<String> options;

    private int counts[];
}
