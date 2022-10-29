package mil.af.welcometoarmy.web.dto.gallery;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import mil.af.welcometoarmy.domain.Gallery;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Builder
@Getter
@Setter
@ToString
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class GalleryUpdateDto {

    @NotBlank(message = "제목을 입력해주세요.")
    @ApiModelProperty(value = "제목", required = true)
    private String title;

    @NotBlank(message = "본문을 입력해주세요.")
    @ApiModelProperty(value = "본문", required = true)
    private String content;

    @NotNull(message = "기수를 입력해주세요.")
    @Positive(message = "기수는 양수만 입력해주세요.")
    @ApiModelProperty(value = "기수", required = true)
    private int generation;

    @NotBlank(message = "공개범위를 입력해주세요.")
    @ApiModelProperty(value = "공개범위", required = true, example = "111")
    private String belong;

    public Gallery toEntity() {
        return Gallery.builder()
                .title(title)
                .content(content)
                .generation(generation)
                .belong(belong)
                .build();
    }
}
